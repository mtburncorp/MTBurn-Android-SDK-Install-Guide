# 目次

* [まずはじめに](#start)
	* [AndroidManifest.xml](#start/manifest)
	* [Media Id の取得](#start/media_id)
	* [コード内初期化](#start/init)
* [(deprecated) ウォール広告](#wall)
	* [ウォール広告の表示](#wall/display)
	* [ウォール広告の表示時のイベント取得](#wall/event)
* [(deprecated) アイコン広告](#icon)
	* [アイコン広告の表示](#icon/display)
	* [アイコン広告の表示時のイベント取得](#icon/event)
	* [アイコン広告のリフレッシュ時間の調整](#icon/refresh)
	* [アイコン広告のリフレッシュ停止](#icon/refresh_stop)
	* [アイコン広告のリフレッシュ管理](#icon/refresh_manage)
	* [アイコン広告表示パラメータの設定](#icon/display_param)
* [インフィード広告](#infeed)
	* [広告枠 Id の取得](#infeed/adspot_id)
	* [簡易版インフィード広告](#infeed/simple)
		* [簡易版インフィード広告の表示](#infeed/simple/display)
		* [簡易版インフィード広告の表示時のイベント取得](#infeed/simple/event)
		* [簡易版インフィード広告の追加ロード](#infeed/simple/additional_load)
		* [利用可能な簡易版インフィード広告フォーマット](#infeed/simple/format)
	* [カスタムインフィード広告](#infeed/custom)
		* [カスタムインフィード広告のロード](#infeed/custom/load)
		* [カスタムインフィード広告の表示に用いるパラメーター](#infeed/custom/display_param)
		* [カスタムインフィード広告の表示時のイベント取得](#infeed/custom/event)
		* [カスタムインフィード広告の表示](#infeed/custom/display)
* [更新履歴](#update)

<a name="start"></a>
# まずはじめに

ダウンロードした SDK プロジェクト内の doc/ 以下に javadoc が格納されています。

本ガイドに登場する各種パッケージの public 修飾子に関連する情報がまとめられています。

doc/index.html をお使いのブラウザで閲覧ください。

<a name="start/manifest"></a>
## AndroidManifest.xml

AndroidManifest.xmlに

```Java
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

<a name="start/media_id"></a>
##Media Id の取得

管理画面より登録し、Media Id を発行します。

**(現段階では担当者にお問い合わせください)**

この Media Id はアプリの識別に用いるものですので、忘れない様にして下さい。

### テスト用 ID

テスト用の Media Id "1" または "2" をご利用いただくことができます。ダミーデータが表示され、動作確認が可能です。

<a name="start/init"></a>
##コード内初期化

```Java
import com.mtburn.android.sdk.AppDavis;
...
public class MyActivity extends Activity {
...
AppDavis.init(this.getApplicationContext(), "YOUR_MEDIA_ID");
...
}
```

<a name="wall"></a>
#(deprecated) ウォール広告

__次のアップデートにて、ウォール広告 API の削除を予定しております。既存のウォール広告呼び出しへの影響はありません。__

<a name="wall/display"></a>
## ウォール広告の表示
ウォール広告を表示するためにAndroidManifest.xmlのapplicationタグ内に以下activityを記述してください。

```XML
<activity
	android:name="com.mtburn.android.sdk.wall.WallAdActivity"
	android:label="@string/app_name" >
</activity>
```

以下の様に実装してウォール広告を表示します。

```Java
//(1) 必要なパッケージをインポート
import com.mtburn.android.sdk.AppDavis;
import com.mtburn.android.sdk.wall.ADVSWallAdLoader;
import com.mtburn.android.sdk.wall.WallAdActivity;
…
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//(2) ウォール広告の初期化処理
		AppDavis.init(this.getApplicationContext(), "YOUR_MEDIA_ID");
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

<a name="wall/event"></a>
## ウォール広告の表示時のイベント取得

ウォール広告を表示する際に、そのイベントを受け取りたい場合があります。

その場合は ADVSWallAdLoader の setOnWallAdLoadListener に、ADVSWallAdLoadListener を渡すことで、

イベントを受信する事が出来ます。

```Java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	...
	//(1) listener を設定
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

<a name="icon"></a>
#(deprecated) アイコン広告

__次のアップデートにて、アイコン広告 API の削除を予定しております。既存のアイコン広告呼び出しへの影響はありません。__

<a name="icon/display"></a>
## アイコン広告の表示

アイコン広告を表示するために必要な IconAdView を生成しレイアウトに追加します。

```XML
<!-- xmlで定義する場合 -->
<com.mtburn.android.sdk.icon.IconAdView
        android:id="@+id/iconAd1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
```

ADVSIconAdLoader がアイコン広告の情報をロードするクラス、IconAdView がアイコン広告を表示するためのビューです。

以下のように実装してアイコン広告を表示します。

```Java
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
		AppDavis.init(this.getApplicationContext(), "YOUR_MEDIA_ID");
		iconAdLoader = AppDavis.createIconAdLoader(this.getApplicationContext());

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

<a name="icon/event"></a>
## アイコン広告の表示時のイベント取得

アイコン広告を表示する際に、そのイベントを受け取りたい場合があります。

その場合はADVSIconAdLoaderに ADVSIconAdLoadListener を、

ADVSIconAdView に ADVSIconAdViewListener をそれぞれセットします。

```Java
ADVSIconAdLoader iconAdLoader;
ADVSIconAdLoadListener iconAdLoadListener;
ADVSIconAdView iconAdView;
ADVSIconAdViewListener iconAdViewListener;

//(1) ADVSIconAdLoader に listener を設定
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

//(5) ADVSIconAdView に listener を設定
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

<a name="icon/refresh"></a>
## アイコン広告のリフレッシュ時間の調整

アイコン広告情報は ADVSIconAdLoader によってリフレッシュされます。

デフォルトの設定では30秒になっています。

リフレッシュ時間は30秒~120秒の間で以下のように設定できます。

それ以外の時間を設定しようとした場合は無視されますので注意して下さい。

```Java
ADVSIconAdLoader iconAdLoader;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	iconAdLoader.setRefreshInterval(60.0f);
}
```

<a name="icon/refresh_stop"></a>
## アイコン広告のリフレッシュ停止

デフォルトでは自動的にリフレッシュされますので、リフレッシュされたくない場合は以下のように設定してください。

```Java
ADVSIconAdLoader iconAdLoader;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	iconAdLoader.setAutoRefreshingEnabled(false);
}
```

<a name="icon/refresh_manage"></a>
## アイコン広告のリフレッシュ管理

リフレッシュ設定されたアイコン広告が画面内に表示しないケースがある場合には、必ず一時停止・再開処理を行ってください。これには、複数広告の切り替え処理（広告振り分け SDK の利用等も含む）を行う場合も含まれます。

- 画面遷移で広告が隠れる場合など、画面内にアイコン広告が表示されない場合は、`stopAutoRefreshing` メソッドでリフレッシュを停止してください。
- 画面遷移で広告のある View に戻った場合など、画面内にアイコン広告を表示する場合は、`startAutoRefreshing` メソッドでリフレッシュを再開させてください。

<a name="icon/display_param"></a>
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
android:textColor | 文字色 (RGB16進数表記) | String
android:background | 文字領域の背景色 (RGB16進数表記) | String

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

```XML
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

<a name="infeed"></a>
#インフィード広告

<a name="infeed/adspot_id"></a>
##広告枠 Id の取得

Media Id の取得に加えて、事前に広告枠の登録を行います。以下の情報を設定し、広告枠IDが払い出されます。

- 広告枠名
- [広告フォーマット](#利用可能な簡易版インフィード広告フォーマット)
- 広告案件数
- 広告位置配列
- HTML （WebView フォーマットを使用する場合のみ）

本物の案件とリンクしているため、下記案件数を下回る場合があります。
その際は、担当者までご連絡ください。

テスト用には、Media Id に "2" をセットした上で、下記広告枠 ID (adspot_id) をセットしてください

```
広告枠ID: NDQ0OjMx
広告フォーマット: ThumnailMiddle
1リクエストあたりの広告案件数: 2
広告位置配列: 3,6
```

```
広告枠ID: OTA2OjMy
広告画像サイズ: ThumnailSmall
1リクエストあたりの広告案件数: 1
広告位置配列: 4
```

```
広告枠ID: ODEzOjMz
広告画像サイズ: LandscapePhoto
1リクエストあたりの広告案件数: 3
広告位置配列: 0,1,2
```

```
広告枠ID: OTIyOjM0
広告画像サイズ: PhotoBottom
1リクエストあたりの広告案件数: 4
広告位置配列: 5,7,9,10
```

```
広告枠ID: NzA2OjM1
広告画像サイズ: PhotoMiddle
1リクエストあたりの広告案件数: 6
広告位置配列: 2,4,6,8,10,12
```

```
広告枠ID: MzA3OjM2
広告画像サイズ: TextOnly
1リクエストあたりの広告案件数: 6
広告位置配列: 5,10,15,20,25,30
```

```
広告枠ID: MTI2OjU1
広告画像サイズ: WebView (小)
1リクエストあたりの広告案件数: 3
広告位置配列: 3,6,9
```

```
広告枠ID: OTkzOjU2
広告画像サイズ: WebView (中)
1リクエストあたりの広告案件数: 3
広告位置配列: 3,6,9
```

```
広告枠ID: MzEzOjU3
広告画像サイズ: WebView (大)
1リクエストあたりの広告案件数: 3
広告位置配列: 3,6,9
```

**現在は、新規広告枠の登録設定については担当者へお問い合わせください。**

<a name="infeed/simple"></a>
##簡易版インフィード広告

ListView と ArrayAdapter を用いた、ビューコンポーネントを使っている場合に簡易に実装が可能です。

<a name="infeed/simple/display"></a>
###簡易版インフィード広告の表示

ADVSInstreamAdAdapter を用いて以下の様に実装し、インフィード広告を表示します。

```Java
// (1) 必要なパッケージをインポート
import com.mtburn.android.sdk.AppDavis;
import com.mtburn.android.sdk.instream.ADVSInstreamAdAdapter;

public class MyActivity extends Activity {

    // (2) ADVSInstreamAdAdapter の定義
    private ADVSInstreamAdAdapter<ArrayAdapter> advsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
     	...
     	// (3) AppDavis SDK の初期化
     	AppDavis.init(this.getApplicationContext(), "YOUR_MEDIA_ID");
     	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getApplicationContext(), R.layout.rowdata, data);

     	// (4) インフィード広告を差し込みたい adapter と広告枠Id を設定
     	advsAdapter = AppDavis.createInstreamAdAdapter(this.getApplicationContext(), arrayAdapter, "YOUR_ADSPOT_ID");
     	...
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     	...
     	listView = (ListView) frameLayout.findViewById(R.id.listView);

     	// (5) インフィード広告を差し込みたい list view に adapter を設定
     	listView.setAdapter(advsAdapter);
     	...
    }
    ...
    // 媒体様のデータ取得完了を待って
    ...
    // (6) インフィード広告のロードを開始する
    advsAdapter.loadAd();
}
```

1リクエストあたりの広告案件数と広告位置配列は、`AppDavis のcreateInstreamAdAdapter(Context context, T originalAdapter, String adSpotId, int adCount, List<Integer>positions)`などのAPIを使ってコントロールすることも出来ます。

<a name="infeed/simple/event"></a>
###簡易版インフィード広告の表示時のイベント取得

インフィード広告を表示する際に、そのイベントを受け取りたい場合があります。

その場合は ADVSInstreamAdAdapter に ADVSInstreamAdLoadListener をセットします。

```Java
ADVSInstreamAdAdapter adapter;
ADVSInstreamAdLoadListener listener;

//(1) ADVSInstreamAdAdapter に listener を設定
adapter.setAdListener(listener);

listener = new ADVSInstreamAdLoadListener() {

    //(2)広告のロード開始時
    @Override
    public void instreamAdLoaderDidStartLoadingAd()
    {
    }
    //(3)広告のロード完了時
    @Override
    public void instreamAdLoaderDidFinishLoadingAds()
    {
    }
    //(4)広告Viewのメイン画像のロード完了時
    @Override
    public void instreamAdLoaderDidFinishLoadingMainImage(String imageUrl)
    {
    }
    //(5)広告Viewのアイコン画像のロード完了時
    @Override
    public void instreamAdLoaderDidFinishLoadingIconImage(String imageUrl)
    {
    }
    //(6)広告のロード失敗時
    @Override
    public void instreamAdLoaderDidFailToLoadAdError(String errorString)
    {
    }
    //(7)広告Viewのロード失敗時
    @Override
    public void instreamAdLoaderDidFailToLoadImageError(String errorString)
    {
    }
    //(8)広告のクリック処理完了時
    @Override
    public void instreamAdLoaderDidClick(String redirectUrl)
    {
    }
};
```

<a name="infeed/simple/additional_load"></a>
###簡易版インフィード広告の追加ロード

ユーザーがサイト下部に到達した際に追加フィードを読み込むような UI の場合に、追加で広告ロードを行うことも可能です。

```Java
public void loadMore()
{
	// 追加の広告をロードして、広告位置配列をもとにテーブル内の適切な位置に挿入します
	advsAdapter.loadAd();
}
```

<a name="infeed/simple/format"></a>
###利用可能な簡易版インフィード広告フォーマット

- 1-1) ThumnailMiddle

```
    // The format looks like this
    //  -----------------------------------------------------
    // |             |  icon + name                          |
    // |   image     |  ad text                              |
    // |             |                                       |
    // |   90x90     |                                       |
    // |             |                                    PR |
    //  -----------------------------------------------------
```

- 1-2) ThumnailSmall

```
    // The format looks like this
    //  -----------------------------------------------------
    // |  -------    icon + name                             |
    // | | image |   PR                                      |
    // | | 50x50 |   ad text                                 |
    // | |       |                                           |
    // |  -------                                            |
    //  -----------------------------------------------------
```

- 1-3) LandscapePhoto

```
    // The format looks like this
    //  -----------------------------------------------------
    // |                                                     |
    // |                  ad image                           |
    // |                                                     |
    // | --------------------------------------------------- |
    // |  ad text                                            |
    // |                                                     |
    // |                                                     |
    // |                                                 PR  |
    // |  advertiser icon + name                             |
    //  -----------------------------------------------------
```

- 1-4) PhotoBottom

```
    // The format looks like this
    //  -----------------------------------------------------
    // |  advertiser |  advertiser name                      |
    // |     icon    |  PR                                   |
    // |             |  ad text                              |
    // | --------------------------------------------------- |
    // |                                                     |
    // |                                                     |
    // |                   image                             |
    // |                                                     |
    //  -----------------------------------------------------
```

- 1-5) PhotoMiddle

```
    // The format looks like this
    //  -----------------------------------------------------
    // | icon + name                                         |
    // |                                                  PR |
    // | --------------------------------------------------- |
    // |                                                     |
    // |                                                     |
    // |                      image                          |
    // |                                                     |
    // |                                                     |
    // |                                                     |
    // | --------------------------------------------------- |
    // |  text                                               |
    //  -----------------------------------------------------
```

- 1-6) TextOnly

```
    // The format looks like this
    //  -----------------------------------------------------
    // |  icon + name                                        |
    // |                                                  PR |
    // | --------------------------------------------------- |
    // |                                                     |
    // |             ad text                                 |
    // |                                                     |
    // |                                                     |
    //  -----------------------------------------------------
```

- 1-7) WebView

HTML を入稿することで、アプリ内 WebView 上で描画することが出来ます。

<a name="infeed/custom"></a>
##カスタムインフィード広告

簡易版インフィード広告よりも柔軟な表示を行いたい場合などに、カスタム型のインフィード広告機能を利用することが出来ます。

<a name="infeed/custom/load"></a>
###カスタムインフィード広告のロード

ADVSInstreamAdPlacer を用いて以下の様に実装し、インフィード広告を表示します。

- ListView

```Java
// (1) 必要なパッケージをインポート
import com.mtburn.android.sdk.AppDavis;
import com.mtburn.android.sdk.instream.ADVSInstreamAdPlacer;
import com.mtburn.android.sdk.instream.InstreamAdViewBinderImpl;
import com.mtburn.android.sdk.model.ADVSInstreamInfoModel;

public class MyActivity extends Activity {

    // (2) ADVSInstreamAdPlacer の定義
    private ADVSInstreamAdPlacer adPlacer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     	// (3) AppDavis SDK の初期化
     	AppDavis.init(getActivity().getApplicationContext(), "YOUR_MEDIA_ID");

     	// (4) ADVSInstreamAdPlacer の生成
     	adPlacer = AppDavis.createInstreamAdPlacer(getActivity().getApplicationContext(), "YOUR_ADSPOT_ID");

     	// (5) 任意の View に広告案件情報を割り当てる（下記、カスタムインフィード広告の表示に用いるパラメーターの項を参照）
     	InstreamAdViewBinderImpl adViewBinder = new InstreamAdViewBinderImpl(getActivity().getApplicationContext()){
            @Override
            public View createView(ViewGroup parent, int layoutId)
            {
                View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.custom_instream_ad_view, parent, false);
                AdViewHolder holder = new AdViewHolder(view);
                view.setTag(holder);
                return view;
            }

            @Override
            public void bindAdData(View v, ADVSInstreamInfoModel adData) {
                AdViewHolder holder = (AdViewHolder)v.getTag();
                holder.setData(adData);

                loadAdImage(adData, holder.adImage, null);
                loadAdIconImage(adData, holder.iconImage, null);
            }
        };
        adPlacer.registerAdViewBinder(adViewBinder);
   }

   // (6) インフィード広告のロードを開始する
   adPlacer.loadAd();

   ...

   // (7) パフォーマンスを出すためには [ViewHolder pattern](http://developer.android.com/training/improving-layouts/smooth-scrolling.html) を使ってください
   static class AdViewHolder {
        TextView advertiserName;
        TextView adText;
        ImageView adImage;
        ImageView iconImage;
        TextView adSponsoredLabel;

        public AdViewHolder(View convertView) {
            advertiserName = (TextView) convertView.findViewById(R.id.custom_instream_advertiser_name);
            adText = (TextView) convertView.findViewById(R.id.custom_instream_ad_text);
            adImage = (ImageView) convertView.findViewById(R.id.custom_instream_ad_image);
            iconImage = (ImageView) convertView.findViewById(R.id.custom_instream_advertiser_icon);
            adSponsoredLabel = (TextView) convertView.findViewById(R.id.custom_instream_sponsor_name);
        }

        void setData(ADVSInstreamInfoModel adData) {
            advertiserName.setText(adData.title());
            adText.setText(adData.content());

            String displayedAdvertiser = adData.displayedAdvertiser();
            if (null != displayedAdvertiser && 0 < displayedAdvertiser.length()) {
                adSponsoredLabel.setText(displayedAdvertiser);
            }
        }
    }
}
```

- RecyclerView

```java
// (1) 必要なパッケージをインポート
import com.mtburn.android.sdk.AppDavis;
import com.mtburn.android.sdk.instream.ADVSInstreamAdPlacer;
import com.mtburn.android.sdk.instream.InstreamAdViewBinderImpl;
import com.mtburn.android.sdk.model.ADVSInstreamInfoModel;

public class MyActivity extends Activity {

    // (2) ADVSInstreamAdPlacer の定義
    private ADVSInstreamAdPlacer adPlacer;
	private RVAdapter adapter;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);

		// (3) 初期化の呼び出し
		setupSDK();

        RecyclerView rv = (RecyclerView)findViewById(R.id.recycler);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        initializeData();

        adapter = new RVAdapter(persons, adPlacer);
        rv.setAdapter(adapter);
    }

	...

	// AppDavis SDK を初期化して、広告案件のロードを開始する
    private void setupSDK() {
        AppDavis.init(this.getApplicationContext(), getString(R.integer.media_id));
        adPlacer = AppDavis.createInstreamAdPlacer(this.getApplicationContext(), getAdSpotId());
        adPlacer.setAdListener(this);

        // 広告読み込み開始
        adPlacer.loadAd();
    }
}
```

<a name="infeed/custom/display_param"></a>
###カスタムインフィード広告の表示に用いるパラメーター

| パラメータ名 | 説明 | 例 |
| --- | --- | --- |
| title | タイトル文言(全角20文字以内) | `TestAd` |
| content | 説明・紹介文(全角40~70文字以内) | `テスト広告です。` |
| displayedAdvertiser | 表記広告主名 | `飲料会社 A社` |
| iconImage | アイコン型の正方形画像(114x114 pixel固定) | 下記メソッドを呼び出してください |
| mainImage | バナー型の矩形画像など(広告枠IDごとにサイズ可変) | 下記メソッドを呼び出してください |

<a name="infeed/custom/event"></a>
###カスタムインフィード広告の表示時のイベント取得

カスタムインフィード広告を表示する際に、そのイベントを受け取りたい場合があります。

その場合は ADVSInstreamAdPlacer に ADVSInstreamAdPlacerListener をセットします。

- ListView

```Java
ADVSInstreamAdPlacer adPlacer;
ADVSInstreamAdPlacerListener listener;

// (1) ADVSInstreamAdAdapter に listener を設定
adPlacer.setAdListener(listener);

listener = new ADVSInstreamAdPlacerListener() {

    // (2) 広告のロード完了時
    // 後述する、ADVSInstreamAdPlacer の placeAd(ADVSInstreamInfoModel advsinstreaminfomodel, View view, ViewGroup viewgroup) に渡すために、広告案件情報を取得します
    @Override
    public void onAdsLoaded(List<? extends ADVSInstreamInfoModel>items) {
    }
    // (3) 広告 View のメイン画像ロード完了時
    @Override
    public void onAdMainImageLoaded(String imageUrl) {
    }
    // (4) 広告 View のアイコン画像ロード完了時
    @Override
    public void onAdIconImageLoaded(String imageUrl) {
    }
    // (5) 広告のロード失敗時
    @Override
    public void onAdsLoadedFail(String errorString) {
    }
    // (6) 広告 View の画像ロード失敗時
    @Override
    public void onAdImageLoadedFail(String imageUrl, String errorString) {
    }
    // (7) 広告 View のクリック時
    @Override
    public void onAdClicked(String redirectURL) {
    }
};
```

- RecyclerView

```java
	// AppDavis SDK から広告案件情報のロード進捗を伝えるためのコールバック群

    @Override
	// (1) メイン画像ロード完了時
    public void onAdMainImageLoaded(String imageUrl) {
    }

	// (2) アイコン画像ロード完了時
    @Override
    public void onAdIconImageLoaded(String imageUrl) {
    }

	// (3) メイン画像ロード失敗時
    @Override
    public void onAdImageLoadedFail(String imageUrl, String errorString) {
    }

	// (4) 広告のロード失敗時
    @Override
    public void onAdsLoadedFail(String errorString) {
    }

	// (5) 広告 View のクリック時
    @Override
    public void onAdClicked(String redirectURL) {
    }
```

<a name="infeed/custom/display"></a>
###カスタムインフィード広告の表示

ADVSInstreamAdPlacer を用いて以下の様に実装し、インフィード広告を表示します。

ここでは、BaseAdapter 内での利用例を示していますが、これに限るものではありません。

- ListView

```Java
private class CustomInstreamSampleAdapter extends BaseAdapter {
        ...
        @Override
        public int getCount() {
            return dataSourceList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataSourceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            // (1) 広告案件の表示形式をサポートする
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            // (1) に同じ
            return isAd(position) ? 0 : 1;
        }

        private boolean isAd(int position) {
            // (1) に同じ
            return (getItem(position) instanceof ADVSInstreamInfoModel) ? true : false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (isAd(position)) {
                // (2) 広告案件を表現するカスタム View を取り出して表示する
                convertView = adPlacer.placeAd((ADVSInstreamInfoModel) getItem(position), convertView, parent);
            } else {
                // コンテンツ View を表示する
                if (null == convertView || !TAG_DEFAULT.equals(convertView.getTag())) {
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(android.R.layout.simple_expandable_list_item_1, parent,false);
                    convertView.setTag(TAG_DEFAULT);
                }
                TextView textView = (TextView)convertView.findViewById(android.R.id.text1);
                textView.setText((String)getItem(position));
            }
            return convertView;
        }
}
...
// (3) インフィード広告の情報を取得する
@Override
public void onAdsLoaded(List<? extends ADVSInstreamInfoModel>items) {
   for (ADVSInstreamInfoModel adData : items) {
       adapter.dataSourceList.add(3, adData);
    }
    adapter.notifyDataSetChanged();
}
```

- RecyclerView

```java

static class AdViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
	ADVSInstreamAdPlacer adPlacer;
    ADVSInstreamInfoModel adData;

	...

	private static final int ITEM_VIEWTYPE_AD      = 0;
    private static final int ITEM_VIEWTYPE_CONTENT = 1;

    List<Object> persons;
    private ADVSInstreamAdPlacer adPlacer;

    RVAdapter(List<Object> persons, ADVSInstreamAdPlacer adPlacer){
        this.persons = persons;
        this.adPlacer = adPlacer;
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    @Override
    public int getItemViewType(int position) {
        return isAd(position) ? ITEM_VIEWTYPE_AD : ITEM_VIEWTYPE_CONTENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == ITEM_VIEWTYPE_AD) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_instream_ad_view, parent, false);
            return new AdViewHolder(v, adPlacer);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
            return new PersonViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM_VIEWTYPE_AD) {
            ADVSInstreamInfoModel adInfo = (ADVSInstreamInfoModel) persons.get(position);
            AdViewHolder avh = (AdViewHolder)holder;
            avh.setData(adInfo);

            // インプレッションを送信する（重複排除などは SDK がやるため、毎回通信などは発生しない）
            adPlacer.measureImp(adInfo);
        } else {
            Person p = ((Person)persons.get(position));
            PersonViewHolder pvh = (PersonViewHolder)holder;
            pvh.personName.setText(p.name);
            pvh.personAge.setText(p.age);
            pvh.personPhoto.setImageResource(p.photoId);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private boolean isAd(int position) {
        return (persons.get(position) instanceof ADVSInstreamInfoModel) ? true : false;
    }

	// インフィード広告の情報を取得する
	@Override
    public void onAdsLoaded(List<? extends ADVSInstreamInfoModel>items) {
        App.logd("onAdsLoaded");

        int i = 1;
        for (ADVSInstreamInfoModel adData : items) {
            persons.add(i, adData);
            i += 2;
        }
        adapter.notifyDataSetChanged();
    }
```

<a name="update"></a>
# 更新履歴

[CHANGELOG](../CHANGELOG.md) をご確認ください
