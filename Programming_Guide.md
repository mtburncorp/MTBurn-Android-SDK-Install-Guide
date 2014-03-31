# 目次

* [まずはじめに](#まずはじめに)
	* [AndroidManifest.xml](AndroidManifest.xml)
* [ウォール広告](#ウォール広告)
	* [ウォール広告について](#ウォール広告について)
	* [ウォール広告の表示](#ウォール広告の表示)
* [アイコン広告](#アイコン広告)
	* [アイコン広告の表示](#アイコン広告の表示)
	* [アイコン広告の表示時のイベント取得](#アイコン広告の表示時のイベント取得)
	* [アイコン広告のリフレッシュ時間の調整](#アイコン広告のリフレッシュ時間の調整)
	* [アイコン広告のリフレッシュ停止](#アイコン広告のリフレッシュ停止)
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


	<receiver android:name="com.mtburn.android.sdk.receiver.ADVSReceiver" >
            <intent-filter>
                <action android:name="android.intent.action.PACKAGE_ADDED" />

                <data android:scheme="package" />
            </intent-filter>
    </receiver>
</application>

```


# ウォール広告

## ウォール広告について

ウォール広告は〜

## ウォール広告の表示
ウォール広告を表示するためにAndroidManifest.xmlのapplicationタグ内に以下activityを記述してください。

```

<activity
	android:name="com.mtburn.android.sdk.wall.ADVSWallAdActivity"
    android:label="@string/app_name" >
</activity>

```

以下の様に実装してウォール広告を表示します。

```

//(1) 必要なパッケージをインポート
import com.mtburn.android.sdk.wall.ADVSWallAdLoader;

…

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//(2) ウォール広告の初期化処理(Activityとmedia_idを渡す)
		ADVSWallAdLoader.init(Activity, "YOUR_MEDIA_ID");

	}

…

	//(3)onClickイベントなど、表示させたい場所でshow(Activity)メソッドを呼ぶだけ
	@Override
	public void onClick(View v) {
		ADVSWallAdLoader.show(Activity);
	}
	
…

```


# アイコン広告

## アイコン広告の表示

アイコン広告を表示するために必要なADVSIconAdViewを生成しレイアウトに追加します。


```
<!-- xmlで定義する場合 -->
<com.mtburn.android.sdk.icon.ADVSIconAdView
        android:id="@+id/advsIconAdView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>

```

ADVSIconAdLoaderがアイコン広告の情報をロードするクラス、ADVSIconAdViewがアイコン広告を表示するためのビューです。

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
		this.iconAdLoader = new ADVSIconAdLoader(this, "YOUR_MEDIA_ID");
	
		// (4) アイコン広告ビューを生成
		ADVSIconAdView iconAdView = (ADVSIconAdView) findViewById(R.id.advsIconAdView);

		// (5) 広告情報をロードする対象ビューを追加
		this.iconAdLoader.addIconView(iconAdView);

		// (6) 広告情報をロード
		this.iconAdLoader.loadAd();
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
その場合はADVSIconAdLoaderにリスナをセットします。

```

ADVSIconAdLoader iconAdLoader;

iconAdLoader.setOnIconAdLoadListener(iconAdLoadListener);

ADVSIconAdLoadListener iconAdLoadListener = new ADVSIconAdLoadListener() {

	/**
	* アイコン広告情報取得開始のイベントを通知	
	*/
	@Override
	public void iconAdLoaderDidStartLoadingAd() {
	}

	/**
	* アイコン広告情報全体の取得完了のイベントを通知
	*/
	@Override
	public void iconAdLoaderDidFinishLoadingAd() {
	}

	/**
	* アイコン広告情報取得失敗のイベントを通知
	*/
	@Override
	public void iconAdLoaderDidFailToLoadAdError() {
	}

};

```

```

ADVSIconAdView iconAdView;

iconAdView.setOnIconADVSIconAdViewListener(iconAdViewListener);

ADVSIconAdViewListener iconAdViewListener = new ADVSIconAdViewListener() {
	/**
	* 対象アイコンビューのアイコン広告情報の取得完了イベントを通知
	*/
	@Override
	public void iconAdLoaderDidReceiveIconAdView() {
	}
	/**
	* 対象アイコンビューのアイコン広告情報の取得失敗イベントを通知
	*/
	@Override
	public void iconAdLoaderDidFailedToReceiveIconViewError() {
	}
	/**
	* 対象アイコンビューのクリックイベントを通知
	*/
	@Override
	public void iconAdLoaderDidClickIconAdView() {
	}
}

```

## アイコン広告のリフレッシュ時間の調整

アイコン広告情報はADVSIconAdLoaderによってリフレッシュされます。

デフォルトの設定では30秒になっています。

リフレッシュ時間は30秒~120秒の間で以下のように設定できます。

```

ADVSIconAdLoader iconAdLoader;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	iconAdLoader.setRefreshInterval(60.0f);
}

```

## アイコン広告のリフレッシュ停止



デフォルトではリフレッシュされますので、リフレッシュされたくない場合は以下のように設定してください。

```

ADVSIconAdLoader iconAdLoader;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	iconAdLoader.setAutoRefreshingEnabled(false);

}

```



# よくある質問


# 更新履歴
