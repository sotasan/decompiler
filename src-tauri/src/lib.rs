use jni::objects::JString;
use jni::{InitArgsBuilder, JNIVersion, JavaVM, jni_sig, jni_str};
use tauri::{Manager, State};

struct Jvm(JavaVM);

#[tauri::command]
fn greet_from_jvm(jvm: State<'_, Jvm>) -> Result<String, String> {
    jvm.0
        .attach_current_thread(|env| -> jni::errors::Result<String> {
            let class = env.find_class(jni_str!("moe/sota/decompiler/jvm/Main"))?;
            let result = env.call_static_method(
                &class,
                jni_str!("greet"),
                &jni_sig!(() -> java.lang.String),
                &[],
            )?;
            let obj = result.l()?;
            let jstr = unsafe { JString::from_raw(env, obj.as_raw()) };
            jstr.try_to_string(env)
        })
        .map_err(|e: jni::errors::Error| format!("greet failed: {e}"))
}

#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() {
    tauri::Builder::default()
        .plugin(tauri_plugin_cli::init())
        .plugin(tauri_plugin_opener::init())
        .invoke_handler(tauri::generate_handler![greet_from_jvm])
        .setup(|app| {
            let resource_dir = app.path().resource_dir()?;
            let jre = resource_dir.join("jre");
            let jar = resource_dir.join("jvm.jar");
            let jar_str = jar.to_str().ok_or("non-UTF8 jar path")?;
            let args = InitArgsBuilder::new()
                .version(JNIVersion::V21)
                .option(format!("-Djava.class.path={jar_str}"))
                .build()?;
            let libjvm = jre
                .join(if cfg!(target_os = "windows") { "bin" } else { "lib" })
                .join("server")
                .join(java_locator::get_jvm_dyn_lib_file_name());
            let jvm = JavaVM::with_libjvm(args, || Ok(libjvm))?;
            app.manage(Jvm(jvm));

            #[cfg(target_os = "macos")]
            {
                use tauri::menu::{AboutMetadata, MenuBuilder, PredefinedMenuItem, SubmenuBuilder};

                let metadata = AboutMetadata {
                    name: Some("Decompiler".into()),
                    version: Some(env!("CARGO_PKG_VERSION").into()),
                    short_version: Some("".into()),
                    authors: None,
                    comments: None,
                    copyright: Some("Copyright © 2026 Sōta".into()),
                    license: None,
                    website: None,
                    website_label: None,
                    credits: Some("https://github.com/sotasan/decompiler".into()),
                    icon: None,
                };

                let about = PredefinedMenuItem::about(
                    app.handle(),
                    Some("About Decompiler"),
                    Some(metadata),
                )?;
                let quit = PredefinedMenuItem::quit(app.handle(), None)?;

                let app_menu = SubmenuBuilder::new(app.handle(), "Decompiler")
                    .item(&about)
                    .separator()
                    .item(&quit)
                    .build()?;

                let menu = MenuBuilder::new(app.handle()).item(&app_menu).build()?;
                app.set_menu(menu)?;
            }
            Ok(())
        })
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
