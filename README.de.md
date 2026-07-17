<p align="center">
    <img src="src/main/resources/logo/logo.png" alt="Logo" width="80">
</p>

<h1 align="center">Decompiler</h1>

<p align="center">Eine GUI-Anwendung zum Durchstöbern von Java-Archiven mit dem Decompiler deiner Wahl.</p>

<p align="center">
    <a href="https://github.com/sotasan/decompiler/tags"><img alt="Version" src="https://img.shields.io/github/v/release/sotasan/decompiler?label=Version"></a>
    <a href="https://github.com/sotasan/decompiler/actions/workflows/release.yml"><img alt="Release" src="https://github.com/sotasan/decompiler/actions/workflows/release.yml/badge.svg"></a>
    <a href="LICENSE"><img alt="License" src="https://img.shields.io/github/license/sotasan/decompiler?label=License"></a>
</p>

<p align="center"><a href="README.md">English</a> | Deutsch | <a href="README.es.md">Español</a> | <a href="README.fr.md">Français</a> | <a href="README.ja.md">日本語</a> | <a href="README.nl.md">Nederlands</a> | <a href="README.ru.md">Русский</a> | <a href="README.zh.md">中文</a></p>

<p align="center">
    <a href="demo/src/main/java/moe/sota/decompiler/demo/Main.java"><img width="75%" alt="Screenshot" src=".github/assets/screenshot.png"></a>
</p>

## Verwendung

Lade die neueste JAR-Datei von der [Releases](https://github.com/sotasan/decompiler/releases)-Seite herunter und starte sie mit Java 25+:

```bash
java -jar decompiler-x.y.z.jar
```

- **Archiv öffnen** — `Datei > Datei öffnen` (`Strg/Cmd + O`), oder einfach per Drag & Drop ins Fenster ziehen.
- **Decompiler wechseln** — über die Combobox oben rechts.
- **Vergleichen oder parallel arbeiten** — `Datei > Neue Instanz` (`Strg/Cmd + N`) öffnet ein weiteres Fenster, um Decompiler nebeneinander zu vergleichen oder mehrere Archive gleichzeitig zu durchstöbern.

## Decompiler

Die folgenden Decompiler werden unterstützt:

- [CFR](https://github.com/FabricMC/cfr) — Ein weiterer Java-Decompiler
- [JD](https://java-decompiler.github.io) — Noch ein schneller Java-Decompiler
- [Procyon](https://github.com/mstrobel/procyon) — Eine Suite von Java-Metaprogrammierungswerkzeugen
- [Vineflower](https://vineflower.org) — Ein moderner Java-Decompiler mit dem Ziel größtmöglicher Genauigkeit

## Sprachen

Die Anwendung richtet sich nach deiner Systemsprache:

🇬🇧 [Englisch](src/main/resources/langs/language.properties) |
🇩🇪 [Deutsch](src/main/resources/langs/language_de.properties) |
🇪🇸 [Spanisch](src/main/resources/langs/language_es.properties) |
🇫🇷 [Französisch](src/main/resources/langs/language_fr.properties) |
🇯🇵 [Japanisch](src/main/resources/langs/language_ja.properties) |
🇳🇱 [Niederländisch](src/main/resources/langs/language_nl.properties) |
🇷🇺 [Russisch](src/main/resources/langs/language_ru.properties) |
🇨🇳 [Chinesisch](src/main/resources/langs/language_zh.properties)

Um eine Übersetzung hinzuzufügen oder zu verbessern, bearbeite die [Sprachdateien](src/main/resources/langs) und erstelle einen [Pull Request](https://github.com/sotasan/decompiler/pulls).

## Build

Um die Anwendung aus dem Quellcode zu bauen, genügt ein Gradle-Build:

```bash
git clone https://github.com/sotasan/decompiler.git
cd decompiler
mise install
./gradlew build
```

Um die Anwendung aus dem Quellcode mit einem vorgeladenen Demo-JAR zu starten:

```bash
./gradlew run
```

## Mitwirken

Einen Fehler gefunden oder eine Idee? Erstelle ein [Issue](https://github.com/sotasan/decompiler/issues) — Pull Requests sind ebenfalls willkommen.

## Lizenz

Lizenziert unter der [MIT](LICENSE)-Lizenz.
