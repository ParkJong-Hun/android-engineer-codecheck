# 株式会社ゆめみ Android エンジニアコードチェック課題

## アプリ仕様

本アプリは GitHub のリポジトリを検索するアプリです。

<video src="https://github.com/user-attachments/assets/71cd5a49-9f63-4d4b-8e6f-ac7f961ffeaf" width="320"></video>

### 環境

- IDE：Android Studio Ladybug Feature Drop | 2024.2.2
- Kotlin：2.1.10
- Java：17
- Gradle：8.10.2
- minSdk：26
- targetSdk：35

※ ライブラリの利用はオープンソースのものに限ります。
※ 環境は適宜更新してください。

### 動作

1. 何かしらのキーワードを入力
2. GitHub API（`search/repositories`）でリポジトリを検索し、結果一覧を概要（リポジトリ名）で表示
3. 特定の結果を選択したら、該当リポジトリの詳細（リポジトリ名、オーナーアイコン、プロジェクト言語、Star 数、Watcher 数、Fork 数、Issue 数）を表示
