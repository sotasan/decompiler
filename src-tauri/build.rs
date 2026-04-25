use std::path::PathBuf;
use std::process::Command;

fn main() {
    let manifest_dir = PathBuf::from(env!("CARGO_MANIFEST_DIR"));
    let jvm_dir = manifest_dir
        .join("../jvm")
        .canonicalize()
        .expect("jvm directory not found");

    println!("cargo:rerun-if-changed={}", jvm_dir.display());

    let gradlew = if cfg!(windows) {
        jvm_dir.join("gradlew.bat")
    } else {
        jvm_dir.join("gradlew")
    };

    let status = Command::new(&gradlew)
        .arg(":shadowJar")
        .current_dir(&jvm_dir)
        .status()
        .expect("failed to invoke gradle wrapper");

    if !status.success() {
        panic!("gradle :shadowJar failed");
    }

    tauri_build::build();
}
