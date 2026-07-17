<p align="center">
    <img src="src/main/resources/logo/logo.png" alt="Logo" width="80">
</p>

<h1 align="center">Decompiler</h1>

<p align="center">お好みのデコンパイラでJavaアーカイブを閲覧できるGUIアプリケーション。</p>

<p align="center">
    <a href="https://github.com/sotasan/decompiler/tags"><img alt="Version" src="https://img.shields.io/github/v/release/sotasan/decompiler?label=Version"></a>
    <a href="https://github.com/sotasan/decompiler/actions/workflows/release.yml"><img alt="Release" src="https://github.com/sotasan/decompiler/actions/workflows/release.yml/badge.svg"></a>
    <a href="LICENSE"><img alt="License" src="https://img.shields.io/github/license/sotasan/decompiler?label=License"></a>
</p>

<p align="center"><a href="README.md">English</a> | <a href="README.de.md">Deutsch</a> | <a href="README.es.md">Español</a> | <a href="README.fr.md">Français</a> | 日本語 | <a href="README.nl.md">Nederlands</a> | <a href="README.ru.md">Русский</a> | <a href="README.zh.md">中文</a></p>

<p align="center">
    <a href="demo/src/main/java/moe/sota/decompiler/demo/Main.java"><img width="75%" alt="Screenshot" src=".github/assets/screenshot.png"></a>
</p>

## 使い方

[リリース](https://github.com/sotasan/decompiler/releases)ページから最新のJARをダウンロードし、Java 25以降で実行してください:

```bash
java -jar decompiler-x.y.z.jar
```

- **アーカイブを開く** — `ファイル > ファイルを開く`(`Ctrl/Cmd + O`)、またはウィンドウにドラッグ&ドロップします。
- **デコンパイラを切り替える** — 右上のコンボボックスで選択します。
- **比較・並行作業** — `ファイル > 新しいインスタンス`(`Ctrl/Cmd + N`)で新しいウィンドウが開き、デコンパイラを並べて比較したり、複数のアーカイブを同時に閲覧できます。

## デコンパイラ

以下のデコンパイラに対応しています:

- [CFR](https://github.com/FabricMC/cfr) — もうひとつのJavaデコンパイラ
- [JD](https://java-decompiler.github.io) — 高速なJavaデコンパイラ
- [Procyon](https://github.com/mstrobel/procyon) — Javaメタプログラミングツール群
- [Vineflower](https://vineflower.org) — 精度を追求するモダンなJavaデコンパイラ

## 言語

アプリケーションはシステムのロケールに従います:

🇬🇧 [英語](src/main/resources/langs/language.properties) |
🇩🇪 [ドイツ語](src/main/resources/langs/language_de.properties) |
🇪🇸 [スペイン語](src/main/resources/langs/language_es.properties) |
🇫🇷 [フランス語](src/main/resources/langs/language_fr.properties) |
🇯🇵 [日本語](src/main/resources/langs/language_ja.properties) |
🇳🇱 [オランダ語](src/main/resources/langs/language_nl.properties) |
🇷🇺 [ロシア語](src/main/resources/langs/language_ru.properties) |
🇨🇳 [中国語](src/main/resources/langs/language_zh.properties)

翻訳の追加や改善は、[言語ファイル](src/main/resources/langs)を編集して[プルリクエスト](https://github.com/sotasan/decompiler/pulls)を作成してください。

## ビルド

ソースからビルドするには、Gradleを実行するだけです:

```bash
git clone https://github.com/sotasan/decompiler.git
cd decompiler
mise install
./gradlew build
```

デモJARをプリロードした状態でソースから起動するには:

```bash
./gradlew run
```

## 貢献

バグの報告やアイデアがあれば[Issue](https://github.com/sotasan/decompiler/issues)を作成してください — プルリクエストも歓迎です。

## ライセンス

[MIT](LICENSE)ライセンスの下で公開されています。
