<p align="center">
    <img src="src/main/resources/logo/logo.png" alt="Logo" width="80">
</p>

<h1 align="center">Decompiler</h1>

<p align="center">Une application graphique pour explorer des archives Java avec le décompilateur de votre choix.</p>

<p align="center">
    <a href="https://github.com/sotasan/decompiler/tags"><img alt="Version" src="https://img.shields.io/github/v/release/sotasan/decompiler?label=Version"></a>
    <a href="https://github.com/sotasan/decompiler/actions/workflows/release.yml"><img alt="Release" src="https://github.com/sotasan/decompiler/actions/workflows/release.yml/badge.svg"></a>
    <a href="LICENSE"><img alt="License" src="https://img.shields.io/github/license/sotasan/decompiler?label=License"></a>
</p>

<p align="center"><a href="README.md">English</a> | <a href="README.de.md">Deutsch</a> | <a href="README.es.md">Español</a> | Français | <a href="README.ja.md">日本語</a> | <a href="README.nl.md">Nederlands</a> | <a href="README.ru.md">Русский</a> | <a href="README.zh.md">中文</a></p>

<p align="center">
    <a href="demo/src/main/java/moe/sota/decompiler/demo/Main.java"><img width="75%" alt="Screenshot" src=".github/assets/screenshot.png"></a>
</p>

## Utilisation

Téléchargez le dernier JAR depuis la page des [releases](https://github.com/sotasan/decompiler/releases) et lancez-le avec Java 25+ :

```bash
java -jar decompiler-x.y.z.jar
```

- **Ouvrir une archive** — `Fichier > Ouvrir un fichier` (`Ctrl/Cmd + O`), ou glissez-déposez-la dans la fenêtre.
- **Changer de décompilateur** — via la liste déroulante en haut à droite.
- **Comparer ou multitâche** — `Fichier > Nouvelle instance` (`Ctrl/Cmd + N`) ouvre une nouvelle fenêtre pour comparer les décompilateurs côte à côte ou parcourir plusieurs archives à la fois.

## Décompilateurs

Les décompilateurs suivants sont pris en charge :

- [CFR](https://github.com/FabricMC/cfr) — Un autre décompilateur Java
- [JD](https://java-decompiler.github.io) — Encore un décompilateur Java rapide
- [Procyon](https://github.com/mstrobel/procyon) — Une suite d'outils de métaprogrammation Java
- [Vineflower](https://vineflower.org) — Un décompilateur Java moderne visant une précision maximale

## Langues

L'application suit la langue de votre système :

[Anglais](src/main/resources/langs/language.properties) |
[Allemand](src/main/resources/langs/language_de.properties) |
[Espagnol](src/main/resources/langs/language_es.properties) |
[Français](src/main/resources/langs/language_fr.properties) |
[Japonais](src/main/resources/langs/language_ja.properties) |
[Néerlandais](src/main/resources/langs/language_nl.properties) |
[Russe](src/main/resources/langs/language_ru.properties) |
[Chinois](src/main/resources/langs/language_zh.properties)

Pour ajouter ou améliorer une traduction, modifiez les [fichiers de langue](src/main/resources/langs) et ouvrez une [pull request](https://github.com/sotasan/decompiler/pulls).

## Compilation

Pour compiler l'application depuis les sources, utilisez simplement Gradle :

```bash
git clone https://github.com/sotasan/decompiler.git
cd decompiler
mise install
./gradlew build
```

Pour lancer l'application depuis les sources avec un JAR de démonstration préchargé :

```bash
./gradlew run
```

## Contribuer

Un bug ou une idée ? Ouvrez une [issue](https://github.com/sotasan/decompiler/issues) — les pull requests sont également les bienvenues.

## Licence

Distribué sous licence [MIT](LICENSE).
