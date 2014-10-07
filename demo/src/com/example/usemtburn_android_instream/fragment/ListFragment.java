package com.example.usemtburn_android_instream.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.usemtburn_android_instream.ItemModel;
import com.example.usemtburn_android_instream.RefreshableInterface;
import com.example.usemtburn_android_instream.activity.ItemViewActivity;
import com.example.usemtburn_android_instream.adapter.ItemAdapter;
import com.example.usemtburn_android_instream.view.RefreshableListView;
import com.example.usemtburn_android_sdk.App;
import com.example.usemtburn_android_sdk.R;

//-----------------------------------------------------------------------
//SDK related package
//-----------------------------------------------------------------------
import com.mtburn.android.sdk.AppDavis;
import com.mtburn.android.sdk.instream.ADVSInstreamAdAdapter;
import com.mtburn.android.sdk.instream.ADVSInstreamAdLoadListener;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

public class ListFragment extends Fragment implements RefreshableInterface, ADVSInstreamAdLoadListener {

    private static final String POSITION  = "position";
    private static final String URL       = "url";
    private int tabPosition;
    private String rssUrl;
    
    private ArrayList<ItemModel> listData;
    private ItemAdapter itemAdapter;
    private RefreshableListView postListView;
    private boolean isRefreshLoading = true;
    private boolean isLoading = false;
    private enum RSSXMLTag {
        TITLE, DATE, LINK, CONTENT, GUID, IGNORETAG;
    }
    
    // AppDavis SDK が提供する、ListAdapter をラップするクラス
    private ADVSInstreamAdAdapter<ItemAdapter> advsAdapter;

    public static ListFragment newInstance(int position, String url) {
        ListFragment cardsFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        bundle.putString(URL, url);
        cardsFragment.setArguments(bundle);
        return cardsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabPosition = getArguments().getInt(POSITION);
        rssUrl = getArguments().getString(URL);
        
        listData = new ArrayList<ItemModel>();
        itemAdapter = new ItemAdapter(this.getActivity(), R.layout.item1x1, listData);
        
        // ADVSInstreamAdAdapter<ItemAdapter> の実装クラスを生成して、リスナーをセットする
        List<Integer> positionList = new ArrayList<Integer>(Arrays.asList(5,10,15,20,25));
        advsAdapter = AppDavis.createInstreamAdAdapter(getActivity().getApplicationContext(), itemAdapter, getAdSpotId(tabPosition), 5, positionList);
        advsAdapter.setAdListener(this);
        
        this.startFresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_cards, container, false);
        postListView = (RefreshableListView) frameLayout.findViewById(R.id.postListView);

        postListView.setAdapter(advsAdapter);
        
        postListView.setOnRefresh(this);
        postListView.setOnItemClickListener(onItemClickListener);
        
        if (listData.size() < 1) {
            postListView.onRefreshStart();
        }
        
        // # 31. Fixed a freeze bug on v3.x
        if (Build.VERSION_CODES.HONEYCOMB <= Build.VERSION.SDK_INT &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            View cardPaperView = frameLayout.findViewById(R.id.card_paper_view);
            View cardShadowView = frameLayout.findViewById(R.id.card_shadow_view);
            makeTransparent(cardPaperView, cardShadowView);
        }

        return frameLayout;
    }
    
    // # 31. Fixed a freeze bug on v3.x
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void makeTransparent(View cardPaperView, View cardShadowView) {
        cardPaperView.setAlpha(0);
        cardShadowView.setAlpha(0);
    }
    
    @Override
    public void onResume() {
        super.onResume();
    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            ItemModel data = listData.get(arg2 - 1);

            Bundle postInfo = new Bundle();
            postInfo.putString("url", data.link);

            Intent postviewIntent = new Intent(getActivity(), ItemViewActivity.class);
            postviewIntent.putExtras(postInfo);
            startActivity(postviewIntent);
        }
    };

    private class RssDataController extends
            AsyncTask<String, Integer, ArrayList<ItemModel>> {
        private RSSXMLTag currentTag;

        @Override
        protected ArrayList<ItemModel> doInBackground(String... params) {
            String urlStr = params[0];
            InputStream is = null;
            ArrayList<ItemModel> postDataList = new ArrayList<ItemModel>();
            try {
                URL url = new URL(urlStr);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setReadTimeout(10 * 1000);
                connection.setConnectTimeout(10 * 1000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                is = connection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory
                        .newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(is, null);

                int eventType = xpp.getEventType();
                ItemModel pdData = null;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {

                    } else if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equals("item")) {
                            pdData = new ItemModel();
                            currentTag = RSSXMLTag.IGNORETAG;
                        } else if (xpp.getName().equals("title")) {
                            currentTag = RSSXMLTag.TITLE;
                        } else if (xpp.getName().equals("link")) {
                            currentTag = RSSXMLTag.LINK;
                        } else if (xpp.getName().equals("pubDate")) {
                            currentTag = RSSXMLTag.DATE;
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (xpp.getName().equals("item")) {
                            pdData.imageResId = "rss_" + tabPosition; 
                            postDataList.add(pdData);
                        } else {
                            currentTag = RSSXMLTag.IGNORETAG;
                        }
                    } else if (eventType == XmlPullParser.TEXT) {
                        String content = xpp.getText();
                        content = content.trim();
                        if (pdData != null) {
                            switch (currentTag) {
                            case TITLE:
                                if (content.length() != 0) {
                                    if (pdData.title != null) {
                                        pdData.title += content;
                                    } else {
                                        pdData.title = content;
                                    }
                                }
                                break;
                            case LINK:
                                if (content.length() != 0) {
                                    if (pdData.link != null) {
                                        pdData.link += content;
                                    } else {
                                        pdData.link = content;
                                    }
                                }
                                break;
                            case DATE:
                                if (content.length() != 0) {
                                    if (pdData.date != null) {
                                        pdData.date += content;
                                    } else {
                                        pdData.date = content;
                                    }
                                }
                                break;
                            default:
                                break;
                            }
                        }
                    }

                    eventType = xpp.next();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return postDataList;
        }

        @Override
        protected void onPostExecute(ArrayList<ItemModel> result) {
            for (int i = 0; i < result.size(); i++) {
                listData.add(result.get(i));
            }

            itemAdapter.notifyDataSetChanged();

            isLoading = false;
            
            if (postListView == null) {
                return;
            }

            if (isRefreshLoading) {
                postListView.onRefreshComplete();
            } else {
                postListView.onLoadingMoreComplete();
            }
            
            // 広告案件情報のロードを開始する
            advsAdapter.loadAd();
        }
    }

    @Override
    public void startFresh() {
        if (!isLoading) {
            isRefreshLoading = true;
            isLoading = true;
            new RssDataController().execute(rssUrl);
        } else {
            if (postListView != null) {
                postListView.onRefreshComplete();
            }
        }
    }

    @Override
    public void startLoadMore() {
        if (!isLoading) {
            isRefreshLoading = false;
            isLoading = true;
            new RssDataController().execute(rssUrl);
        } else {
            postListView.onLoadingMoreComplete();
        }
    }
    
    // AppDavis SDK から広告案件情報のロード進捗を伝えるためのコールバック群
    @Override
    public void instreamAdLoaderDidStartLoadingAd()
    {
        App.logd("instreamAdLoaderDidStartLoadingAd:tabPosition=" + tabPosition);
    }
    @Override
    public void instreamAdLoaderDidFinishLoadingAds()
    {
        App.logd("instreamAdLoaderDidFinishLoadingAds:tabPosition=" + tabPosition);
    }
    @Override
    public void instreamAdLoaderDidFinishLoadingMainImage(String imageUrl)
    {
        App.logd("instreamAdLoaderDidFinishLoadingMainImage:tabPosition=" + tabPosition + ":image=" + imageUrl);
    }
    @Override
    public void instreamAdLoaderDidFinishLoadingIconImage(String imageUrl)
    {
        App.logd("instreamAdLoaderDidFinishLoadingIconImage:tabPosition=" + tabPosition + ":image=" + imageUrl);
    }
    @Override
    public void instreamAdLoaderDidFailToLoadAdError(String errorString)
    {
        App.logd("instreamAdLoaderDidFailToLoadAdError:tabPosition=" + tabPosition + ":error=" + errorString);
    }
    @Override
    public void instreamAdLoaderDidFailToLoadImageError(String errorString)
    {
        App.logd("instreamAdLoaderDidFailToLoadImageError:tabPosition=" + tabPosition + ":error=" + errorString);
    }
    @Override
    public void instreamAdLoaderDidClick(String redirectUrl)
    {
        App.logd("instreamAdLoaderDidClick:tabPosition=" + tabPosition + ":redirectUrl=" + redirectUrl);
    }
    // --- ここまでで、コールバック群おわり
    
    // Utility function（広告枠 ID をリソースから取得する）
    private String getAdSpotId(int position) {
        String name = "media_id_"+getString(R.integer.media_id)+"_adspot";
        int resId = getResources().getIdentifier(name, "array", getActivity().getPackageName());
        String[] adSpotIds = getResources().getStringArray(resId);
        return adSpotIds[position];
    }
}
