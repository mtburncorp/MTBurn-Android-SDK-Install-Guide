# 目次

* [対応環境](#対応環境)
* [AppDavis Android SDKをダウンロード](#AppDavis Android SDKをダウンロード)
* [AppDavis Android SDKをインストール](#AppDavis Android SDKをインストール)

本ドキュメントは AppDavis Android SDK を Eclipse のプロジェクトに追加し、実際に使える所までを記したものです。

# 対応環境

動作する **Android のSDKVersionは 8 以上**になります。

お困りの際は以下のサポートまで一報ください。

[a@mtburn.com](a@mtburn.com)

# AppDavis Android SDKをダウンロード

以下の URL から AppDavis Android SDK をダウンロードします。

[AppDavis Android SDK をダウンロード](https://banner.dspcdn.com/mtbimg/resource/AppDavis.1.0.2.zip)

ダウンロードが完了したら、取得した zip ファイルを解凍して以下の jar ファイルを確認して下さい。

```
app_davis.jar
```
# AppDavis Android SDKをインストール

上記で取得した jar ファイルをEclipseで作成したプロジェクトのlibsフォルダに配置してください。

# GooglePlayServiceライブラリの追加

- 1.Existing Android Code into Workspaceを選択。

- 2.次へを選択。
![](Install_SDK_Guide_Images/import_google_play_service.png)

- 3.ルート・ディレクトリの参照で、android-sdk/extras/google/google_play_services/libproject/google-play-services_lib　を選択後、完了。
![](Install_SDK_Guide_Images/import_google_play_service_2.png)

- 4.AppDavis Android SDKを使用するプロジェクトのプロパティを開き、
google-play-services_libのライブラリを追加。
- 5.OKを選択。

![](Install_SDK_Guide_Images/add_google_play_service.png)


以上で完了です。


