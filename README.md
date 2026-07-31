<p align="center">
    <img src="src/main/resources/logo/logo.png" alt="Logo" width="80">
</p>

<h1 align="center">Decompiler</h1>

<p align="center">A GUI application for browsing Java archives with the decompiler of your choice.</p>

<p align="center">English | <a href="README.de.md">Deutsch</a> | <a href="README.es.md">Español</a> | <a href="README.fr.md">Français</a> | <a href="README.ja.md">日本語</a> | <a href="README.nl.md">Nederlands</a> | <a href="README.ru.md">Русский</a> | <a href="README.zh.md">中文</a></p>

<p align="center">
    <a href="https://github.com/sotasan/decompiler/tags"><img alt="Version" src="https://img.shields.io/github/v/release/sotasan/decompiler?label=Version"></a>
    <a href="https://github.com/sotasan/decompiler/actions/workflows/release.yml"><img alt="Release" src="https://github.com/sotasan/decompiler/actions/workflows/release.yml/badge.svg"></a>
    <a href="LICENSE"><img alt="License" src="https://img.shields.io/github/license/sotasan/decompiler?label=License"></a>
</p>

<p align="center">
    <a href="demo/src/main/java/moe/sota/decompiler/demo/Main.java"><img width="75%" alt="Screenshot" src=".github/assets/screenshot.png"></a>
</p>

## Usage

Download the latest JAR from the [releases](https://github.com/sotasan/decompiler/releases) page and run it with Java 25+:

```bash
java -jar decompiler-x.y.z.jar
```

- **Open an archive** - `File > Open File` (`Ctrl/Cmd + O`), or drag and drop it into the window.
- **Switch decompiler** - use the combo box in the top right corner.
- **Compare or multitask** - `File > New Instance` (`Ctrl/Cmd + N`) opens another window, so you can view decompilers side by side or browse multiple archives at once.

## Decompilers

The following decompilers are supported:

- [CFR](https://github.com/FabricMC/cfr) - Another Java Decompiler
- [JD](https://java-decompiler.github.io) - Yet another fast Java decompiler
- [Procyon](https://github.com/mstrobel/procyon) - A suite of Java metaprogramming tools
- [Vineflower](https://vineflower.org) - A modern Java decompiler aiming to be as accurate as possible

## Languages

The application follows your system locale:

[English](src/main/resources/langs/language.properties) |
[German](src/main/resources/langs/language_de.properties) |
[Spanish](src/main/resources/langs/language_es.properties) |
[French](src/main/resources/langs/language_fr.properties) |
[Japanese](src/main/resources/langs/language_ja.properties) |
[Dutch](src/main/resources/langs/language_nl.properties) |
[Russian](src/main/resources/langs/language_ru.properties) |
[Chinese](src/main/resources/langs/language_zh.properties)

To add or improve a translation, edit the [language files](src/main/resources/langs) and open a [pull request](https://github.com/sotasan/decompiler/pulls).

## Build

To build the application from source, simply build it using Gradle:

```bash
git clone https://github.com/sotasan/decompiler.git
cd decompiler
mise install
./gradlew build
```

To launch the application from source with a demo JAR pre-loaded:

```bash
./gradlew run
```

## Contributing

Found a bug or have an idea? Open an [issue](https://github.com/sotasan/decompiler/issues) - pull requests are welcome too.

## License

Licensed under the [MIT](LICENSE) license.
