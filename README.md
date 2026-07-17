<p align="center">
    <a href="demo/src/main/java/moe/sota/decompiler/demo/Main.java"><img width="75%" alt="Screenshot" src=".github/assets/screenshot.png"></a>
</p>

<p align="center">
    <a href="https://github.com/sotasan/decompiler/tags"><img alt="Version" src="https://img.shields.io/github/v/release/sotasan/decompiler?label=Version"></a>
    <a href="https://github.com/sotasan/decompiler/actions/workflows/release.yml"><img alt="Release" src="https://github.com/sotasan/decompiler/actions/workflows/release.yml/badge.svg"></a>
    <a href="LICENSE"><img alt="License" src="https://img.shields.io/github/license/sotasan/decompiler?label=License"></a>
</p>

# <img src="src/main/resources/logo/logo.png" alt="Logo" width="32" align="center"> Decompiler

A GUI application that allows you to browse Java archives using various decompilers.

## Download

You can download the application from the [releases](https://github.com/sotasan/decompiler/releases) page.

## Usage

To run the application, make sure you have Java 25+ installed and then execute the JAR file with `java -jar decompiler-x.y.z.jar`.

To open an archive, select `File > Open File (Ctrl/Cmd + O)` in the menu bar or directly drag and drop it.

To change the decompiler, select the one you want to use, with the combo box in the top right corner.

If you want to compare different decompilers side by side or decompile multiple archives at the same time,
you can create a new instance from the menu bar `File > New Instance (Ctrl/Cmd + N)`.

## Decompilers

The following decompilers are supported:

- [CFR](https://github.com/FabricMC/cfr) - Another Java Decompiler
- [JD](https://java-decompiler.github.io) - Yet another fast Java decompiler
- [Procyon](https://github.com/mstrobel/procyon) - A suite of Java metaprogramming tools
- [Vineflower](https://vineflower.org) - A modern Java decompiler aiming to be as accurate as possible

## Languages

The application is multilingual and determines the language based on your locale.

The following languages are supported:

- [English](src/main/resources/langs/language.properties)
- [German](src/main/resources/langs/language_de.properties)
- [Spanish](src/main/resources/langs/language_es.properties)
- [French](src/main/resources/langs/language_fr.properties)
- [Japanese](src/main/resources/langs/language_ja.properties)
- [Dutch](src/main/resources/langs/language_nl.properties)
- [Russian](src/main/resources/langs/language_ru.properties)
- [Chinese](src/main/resources/langs/language_zh.properties)

If you want to add or change a localization,
you can edit the files [here](src/main/resources/langs)
and create a [pull request](https://github.com/sotasan/decompiler/pulls).

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

Contributions are welcome!

If you want to report bugs or provide feedback,
you can create an [issue](https://github.com/sotasan/decompiler/issues).

If you would like to contribute directly,
you can create a [pull request](https://github.com/sotasan/decompiler/pulls).

## License

Licensed under the [MIT](LICENSE) license.
