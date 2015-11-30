# Table of Contents

* [Before You Start](#start)
	* [AndroidManifest.xml](#start/manifest)
	* [Acquiring the Media ID](#start/media_id)
	* [Initializing within the Code](#start/init)
* [In-feed Advertisements](#infeed)
	* [Acquiring the Advertisement Id](#infeed/adspot_id)
	* [Simple In-feed Advertisements](#infeed/simple)
		* [Displaying Simple In-feed Advertisements](#infeed/simple/display)
		* [Acquiring Events when Displaying Simple In-feed Advertisements](#infeed/simple/event)
		* [Additional Loading of Simple In-feed Advertisements](#infeed/simple/additional_load)
		* [Usable Simple In-feed Advertisement Formats](#infeed/simple/format)
	* [Custom In-feed Advertisements](#infeed/custom)
		* [Loading Custom In-feed Advertisements](#infeed/custom/load)
		* [Parameters Used When Displaying Custom In-feed Advertisements](#infeed/custom/display_param)
		* [Acquiring Events When Displaying Custom In-feed Advertisements](#infeed/custom/event)
		* [Displaying Custom In-feed Advertisements](#infeed/custom/display)
        * [When Using Other than ListView](#infeed/custom/recyclerview)
* [Update History](#update)

<a name="start"></a>
# Before You Start

javadoc is stored under /doc within the downloaded SDK project.

This summarizes the information related to the public modifiers for each package appearing in this guide.

Browse doc/index.html using your favorite browser.

<a name="start/manifest"></a>
## AndroidManifest.xml

Write the following contents in AndroidManifest.xml 

```Java
<!—Necessary permissions-->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<application>
<!-- Include in order to use the Google play service library->
<meta-data
    android:name="com.google.android.gms.version"
    android:value="@integer/google_play_services_version" />
<!—Include in order to send notifications of the application installation results->
<receiver android:name="com.mtburn.android.sdk.util.AppInstallationReceiver">
            <intent-filter>
                <action android:name="android.intent.action.PACKAGE_ADDED" />
                <data android:scheme="package" />
            </intent-filter>
        </receiver>
</application>

```

<a name="start/media_id"></a>
## Acquiring the Media ID

Register from the Management Screen and issue the Media ID.

**(At the current time, you should address any inquiries to your representative)**

This Media ID is used to identify the application, so take care not to forget it.

### Test ID

Media ID "1" or "2" can be used for test purposes. Dummy data can be displayed to check the operation.

<a name="start/init"></a>
## Initialization within the Code

```Java
import com.mtburn.android.sdk.AppDavis;
...
public class MyActivity extends Activity {
...
AppDavis.init(this.getApplicationContext(), "YOUR_MEDIA_ID");
...
}
```

<a name="infeed"></a>
# In-feed advertisements

<a name="infeed/adspot_id"></a>
## Acquisition of Ad Spot ID

In addition to acquiring a Media ID, register the adspot in advance. Set the following information to output the Ad Spot ID.

- Ad Spot name
- [Advertising format](#Usable Simple In-feed Advertisement Formats)
- Number of advertisements
- Advertising location array
- HTML （only when using WebView format
）

As you are clicking an actual project, this may be less than the following number of advertisements. In this case, you should contact a representative.

After setting the "2" to Media ID for test purposes, set the following Ad Spot ID (adspot_id)

```
Ad Spot ID: NDQ0OjMx
Advertisement format: ThumnailMiddle
Number of advertisements per request: 2
Advertisement load array: 3,6
```

```
Advertisement ID: OTA2OjMy
Advertisement image size: ThumnailSmall
Number of advertisements per request: 1
Advertisement location array: 4
```

```
Ad Spot ID: ODEzOjMz
Advertisement image size: LandscapePhoto
Number of advertisements per request: 3
Advertisement location array: 0,1,2
```

```
Ad Spot ID: OTIyOjM0
Advertisement image size: PhotoBottom
Number of advertisements per request: 4
Advertisement location array: 5,7,9,10
```

```
Ad Spot ID: NzA2OjM1
Advertisement image size: PhotoMiddle
Number of advertisements per request: 6
Advertisement location array: 2,4,6,8,10,12
```

```
Ad Spot ID: MzA3OjM2
Advertisement image size: TextOnly
Number of advertisements per request: 6
Advertisement location array: 5,10,15,20,25,30
```

```
Ad Spot ID: MTI2OjU1
Advertisement image size: WebView (Small)
Number of advertisements per request: 3
Advertisement location array: 3,6,9
```

```
Ad Spot ID: OTkzOjU2
Advertisement image size: WebView (Medium)
Number of advertisements per request: 3
Advertisement location array: 3,6,9
```

```
Ad Spot ID: MzEzOjU3
Advertisement image size: WebView (Large)
Number of advertisements per request: 3
Advertisement location array: 3,6,9
```

**At present, you should contact your representative when setting up a registration for new adspots.**

<a name="infeed/simple"></a>
## Simple In-feed Advertisements

When using view components with ListView and ArrayAdapter, this can be implemented simply.

<a name="infeed/simple/display"></a>
### Displaying Simple In-feed Advertisements

Implement in the following way using ADVSInstreamAdAdapter to display in-feed advertisements.

```Java
// (1) Import the necessary package
import com.mtburn.android.sdk.AppDavis;
import com.mtburn.android.sdk.instream.ADVSInstreamAdAdapter;

public class MyActivity extends Activity {

    // (2) Define ADVSInstreamAdAdapter
    private ADVSInstreamAdAdapter<ArrayAdapter> advsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
     	...
     	// (3) Initialize AppDavis SDK
     	AppDavis.init(this.getApplicationContext(), "YOUR_MEDIA_ID");
     	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getApplicationContext(), R.layout.rowdata, data);

     	// (4) Set the adapter and adspot ID in which the in-feed advertisement is to be inserted
     	advsAdapter = AppDavis.createInstreamAdAdapter(this.getApplicationContext(), arrayAdapter, "YOUR_ADSPOT_ID");
     	...
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     	...
     	listView = (ListView) frameLayout.findViewById(R.id.listView);

     	// (5) Set the adapter to the list view into which you wish to insert the advertisement
     	listView.setAdapter(advsAdapter);
     	...
    }
    ...
    // Wait until the media-like data acquisition completes
    ...
    // (6) Start loading the in-feed advertisement
    advsAdapter.loadAd();
}
```

The number of advertisements per request and advertisement location array can be controlled using API such as `AppDavis createInstreamAdAdapter(Context context, T originalAdapter, String adSpotId, int adCount, Listpositions)` etc.

<a name="infeed/simple/event"></a>
### Acquisition of Events When Displaying Simple In-feed Advertisements

When displaying in-feed advertisements, there may be times when you want to receive notifications.

In this case, set ADVSInstreamAdLoadListener in ADVSInstreamAdAdapter.

```Java
ADVSInstreamAdAdapter adapter;
ADVSInstreamAdLoadListener listener;

//(1) Set listener in ADVSInstreamAdAdapter 
adapter.setAdListener(listener);

listener = new ADVSInstreamAdLoadListener() {

    //(2) When advertisement loading begins
    @Override
    public void instreamAdLoaderDidStartLoadingAd()
    {
    }
    //(3) When advertisement loading is completed
    @Override
    public void instreamAdLoaderDidFinishLoadingAds()
    {
    }
    //(4)When loading of the advertising view main screen is completed
    @Override
    public void instreamAdLoaderDidFinishLoadingMainImage(String imageUrl)
    {
    }
    //(5) When loading of advertisement View icon images is completed
    @Override
    public void instreamAdLoaderDidFinishLoadingIconImage(String imageUrl)
    {
    }
    //(6) When advertisement loading fails
    @Override
    public void instreamAdLoaderDidFailToLoadAdError(String errorString)
    {
    }
    //(7) When advertisement View loading fails
    @Override
    public void instreamAdLoaderDidFailToLoadImageError(String errorString)
    {
    }
    //(8) When advertising click processing is completed
    @Override
    public void instreamAdLoaderDidClick(String redirectUrl)
    {
    }
};
```

<a name="infeed/simple/additional_load"></a>
### Additional Loading of Simple In-feed Advertisements

When this is a UI that reads in an additional feed when the bottom of the site is reached, it is possible to add advertisement loading.

```Java
public void loadMore()
{
	// Load an additional advertisement and insert the advertisement location array into an appropriate location within the table.
	advsAdapter.loadAd();
}
```

<a name="infeed/simple/format"></a>
### Usable Simple In-feed Advertisement Formats

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

By including HTML, it is possible to draw in WebView within the application.

<a name="infeed/custom"></a>
## Custom In-feed Advertisements

When you wish to display in a more flexible way than with a simple in-feed advertisement, you can use the custom in-feed advertisement function.

When implementing, be sure to include wording to allow the user to recognize and understand that the location is displaying an advertisement.

- The proper text can be displayed each advertisement by using parameter `displayedAdvertiser` .

The following items assume use of ListView. When using other than ListView（for example RecyclerView), refer to [When Using Other than ListView](#infeed/custom/recyclerview)
.

<a name="infeed/custom/load"></a>
### Loading Custom In-feed Advertisements

Implement in the following way using ADVSInstreamAdPlacer to display the in-feed advertisement.

```Java
// (1) Import the necessary package
import com.mtburn.android.sdk.AppDavis;
import com.mtburn.android.sdk.instream.ADVSInstreamAdPlacer;
import com.mtburn.android.sdk.instream.InstreamAdViewBinderImpl;
import com.mtburn.android.sdk.model.ADVSInstreamInfoModel;

public class MyActivity extends Activity {

    // (2) Define ADVSInstreamAdPlacer
    private ADVSInstreamAdPlacer adPlacer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     	// (3) Initialize AppDavis SDK
     	AppDavis.init(getActivity().getApplicationContext(), "YOUR_MEDIA_ID");

     	// (4) Generate ADVSInstreamAdPlacer
     	adPlacer = AppDavis.createInstreamAdPlacer(getActivity().getApplicationContext(), "YOUR_ADSPOT_ID");

     	// (5) Allocate the advertising project information to your chosen View (refer to the following parameter items used to display custom in-feed advertisements)
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

   // (6) Start loading the in-feed advertisements
   adPlacer.loadAd();

   ...

   // (7) Use [ViewHolder pattern](http://developer.android.com/training/improving-layouts/smooth-scrolling.html) to improve performance.
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

<a name="infeed/custom/display_param"></a>
### Parameters Used for Displaying Custom In-feed Advertisements

| Parameter name | Explanation | Example |
| --- | --- | --- |
| title | Title text (full-size 20 characters or less) | `TestAd` |
| content | explanation/introductory text (full-size 40-70 characters) | `Test advertisement` |
| displayedAdvertiser | Title of the advertisement’s main name | `Beverage company A` |
| iconImage | Icon-type square images (114 × 114 pixel fixed) | Call using the following method |
| mainImage | Banner-type rectangular images etc. (changeable size for each Ad Spot ID) | Call using the following method |

<a name="infeed/custom/event"></a>
### Acquisition of Events When Displaying Custom In-feed Advertisements

When displaying custom in-feed advertisements, there may be times when you want to receive notifications.

In this case, set ADVSInstreamAdPlacerListener in ADVSInstreamAdPlacer .

```Java
ADVSInstreamAdPlacer adPlacer;
ADVSInstreamAdPlacerListener listener;

// (1) Set listener in ADVSInstreamAdAdapter 
adPlacer.setAdListener(listener);

listener = new ADVSInstreamAdPlacerListener() {

    // (2) When loading of an advertisement is complete
    // 後述する、ADVSInstreamAdPlacer の placeAd(ADVSInstreamInfoModel advsinstreaminfomodel, View view, ViewGroup viewgroup) に渡すために、広告案件情報を取得します
    @Override
    public void onAdsLoaded(List<? extends ADVSInstreamInfoModel>items) {
    }
    // (3) When loading of the advertisement View main images is complete
    @Override
    public void onAdMainImageLoaded(String imageUrl) {
    }
    // (4) When loading of the advertisement View icon images is complete
    @Override
    public void onAdIconImageLoaded(String imageUrl) {
    }
    // (5) When advertising loading fails
    @Override
    public void onAdsLoadedFail(String errorString) {
    }
    // (6) When advertisement View image load fails
    @Override
    public void onAdImageLoadedFail(String imageUrl, String errorString) {
    }
    // (7) When clicking advertisement View
    @Override
    public void onAdClicked(String redirectURL) {
    }
};
```

<a name="infeed/custom/display"></a>
### Displaying Custom In-feed Advertisements

Implement in the following way using ADVSInstreamAdPlacer and display the in-feed advertisements.

The example given here is within the BaseAdapter, but it is not limited to such cases.

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
            // (1) Supports display formats for advertising projects
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            // Same as (1)
            return isAd(position) ? 0 : 1;
        }

        private boolean isAd(int position) {
            // Same as (1)
            return (getItem(position) instanceof ADVSInstreamInfoModel) ? true : false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (isAd(position)) {
                // (2) Extract and display the custom View for expressing advertising projects
                convertView = adPlacer.placeAd((ADVSInstreamInfoModel) getItem(position), convertView, parent);
            } else {
                // Display content View 
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
// (3) Acquire in-feed advertisement information
@Override
public void onAdsLoaded(List<? extends ADVSInstreamInfoModel>items) {
   for (ADVSInstreamInfoModel adData : items) {
       adapter.dataSourceList.add(3, adData);
    }
    adapter.notifyDataSetChanged();
}
```

<a name="infeed/custom/recyclerview"></a>
### When Using functions other than ListView

Check [the sample code using RecyclerView](https://github.com/mtburn/MTBurn-Android-SDK-Install-Guide/tree/master/demo/src/com/example/usemtburn_android_instream/recyclerview)

The above sample used ListView to implement InstreamAdViewBinderImpl and so does not need to expressly make a call. 

- The above sample used ListView to implement [InstreamAdViewBinderImpl](http://mtburn.github.io/MTBurn-Android-SDK-Install-Guide/javadoc/latest/com/mtburn/android/sdk/instream/InstreamAdViewBinderImpl.html) and so does not need to expressly make a call. 
- When using functions other than ListView, such as RecyclerView , it is not possible to use InstreamAdViewBinderImpl; thus, it is necessary to call the newly available <a href="http://mtburn.github.io/MTBurn-Android-SDK-Install-Guide/javadoc/latest/com/mtburn/android/sdk/instream/ADVSInstreamAdPlacer.html#measureImp(com.mtburn.android.sdk.model.ADVSInstreamInfoModel)">measureImp</a>
 when displaying the advertisement and <a href="http://mtburn.github.io/MTBurn-Android-SDK-Install-Guide/javadoc/latest/com/mtburn/android/sdk/instream/ADVSInstreamAdPlacer.html#sendClickEvent(com.mtburn.android.sdk.model.ADVSInstreamInfoModel)">sendClickEvent</a>
 when clicking the advertisement. 
- When using ListView, it is possible to use a clear transmission API instead of InstreamAdViewBinderImpl.
 - In this case, be careful as to not use InstreamAdViewBinderImpl, as an unexpected result may occur when trying to post results in duplicate.

<a name="update"></a>
# Update History

Confirm [CHANGELOG](../CHANGELOG.md) 
