<p align="center">
    <img src="src/main/resources/logo/logo.png" alt="Logo" width="80">
</p>

<h1 align="center">Decompiler</h1>

<p align="center">GUI-приложение для просмотра Java-архивов с декомпилятором на ваш выбор.</p>

<p align="center"><a href="README.md">English</a> | <a href="README.de.md">Deutsch</a> | <a href="README.es.md">Español</a> | <a href="README.fr.md">Français</a> | <a href="README.ja.md">日本語</a> | <a href="README.nl.md">Nederlands</a> | Русский | <a href="README.zh.md">中文</a></p>

<p align="center">
    <a href="https://github.com/sotasan/decompiler/tags"><img alt="Version" src="https://img.shields.io/github/v/release/sotasan/decompiler?label=Version"></a>
    <a href="https://github.com/sotasan/decompiler/actions/workflows/release.yml"><img alt="Release" src="https://github.com/sotasan/decompiler/actions/workflows/release.yml/badge.svg"></a>
    <a href="LICENSE"><img alt="License" src="https://img.shields.io/github/license/sotasan/decompiler?label=License"></a>
</p>

<p align="center">
    <a href="demo/src/main/java/moe/sota/decompiler/demo/Main.java"><img width="75%" alt="Screenshot" src=".github/assets/screenshot.png"></a>
</p>

## Использование

Скачайте последний JAR со страницы [релизов](https://github.com/sotasan/decompiler/releases) и запустите его с Java 25+:

```bash
java -jar decompiler-x.y.z.jar
```

- **Открыть архив** - `Файл > Открыть файл` (`Ctrl/Cmd + O`) или просто перетащите его в окно.
- **Сменить декомпилятор** - с помощью выпадающего списка в правом верхнем углу.
- **Сравнение и многозадачность** - `Файл > Новый экземпляр` (`Ctrl/Cmd + N`) открывает ещё одно окно: можно сравнивать декомпиляторы бок о бок или просматривать несколько архивов одновременно.

## Декомпиляторы

Поддерживаются следующие декомпиляторы:

- [CFR](https://github.com/FabricMC/cfr) - Ещё один декомпилятор Java
- [JD](https://java-decompiler.github.io) - Ещё один быстрый декомпилятор Java
- [Procyon](https://github.com/mstrobel/procyon) - Набор инструментов метапрограммирования для Java
- [Vineflower](https://vineflower.org) - Современный декомпилятор Java, нацеленный на максимальную точность

## Языки

Приложение использует локаль вашей системы:

[Английский](src/main/resources/langs/language.properties) |
[Немецкий](src/main/resources/langs/language_de.properties) |
[Испанский](src/main/resources/langs/language_es.properties) |
[Французский](src/main/resources/langs/language_fr.properties) |
[Японский](src/main/resources/langs/language_ja.properties) |
[Нидерландский](src/main/resources/langs/language_nl.properties) |
[Русский](src/main/resources/langs/language_ru.properties) |
[Китайский](src/main/resources/langs/language_zh.properties)

Чтобы добавить или улучшить перевод, отредактируйте [файлы локализации](src/main/resources/langs) и откройте [pull request](https://github.com/sotasan/decompiler/pulls).

## Сборка

Чтобы собрать приложение из исходного кода, просто выполните сборку Gradle:

```bash
git clone https://github.com/sotasan/decompiler.git
cd decompiler
mise install
./gradlew build
```

Чтобы запустить приложение из исходников с предзагруженным демо-JAR:

```bash
./gradlew run
```

## Участие

Нашли баг или есть идея? Создайте [issue](https://github.com/sotasan/decompiler/issues). Pull request'ы тоже приветствуются.

## Лицензия

Распространяется по лицензии [MIT](LICENSE).
