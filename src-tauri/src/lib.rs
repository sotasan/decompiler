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
            "moe.sota.decompiler.jvm.Service",
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
        .plugin(tauri_plugin_opener::init())
        .invoke_handler(tauri::generate_handler![greet_from_jvm])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
