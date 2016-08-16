package pf.xqq;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RunnableFuture;

import pf.xqq.adapter.AdapterMain;
import pf.xqq.rxjava.RxVideoTime;
import pf.xqq.rxjava.RxVideoTime2;
import pf.xqq.rxjava.post;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 写在最前面：让我欢喜让我忧。
 *
 * 其实你对我有一点点动心了，为什么要这样啊。
 *
 * */
public class Main extends AppCompatActivity {



    private XRecyclerView mXRView;
    private AdapterMain mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        initView();
        initview2();
    }

    private void initview2() {
        String url="http://60.205.95.148/xxpt/?q=course_module_items/page/592&username=13718143756&token=3d10ce8b1d932ad89ea530e0c2042598";


        WebView webView = (WebView) findViewById(R.id.xwv_new_show_web);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//		webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new MyWebViewClient());
//        webView.setDownloadListener(new MyWebViewDownLoadListener());
    }


    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
//            waitingBar.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            super.onReceivedSslError(view, handler, error);
        }

        // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Log.e("QT","点击的连接："+url);
            //http://60.205.95.148/xxpt/sites/default/files/res0/u10645/video.mp4?vid=8620&is_resource=0

            if(url.contains("?")){
                String vid =url.substring(url.indexOf("=")+1, url.lastIndexOf("&"));
//				ALog.e("截取vid--->"+vid);
                String is_resource =url.substring(url.lastIndexOf("=")+1, url.length());
//				vid=is_resource.substring(4, is_resource.indexOf("&"));
//                ALog.e("截取vid--->"+vid);
//                ALog.e("截取is_resource--->"+is_resource);

//                initRxVideoInfo(deURL,vid,is_resource,course_id);
            }
            if(url.startsWith("intent://")){
                return true;
            }
//            super.shouldOverrideUrlLoading(view, url);
            if (TextUtils.isEmpty(url)){
                return true;
            }

            initRxVideoInfo(deURL,"","",course_id);
//            new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                        new post().loginByPost();
//                }
//            }).start();
            return true;//super.shouldOverrideUrlLoading(view, url);
        }
    }

    private Subscription subscription;
    private String course_id;
    private String deURL;
    private void initRxVideoInfo(String deURL, String vid, String is_resource, String course_id) {
        subscription = new RxVideoTime2(deURL,vid,is_resource,course_id).getGistObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {

                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(String gist) {}});
    }
    private void initView() {
        mXRView = (XRecyclerView) findViewById(R.id.xrv_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRView.setLayoutManager(layoutManager);

        mXRView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRView.setArrowImageView(R.mipmap.iconfont_downgrey);

        mXRView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                toRefresh();
            }

            @Override
            public void onLoadMore() {
                toLoadMore();
            }
        });
        mXRView.setRefreshing(true);
    }

    private void toLoadMore() {
        toHandler();
    }

    private void toHandler(final int start, final int end) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                for (int i = start; i < end; i++) {
//                    listData.add(allgist.get(i));
                }
                mAdapter.notifyDataSetChanged();
                mXRView.loadMoreComplete();
            }
        }, 1000);
    }

    private void toHandler() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mAdapter.notifyDataSetChanged();
                mXRView.loadMoreComplete();
            }
        }, 1000);
    }

    private void toRefresh() {
        useData();
    }

    private void useData() {
        String[] sData = getResources().getStringArray(R.array.main);
        List<String> mData = Arrays.asList(sData);
        mAdapter = new AdapterMain(mData, Main.this);
        mXRView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mXRView.refreshComplete();
    }


    public void onClickItem(String str) {
//		switch (str.substring(1, 2)) {
//			case "1":
//				intent = new Intent(MainActivity.this, FirstActivity.class);
//				break;
//			case "2":
//				intent = new Intent(MainActivity.this, ShowRx.class);
//				break;
//			case "3":
//				intent = new Intent(MainActivity.this, ShowLine.class);
//				break;
//			case "4":
//				intent = new Intent(MainActivity.this, ShowFMFrame.class);
//				break;
//			case "5":
//				intent = new Intent(MainActivity.this, ShowFriends.class);
//				break;
//			case "6":
//				intent = new Intent(MainActivity.this, NoteActivity.class);
//				break;
//		}
//		startActivity(intent);
    }
}
