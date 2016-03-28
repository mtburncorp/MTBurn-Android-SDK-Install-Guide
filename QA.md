# 目次

* [問い合わせの仕方](#howto)
* [端末からどのような情報を取得していますか？](#info)
* [SDK のログ出力は抑制できますか？](#log)
* [〇〇の機能はありますか？](#function)
* [サンプルプロジェクトはありますか？](#sample)
* [広告の表示に問題がある（表示されない、重複するなど）](#not_found_ad)
* [バージョン番号はどういう意味がありますか？](#version)
* [コード中にあるInstreamはどういう意味ですか](#instream)

<a name="howto"></a>
#問い合わせの仕方

###問い合わせ先

- [issues](https://github.com/mtburn/MTBurn-Android-SDK-Install-Guide/issues) を活用ください
- 何らかの理由により、`issues` による問い合わせが難しい場合には、担当者を介して問い合わせください
 - その場合も問い合わせ先以外の仕方はこの項を参考にしてください

<a name="info"></a>
#端末からどのような情報を取得していますか？

- 次の情報を取得しています

| 名称 | API | 詳細 |
| --- | --- | --- |
| advertising ID | `AdvertisingIdClient.Info getId()` | ユーザーがオプトアウト設定をしている場合には取得しない |
| OS バージョン | `Build.VERSION.RELEASE` | `2.2`, `4.0.3 ` など |
| 端末名称 | `Build.MODEL` | `Nexus One`, `X06HT` など |
| 言語設定 | `Locale.getDefault().getLanguage()` | `ja`, `en` など |
| 国コード | `Locale.getDefault().getCountry()` | `JP`, `US` など |

<a name="log"></a>
# SDK のログ出力は抑制できますか？

次のコードを任意の場所に書いてください

```Java
AppDavis.setMuteLogOutput(true);
```

<a name="function"></a>
# 〇〇の機能はありますか？

[javadoc](http://mtburn.github.io/MTBurn-Android-SDK-Install-Guide/javadoc/latest/) に public API が公開されています

<a name="sample"></a>
# サンプルプロジェクトはありますか？
[demo](https://github.com/mtburn/MTBurn-Android-SDK-Install-Guide/tree/master/demo) を参考にしてください

<a name="not_found_ad"></a>
#広告の表示に問題がある（表示されない、重複するなど）

次の順番でおおまかな原因の特定が可能です。それぞれに対する対応方法も示します。

1. アカウントに問題がある
2. アプリの呼び出し方法に問題がある

###アカウントに問題がある

ここでいう`アカウント`とは `媒体 ID (media_id)` とそれに紐づく `広告枠 ID (adspot_id)` を指します。

まず、発行いただいた`アカウント`の設定に問題がある可能性があります。それを確認するために、別の`アカウント` で試してもらうことをお願いしております。別の`アカウント`は、このドキュメントにある `media_id 1 または media_id 2` とそれぞれに紐づく `広告枠 ID` をご参照ください。

そのうえで、上記インフィード広告の説明にある、`media_id 2` とそれに紐づく`広告枠 ID` （以後、`本番の広告を表示するテストアカウント`）は、まれにですが、本番の広告を取得する性質上、広告が取得できない場合があります。
`本番の広告を表示するテストアカウント` を設定しても広告が表示されない場合には、`テストの広告を表示するテストアカウント`を試してもらうことをお願いしております。

`テストの広告を表示するテストアカウント` は `media_id` は `1` で、それぞれに対応する`広告枠 ID` は次のとおりです。

| media_id 1 に対応する広告枠 ID | media_id 2 に対応する広告枠 ID | 広告画像サイズ | 広告フォーマット |
| --- | --- | --- | --- |
| NDgzOjE | NDQ0OjMx | 114x114 pixel | ThumnailMiddle |
| Njc4OjI | OTA2OjMy | 114x114 pixel | ThumnailSmall |
| NzA3OjM | ODEzOjMz | 640x200 pixel | LandscapePhoto |
| MTY5OjQ | OTIyOjM0 | 640x320 pixel | PhotoBottom |
| OTMzOjU | NzA2OjM1 | 640x320 pixel | PhotoMiddle |
| MzUxOjk | MzA3OjM2 | 114x114 pixel | TextOnly |
| OTgxOjUy | MTI2OjU1 | 114x114 pixel | WebView（小） |
| MjQxOjUz | OTkzOjU2 | 640x200 pixel | WebView（中） |
| NjA0OjU0 | MzEzOjU3 | 640x320 pixel | WebView（大） |

###アプリの呼び出し方法に問題がある

`発行していただいたアカウント`、`本番の広告を表示するテストアカウント`、`テストの広告を表示するテストアカウント`の 3 つを試しても表示の問題が解消されない場合には、`アカウント` 関係に問題があるのか、アプリまたは SDK に問題があるのかどうかを確認してもらうことをお願いしております。

[SDK 付属のデモプロジェクト](https://github.com/mtburn/MTBurn-Android-SDK-Install-Guide/tree/master/demo)はデフォルトで `本番の広告を表示するテストアカウント` で動いているので、これを起動して表示がされれば、アプリの呼び出し方法に問題があることがわかります。[main.xml](https://github.com/mtburn/MTBurn-Android-SDK-Install-Guide/blob/master/demo/res/values/main.xml#L18-L43) で `アカウント`を任意に設定出来ますので、`アカウント`に問題があるのかどうかの切り分けにご活用ください。

`アカウント`に問題がある場合には、担当者にご連絡ください。

<a name="version"></a>
# バージョン番号はどういう意味がありますか？

[semver](http://semver.org/) に従います

<a name="instream"></a>
#コード中にあるInstreamはどういう意味ですか

このガイド中にある、`インフィード` と同じ意味で用いられています
