<p align="center">
    <img src="src/main/resources/logo/logo.png" alt="Logo" width="80">
</p>

<h1 align="center">Decompiler</h1>

<p align="center">Una aplicación GUI para explorar archivos Java con el decompilador que elijas.</p>

<p align="center">
    <a href="https://github.com/sotasan/decompiler/tags"><img alt="Version" src="https://img.shields.io/github/v/release/sotasan/decompiler?label=Version"></a>
    <a href="https://github.com/sotasan/decompiler/actions/workflows/release.yml"><img alt="Release" src="https://github.com/sotasan/decompiler/actions/workflows/release.yml/badge.svg"></a>
    <a href="LICENSE"><img alt="License" src="https://img.shields.io/github/license/sotasan/decompiler?label=License"></a>
</p>

<p align="center"><a href="README.md">English</a> | <a href="README.de.md">Deutsch</a> | Español | <a href="README.fr.md">Français</a> | <a href="README.ja.md">日本語</a> | <a href="README.nl.md">Nederlands</a> | <a href="README.ru.md">Русский</a> | <a href="README.zh.md">中文</a></p>

<p align="center">
    <a href="demo/src/main/java/moe/sota/decompiler/demo/Main.java"><img width="75%" alt="Screenshot" src=".github/assets/screenshot.png"></a>
</p>

## Uso

Descarga el último JAR desde la página de [releases](https://github.com/sotasan/decompiler/releases) y ejecútalo con Java 25+:

```bash
java -jar decompiler-x.y.z.jar
```

- **Abrir un archivo** — `Archivo > Abrir Archivo` (`Ctrl/Cmd + O`), o arrástralo y suéltalo en la ventana.
- **Cambiar de decompilador** — usa el desplegable de la esquina superior derecha.
- **Comparar o multitarea** — `Archivo > Nueva Instancia` (`Ctrl/Cmd + N`) abre otra ventana para ver decompiladores lado a lado o explorar varios archivos a la vez.

## Decompiladores

Se admiten los siguientes decompiladores:

- [CFR](https://github.com/FabricMC/cfr) — Otro decompilador de Java
- [JD](https://java-decompiler.github.io) — Otro decompilador de Java rápido más
- [Procyon](https://github.com/mstrobel/procyon) — Un conjunto de herramientas de metaprogramación para Java
- [Vineflower](https://vineflower.org) — Un decompilador de Java moderno que busca la máxima precisión

## Idiomas

La aplicación se adapta a la configuración regional de tu sistema:

[Inglés](src/main/resources/langs/language.properties) |
[Alemán](src/main/resources/langs/language_de.properties) |
[Español](src/main/resources/langs/language_es.properties) |
[Francés](src/main/resources/langs/language_fr.properties) |
[Japonés](src/main/resources/langs/language_ja.properties) |
[Neerlandés](src/main/resources/langs/language_nl.properties) |
[Ruso](src/main/resources/langs/language_ru.properties) |
[Chino](src/main/resources/langs/language_zh.properties)

Para añadir o mejorar una traducción, edita los [archivos de idioma](src/main/resources/langs) y abre un [pull request](https://github.com/sotasan/decompiler/pulls).

## Compilación

Para compilar la aplicación desde el código fuente, simplemente usa Gradle:

```bash
git clone https://github.com/sotasan/decompiler.git
cd decompiler
mise install
./gradlew build
```

Para iniciar la aplicación desde el código fuente con un JAR de demostración precargado:

```bash
./gradlew run
```

## Contribuir

¿Has encontrado un error o tienes una idea? Abre un [issue](https://github.com/sotasan/decompiler/issues) — los pull requests también son bienvenidos.

## Licencia

Publicado bajo la licencia [MIT](LICENSE).
