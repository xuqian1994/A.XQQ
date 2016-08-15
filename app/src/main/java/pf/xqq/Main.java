package pf.xqq;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.Arrays;
import java.util.List;

import pf.xqq.adapter.AdapterMain;

/**
 * 写在最前面：倩倩想你了。
 *
 * 该项目主要用于和倩倩在Github上的协同开发。
 * */
public class Main extends AppCompatActivity {



    private XRecyclerView mXRView;
    private AdapterMain mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
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
