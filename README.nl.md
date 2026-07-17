<p align="center">
    <img src="src/main/resources/logo/logo.png" alt="Logo" width="80">
</p>

<h1 align="center">Decompiler</h1>

<p align="center">Een GUI-applicatie om Java-archieven te doorbladeren met de decompiler van jouw keuze.</p>

<p align="center"><a href="README.md">English</a> | <a href="README.de.md">Deutsch</a> | <a href="README.es.md">Español</a> | <a href="README.fr.md">Français</a> | <a href="README.ja.md">日本語</a> | Nederlands | <a href="README.ru.md">Русский</a> | <a href="README.zh.md">中文</a></p>

<p align="center">
    <a href="https://github.com/sotasan/decompiler/tags"><img alt="Version" src="https://img.shields.io/github/v/release/sotasan/decompiler?label=Version"></a>
    <a href="https://github.com/sotasan/decompiler/actions/workflows/release.yml"><img alt="Release" src="https://github.com/sotasan/decompiler/actions/workflows/release.yml/badge.svg"></a>
    <a href="LICENSE"><img alt="License" src="https://img.shields.io/github/license/sotasan/decompiler?label=License"></a>
</p>

<p align="center">
    <a href="demo/src/main/java/moe/sota/decompiler/demo/Main.java"><img width="75%" alt="Screenshot" src=".github/assets/screenshot.png"></a>
</p>

## Gebruik

Download de nieuwste JAR van de [releases](https://github.com/sotasan/decompiler/releases)-pagina en start hem met Java 25+:

```bash
java -jar decompiler-x.y.z.jar
```

- **Een archief openen** — `Bestand > Bestand openen` (`Ctrl/Cmd + O`), of sleep het naar het venster.
- **Van decompiler wisselen** — via de keuzelijst rechtsboven.
- **Vergelijken of multitasken** — `Bestand > Nieuwe instantie` (`Ctrl/Cmd + N`) opent een extra venster om decompilers naast elkaar te bekijken of meerdere archieven tegelijk te doorbladeren.

## Decompilers

De volgende decompilers worden ondersteund:

- [CFR](https://github.com/FabricMC/cfr) — Nog een Java-decompiler
- [JD](https://java-decompiler.github.io) — Alweer een snelle Java-decompiler
- [Procyon](https://github.com/mstrobel/procyon) — Een verzameling Java-metaprogrammeertools
- [Vineflower](https://vineflower.org) — Een moderne Java-decompiler die zo nauwkeurig mogelijk wil zijn

## Talen

De applicatie volgt de taalinstelling van je systeem:

[Engels](src/main/resources/langs/language.properties) |
[Duits](src/main/resources/langs/language_de.properties) |
[Spaans](src/main/resources/langs/language_es.properties) |
[Frans](src/main/resources/langs/language_fr.properties) |
[Japans](src/main/resources/langs/language_ja.properties) |
[Nederlands](src/main/resources/langs/language_nl.properties) |
[Russisch](src/main/resources/langs/language_ru.properties) |
[Chinees](src/main/resources/langs/language_zh.properties)

Wil je een vertaling toevoegen of verbeteren, bewerk dan de [taalbestanden](src/main/resources/langs) en open een [pull request](https://github.com/sotasan/decompiler/pulls).

## Bouwen

Om de applicatie vanaf de broncode te bouwen, volstaat een Gradle-build:

```bash
git clone https://github.com/sotasan/decompiler.git
cd decompiler
mise install
./gradlew build
```

Om de applicatie vanaf de broncode te starten met een vooraf geladen demo-JAR:

```bash
./gradlew run
```

## Bijdragen

Een bug gevonden of een idee? Open een [issue](https://github.com/sotasan/decompiler/issues) — pull requests zijn ook welkom.

## Licentie

Uitgebracht onder de [MIT](LICENSE)-licentie.
