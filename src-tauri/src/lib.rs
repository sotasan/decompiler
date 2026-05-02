use j4rs::{ClasspathEntry, InvocationArg, JvmBuilder};
use tauri::{AppHandle, Manager};

#[tauri::command]
fn greet_from_jvm(app: AppHandle) -> Result<String, String> {
    let jar = app
        .path()
        .resource_dir()
        .map_err(|e| format!("failed to resolve resource_dir: {e}"))?
        .join("jvm.jar");

    let jar_str = jar.to_str().ok_or_else(|| "non-UTF8 jar path".to_string())?;

    let jvm = JvmBuilder::new()
        .classpath_entry(ClasspathEntry::new(jar_str))
        .build()
        .map_err(|e| format!("failed to start JVM: {e}"))?;

    let result = jvm
        .invoke_static(
            "moe.sota.decompiler.jvm.Main",
            "greet",
            &[] as &[InvocationArg],
        )
        .map_err(|e| format!("failed to invoke Service.greet: {e}"))?;

    jvm.to_rust(result)
        .map_err(|e| format!("failed to unwrap String: {e}"))
}

#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() {
    tauri::Builder::default()
        .plugin(tauri_plugin_cli::init())
        .plugin(tauri_plugin_opener::init())
        .invoke_handler(tauri::generate_handler![greet_from_jvm])
        .setup(|app| {
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
