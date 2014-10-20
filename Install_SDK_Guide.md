# 目次

* [対応環境](#対応環境)
* [MTBurn-Android-SDKをダウンロード](#MTBurn-Android-SDKをダウンロード)
* [Eclipse への導入](#Eclipseへの導入)
	* [MTBurn-Android-SDK ライブラリの追加](#MTBurn-Android-SDKライブラリの追加)
	* [GooglePlayServiceライブラリの追加](#GooglePlayServiceライブラリの追加)
* [Android Studioへの導入](#AndroidStudioへの導入)
	* [MTBurn-Android-SDK ライブラリの追加](#MTBurn-Android-SDKライブラリの追加)
	* [GooglePlayServiceライブラリの追加](#GooglePlayServiceライブラリの追加)

本ドキュメントは MTBurn-Android-SDK を Eclipse のプロジェクトに追加し、実際に使える所までを記したものです。

#対応環境

動作する **Android のSDKVersionは 8 以上**になります。

お困りの際は以下のサポートまで一報ください。

[a@mtburn.com](a@mtburn.com)

#MTBurn-Android-SDKをダウンロード

以下の URL から AppDavis Android SDK をダウンロードします。最新のバージョンを選択することを推奨します。

[AppDavis Android SDK をダウンロード](https://github.com/mtburn/MTBurn-Android-SDK-Install-Guide/releases)

ダウンロードが完了したら、取得した zip ファイルを解凍して SDK ライブラリを確認して下さい。

```
MTBurn-Android-SDK-Release
```

また、デモアプリを試す場合は、demo ディレクトリを import してください。

下記の説明に沿ってMTBurn-Android-SDKライブラリとGooglePlayServicerライブラリを追加することで SDK の各種機能を確認することが出来ます。

#Eclipseへの導入

###MTBurn-Android-SDKライブラリの追加

- 1.Existing Android Code into Workspaceを選択。

- 2.次へを選択。
![](Install_SDK_Guide_Images/import_google_play_service.png)

- 3.ルート・ディレクトリの参照で、ダウンロードした MTBurn-Android-SDK-Release を選択後、完了。
![](Install_SDK_Guide_Images/import_mtburn_android_sdk.png)

- 4.MTBurn-Android-SDK を使用するプロジェクトのプロパティを開き、
MTBurn-Android-SDK-Release ライブラリを追加。

- 5.OKを選択。

![](Install_SDK_Guide_Images/add_mtburn_android_sdk.png)

###GooglePlayServiceライブラリの追加

- 1.Existing Android Code into Workspaceを選択。

- 2.次へを選択。
![](Install_SDK_Guide_Images/import_google_play_service.png)

- 3.ルート・ディレクトリの参照で、android-sdk/extras/google/google_play_services/libproject/google-play-services_lib　を選択後、完了。
![](Install_SDK_Guide_Images/import_google_play_service_2.png)

- 4.MTBurn-Android-SDK を使用するプロジェクトのプロパティを開き、
google-play-services_libのライブラリを追加。
- 5.OKを選択。

![](Install_SDK_Guide_Images/add_google_play_service.png)


以上で Eclipse への導入は完了です。

#AndroidStudioへの導入

###MTBurn-Android-SDKライブラリの追加

- 1.File -> Import Module を選択。

- 2.ソース・ディレクトリの参照で、ダウンロードした MTBurn-Android-SDK-Release を選択後、Next。
![](Install_SDK_Guide_Images/import_mtburn_android_sdk_androidstudio.png)

- 3.チェックマークを確認後、完了。
![](Install_SDK_Guide_Images/import_mtburn_android_sdk_androidstudio2.png)

- 4.MTBurn-Android-SDK を使用するプロジェクトの app/build.gradle の dependencies 配下に、
`compile project(":mTBurnAndroidSDKRelease")` を追加。

- 5.Sync Projects with Gradle Files を実行。

###GooglePlayServiceライブラリの追加

- 1.Google Play services SDK をまだインストールしていない場合は [SDK Manager からインストールする](http://developer.android.com/google/play-services/setup.html)。

- 2.MTBurn-Android-SDK を使用するプロジェクトの app/build.gradle の dependencies 配下に、
`compile 'com.google.android.gms:play-services:6.1.+'` を追記する

- 3.MTBurn-Android-SDK を使用するプロジェクトの app/build.gradle の android, defaultConfig 配下の
`minSdkVersion` を 9 に修正する

- 4.Sync Projects with Gradle Files を実行。

以上で Android Studio への導入は完了です。
