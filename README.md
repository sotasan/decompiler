<p align="center">
    <a href="src/test/java/com/sotasan/decompiler/Main.java"><img width="85%" alt="Screenshot" src="src/test/resources/META-INF/screenshot.png"></a>
</p>

<p align="center">
    <a href="https://github.com/sotasan/decompiler/tags"><img alt="Version" src="https://img.shields.io/github/v/release/sotasan/decompiler?label=Version"></a>
    <a href="https://github.com/sotasan/decompiler/actions/workflows/build.yml"><img alt="Build" src="https://github.com/sotasan/decompiler/actions/workflows/build.yml/badge.svg"></a>
    <a href="LICENSE.txt"><img alt="License" src="https://img.shields.io/github/license/sotasan/decompiler?label=License"></a>
</p>

# <a href="src/main/resources/logo/logo.svg"><img src="src/main/resources/logo/logo.png" alt="Logo" width="30" height="auto"></a> Decompiler

A GUI application that allows you to browse Java archives using various decompilers.

## Download

You can download the application from the [releases](https://github.com/sotasan/decompiler/releases) page.

## Usage

To run the application, make sure you have Java 11+ installed and then simply execute the JAR file.

To open an archive, select `File > Open File (Ctrl + O)` in the menu bar or directly drag and drop it.

To change the decompiler, select the one you want to use, with the combo box in the top right corner.

If you want to compare different decompilers side by side or decompile multiple archives at the same time,
you can create a new instance from the menu bar `File > New Instance (Ctrl + N)`.

## Decompilers

The following decompilers are supported:

- [CFR](https://github.com/FabricMC/cfr) - Another Java Decompiler
- [Procyon](https://github.com/mstrobel/procyon) - A suite of Java metaprogramming tools
- [Vineflower](https://vineflower.org) - Modern Java decompiler aiming to be as accurate as possible

## Languages

The application is multilingual and determines the language based on your locale.

The following languages are supported:

- [English](src/main/resources/langs/language.properties)
- [German](src/main/resources/langs/language_de.properties)
- [French](src/main/resources/langs/language_fr.properties)
- [Japanese](src/main/resources/langs/language_ja.properties)
- [Dutch](src/main/resources/langs/language_nl.properties)
- [Russian](src/main/resources/langs/language_ru.properties)
- [Chinese](src/main/resources/langs/language_zh.properties)

If you want to add or change a localization,
you can edit the files [here](src/main/resources/langs)
and create a [pull request](https://github.com/sotasan/decompiler/pulls).

## Build

To build the application from source,
make sure you have Java 11+ installed,
then simply build it using Gradle:

```bash
$ git clone https://github.com/sotasan/decompiler.git
$ cd decompiler
$ chmod +x gradlew
$ ./gradlew build
```

## Contributing

Contributions are welcome!

If you want to report bugs or provide feedback,
you can create an [issue](https://github.com/sotasan/decompiler/issues).

If you would like to contribute directly,
you can create a [pull request](https://github.com/sotasan/decompiler/pulls).

## License

Licensed under the [MIT](LICENSE.txt) license.