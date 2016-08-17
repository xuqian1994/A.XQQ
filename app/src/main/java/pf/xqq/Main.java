package pf.xqq;

import android.content.Intent;
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
import pf.xqq.unvideo.MainVideo;
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
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
//        initview2();
    }

    private void initview2() {
        String url="http://60.205.95.148/xxpt/?q=course_module_items/page/592&username=13718143756&token=3d10ce8b1d932ad89ea530e0c2042598";
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
		switch (str.substring(1, 2)) {
			case "1":
				intent = new Intent(Main.this, MainVideo.class);
				break;
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
		}
		startActivity(intent);
    }
}
