# 目次

* [まずはじめに](#まずはじめに) 
	* [AndroidManifest.xml](#AndroidManifest.xml)
	* [Media Id の取得](#Media Id の取得)
	* [コード内初期化](#コード内初期化)
* [ウォール広告](#ウォール広告)
	* [ウォール広告の表示](#ウォール広告の表示)
	* [ウォール広告の表示時のイベント取得](#ウォール広告の表示時のイベント取得)
* [アイコン広告](#アイコン広告)
	* [アイコン広告の表示](#アイコン広告の表示)
	* [アイコン広告の表示時のイベント取得](#アイコン広告の表示時のイベント取得)
	* [アイコン広告のリフレッシュ時間の調整](#アイコン広告のリフレッシュ時間の調整)
	* [アイコン広告のリフレッシュ停止](#アイコン広告のリフレッシュ停止)
	* [アイコン広告のリフレッシュ管理](#アイコン広告のリフレッシュ管理)
	* [アイコン広告表示パラメータの設定](#アイコン広告表示パラメータの設定)
* [よくある質問](#よくある質問)
* [更新履歴](#更新履歴)

# まずはじめに

## AndroidManifest.xml

AndroidManifest.xmlに

```
<!-- 必要なパーミッション -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<application>
	<!--  google play serviceのライブラリを使うための記述　-->
	<meta-data
    	android:name="com.google.android.gms.version"
    	android:value="@integer/google_play_services_version" />
	<!-- アプリインストール成果通知を送信するための記述 -->
	<receiver android:name="com.mtburn.android.sdk.util.AppInstallationReceiver">
            <intent-filter>
                <action android:name="android.intent.action.PACKAGE_ADDED" />
                <data android:scheme="package" />
            </intent-filter>
        </receiver>
</application>

```

##Media Id の取得

管理画面より登録し、Media Id を発行します。

**(現段階では担当者にお問い合わせください)**

この Media Id はアプリの識別に用いるものですので、忘れない様にして下さい。

### テスト用 ID

テスト用の Media Id 1 をご利用いただくことができます。ダミーデータが表示され、動作確認が可能です。

##コード内初期化

```
import com.mtburn.android.sdk.AppDavis;
...
public class MyActivity extends Activity {
...
AppDavis.init(this, "YOUR_MEDIA_ID");
...
}
```

# ウォール広告

## ウォール広告の表示
ウォール広告を表示するためにAndroidManifest.xmlのapplicationタグ内に以下activityを記述してください。

```
<activity
	android:name="com.mtburn.android.sdk.wall.WallAdActivity"
	android:label="@string/app_name" >
</activity>
```

以下の様に実装してウォール広告を表示します。

```
//(1) 必要なパッケージをインポート
import com.mtburn.android.sdk.AppDavis;
import com.mtburn.android.sdk.wall.ADVSWallAdLoader;
import com.mtburn.android.sdk.wall.WallAdActivity;
…
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//(2) ウォール広告の初期化処理
		AppDavis.init(this, "YOUR_MEDIA_ID");
		ADVSWallAdLoader wallAdLoader = AppDavis.createWallAdLoader(this);

		//(3)onClickイベントなど、表示させたい場所でshow(Activity)メソッドを呼ぶ
		wallAdLoader.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				wallAdLoader.show(WallAdActivity.class);
			}
		});
	}
…
```

## ウォール広告の表示時のイベント取得

ウォール広告を表示する際に、そのイベントを受け取りたい場合があります。

その場合は ADVSWallAdLoader の setOnWallAdLoadListener に、ADVSWallAdLoadListener を渡すことで、イベントを受信する事が出来ます。

```
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	...
	//(1) listner を設定
	wallAdLoader.setOnWallAdLoadListener(wallAdLoadListener);
	...
    }
    ...
    ADVSWallAdLoadListener wallAdLoadListener = new ADVSWallAdLoadListener() {

       //(2)ウォール広告が出る直前のイベントを通知
       @Override
       public void wallAdLoaderWillPresentWallAd() {
       }

       //(3)ウォール広告のロード失敗のイベントを通知
       @Override
       public void wallAdLoaderDidFailToLoadAdError(String errorString) {
       }
   }
```

# アイコン広告

## アイコン広告の表示

アイコン広告を表示するために必要な IconAdView を生成しレイアウトに追加します。

```
<!-- xmlで定義する場合 -->
<com.mtburn.android.sdk.icon.IconAdView
        android:id="@+id/iconAd1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
```

ADVSIconAdLoader がアイコン広告の情報をロードするクラス、IconAdView がアイコン広告を表示するためのビューです。

以下のように実装してアイコン広告を表示します。

```
//(1) 必要なパッケージをインポート
import com.mtburn.android.sdk.icon.ADVSIconAdLoader;
import com.mtburn.android.sdk.icon.ADVSIconAdView;

public class MyActivity extends Activity {

	// (2) ADVSIconAdLoaderの定義
	ADVSIconAdLoader iconAdLoader;
	
	/** 
	 * ロードの処理はActivityの場合はonCreate内で、
	 *　Fragmentの場合はonCreateView内でします。
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// (3) ADVSIconAdLoaderを生成(Contextとmedia_idをコンストラクタで渡す)
		AppDavis.init(this, "YOUR_MEDIA_ID");
		iconAdLoader = AppDavis.createIconAdLoader(this);
	
		// (4) アイコン広告ビューを生成
		ADVSIconAdView iconAdView = (ADVSIconAdView) findViewById(R.id.iconAdView);

		// (5) 広告情報をロードする対象ビューを追加
		iconAdLoader.addIconView(iconAdView);

		// (6) 広告情報をロード
		iconAdLoader.loadAd();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// (7) 広告のリフレッシュ開始
		iconAdLoader.startAutoRefreshing();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// (8) 広告のリフレッシュ停止
		iconAdLoader.stopAutoRefreshing();
	}
}

```
## アイコン広告の表示時のイベント取得

アイコン広告を表示する際に、そのイベントを受け取りたい場合があります。

その場合はADVSIconAdLoaderに ADVSIconAdLoadListener を、

ADVSIconAdView に ADVSIconAdViewListener をそれぞれセットします。

```
ADVSIconAdLoader iconAdLoader;
ADVSIconAdLoadListener iconAdLoadListener;
ADVSIconAdView iconAdView;
ADVSIconAdViewListener iconAdViewListener;

//(1) ADVSIconAdLoader に listner を設定
iconAdLoader.setOnIconAdLoadListener(iconAdLoadListener);

iconAdLoadListener = new ADVSIconAdLoadListener() {

	// (2) アイコン広告情報取得開始のイベントを通知
	@Override
	public void iconAdLoaderDidStartLoadingAd() {
	}

	// (3) アイコン広告情報全体の取得完了のイベントを通知
	@Override
	public void iconAdLoaderDidFinishLoadingAd() {
	}

	// (4) アイコン広告情報取得失敗のイベントを通知
	@Override
	public void iconAdLoaderDidFailToLoadAdError(String errorString) {
	}
};

//(5) ADVSIconAdView に listner を設定
iconAdView.setOnIconADVSIconAdViewListener(iconAdViewListener);

iconAdViewListener = new ADVSIconAdViewListener() {
	// (6) 対象アイコンビューのアイコン広告情報の取得完了イベントを通知
	@Override
	public void iconAdLoaderDidReceiveIconAdView() {
	}

	// (7) 対象アイコンビューのアイコン広告情報の取得失敗イベントを通知
	@Override
	public void iconAdLoaderDidFailedToReceiveIconViewError(String erroString) {
	}

	// (8) 対象アイコンビューのクリックイベントを通知
	@Override
	public void iconAdLoaderDidClickIconAdView() {
	}
}

```

## アイコン広告のリフレッシュ時間の調整

アイコン広告情報は ADVSIconAdLoader によってリフレッシュされます。

デフォルトの設定では30秒になっています。

リフレッシュ時間は30秒~120秒の間で以下のように設定できます。

それ以外の時間を設定しようとした場合は無視されますので注意して下さい。

```

ADVSIconAdLoader iconAdLoader;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	iconAdLoader.setRefreshInterval(60.0f);
}

```

## アイコン広告のリフレッシュ停止

デフォルトでは自動的にリフレッシュされますので、リフレッシュされたくない場合は以下のように設定してください。

```
ADVSIconAdLoader iconAdLoader;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	iconAdLoader.setAutoRefreshingEnabled(false);
}
```

## アイコン広告のリフレッシュ管理

リフレッシュ設定されたアイコン広告が画面内に表示しないケースがある場合には、必ず一時停止・再開処理を行ってください。これには、複数広告の切り替え処理（広告振り分け SDK の利用等も含む）を行う場合も含まれます。

- 画面遷移で広告が隠れる場合など、画面内にアイコン広告が表示されない場合は、`stopAutoRefreshing` メソッドでリフレッシュを停止してください。
- 画面遷移で広告のある View に戻った場合など、画面内にアイコン広告を表示する場合は、`startAutoRefreshing` メソッドでリフレッシュを再開させてください。

## アイコン広告表示パラメータの設定

ADVSIconAdView のパラメーターをコントロールすることで、アイコン広告の表示形式の調整が可能です。以下の項目が設定できます。

- xml から設定可能な項目
 
パラメータ | 説明 | 型
--- | ---- | ---
app:contentWidth | 広告領域の横幅 (dp) | int
app:contentHeight | 広告領域の縦幅 (dp) | int
app:imageWidth | 広告画像横幅 (dp) | int
app:imageHeight | 広告画像縦幅 (dp) | int
app:titlePadding | 文字領域のパディング(dp)。アイコン画像と文字間の幅 | int
android:textColor | 文字色 (RGB16進数表記) | NSString
android:background | 文字領域の背景色 (RGB16進数表記) | NSString

- ADVSIconAdView の Set method から設定可能な項目

シグネチャ | 説明
--- | ---- | ---
setIconSize(int width, int height) | 広告領域の横幅と縦幅 (dp)
setTextVisible(boolean isVisible) | 文字表示有無
setTextPadding(int padding) | 文字領域のパディング(dp)。アイコン画像と文字間の幅
setTitleSize(int width, int height) | 文字領域の横幅と縦幅 (dp)
setTextSize(int size), setTextSize(int unit, int size) | 文字のフォントサイズ (sp)
setTextColor(int color), setTextColor(ColorStateList colors) | 文字色(RGB16進数表記)

実装例

```
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res/com.example.usemtburn_android_sdk"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".IconAdSampleActivity" >

    <com.mtburn.android.sdk.icon.IconAdView
        android:id="@+id/iconAd"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:layout_alignParentTop="true"
        android:background="@color/icon_ad_view_background"
        app:imageWidth="@dimen/icon_ad_view_image_width"
        app:imageHeight="@dimen/icon_ad_view_image_height"
        app:titlePadding="@dimen/icon_ad_view_title_padding"
        app:contentWidth="@dimen/icon_ad_view_content_width"
        app:contentHeight="@dimen/icon_ad_view_content_height"
        android:textColor="@color/icon_ad_view_text" />
```

# よくある質問

# 更新履歴
