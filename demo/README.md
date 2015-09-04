# DemoApp

Hike SDK の機能を確認するためのデモアプリケーション

## Setup

MTBurn-Android-SDK ライブラリと GooglePlayService ライブラリへの参照の追加が必要です

- SDK は同階層に配置するか、それ以外に配置する場合は `project.properties` を修正してください
- 詳しくは、[Install_SDK_Guide.md](https://github.com/mtburn/MTBurn-Android-SDK-Install-Guide/blob/master/Install_SDK_Guide.md) を参照ください

## Functions

現在確認できる機能は、次の 2 つです

- 広告の表示
- 広告クリック時の挙動

インフィード広告について、確認することができます

## Views

起動すると 6 つのカラムが表示されます

- `Icon Ads` と `Wall Ads` はそれぞれアイコン広告とウォール広告について確認できます
 - これらは非推奨の機能のため、新規の開発には使わないように注意ください
- `Native Ads (Simple)` と `Native Ads 2 (Simple)` はインフィード広告について確認できます
 - 実装には、簡易版インフィード広告機能を使っています
 - `Native Ads 2 (Simple)` は 1 ファイル内で完結する単純なサンプルのため、参考にコードを読むのに適しています
- `Native Ads (Custom)` と `Native Ads 2 (Custom)` もインフィード広告について確認できます
 - 実装には、カスタムインフィード広告機能を使っています
- `Native Ads 2 (Custom)` は RecyclerView を使ってリストを描画しています
 - それ以外は ListView を使ってリストを描画しています

## Account

テストアカウントを使っています

必要に応じて、`values/main.xml` の `media_id` と `adspot_id` をそれぞれ、自分のアカウントに変更してください
