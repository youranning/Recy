package bwie.com.iwant;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnRefreshListener, OnLoadMoreListener {

    private IRecyclerView recyclerView;
    int num = 20;
    int init = 0;
    private List<String> list;
    private MyAdapter adapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    recyclerView.setRefreshing(false);

                    break;
                case 2:

//                    loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);

                    break;
            }
        }
    };
    private LoadMoreFooterView loadMoreFooterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (IRecyclerView) findViewById(R.id.recyle);
        list = new ArrayList<>();
        for (int i = init; i < init + num; i++) {
            list.add("条目" + i);
        }
        adapter = new MyAdapter(getApplication(), list);
//        recyclerView.setAdapter(adapter);
        recyclerView.setIAdapter(adapter);
        final LinearLayoutManager manager = new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(manager);

        recyclerView.setLoadMoreEnabled(true);
        recyclerView.setRefreshEnabled(true);
        recyclerView.setOnRefreshListener(this);
        recyclerView.setOnLoadMoreListener(this);
//        loadMoreFooterView = (LoadMoreFooterView)recyclerView.getLoadMoreFooterView();
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (AbsListView.OnScrollListener.SCROLL_STATE_IDLE == newState) {

                    //当前滚动 停止
                    int findLastCompletelyVisibleItemPosition = manager.findLastCompletelyVisibleItemPosition();
                    int findFirstCompletelyVisibleItemPosition = manager.findFirstCompletelyVisibleItemPosition();

                    int findFirstVisibleItemPosition = manager.findFirstVisibleItemPosition();
                    int findLastVisibleItemPosition = manager.findLastVisibleItemPosition();


                    System.out.println("findLastCompletelyVisibleItemPosition = " + findLastCompletelyVisibleItemPosition);
                    System.out.println("findFirstCompletelyVisibleItemPosition = " + findFirstCompletelyVisibleItemPosition);
                    System.out.println("findFirstVisibleItemPosition = " + findFirstVisibleItemPosition);
                    System.out.println("findLastVisibleItemPosition = " + findLastVisibleItemPosition);

                }


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void onRefresh() {
        recyclerView.setRefreshing(true);


        handler.sendEmptyMessageDelayed(1,2000);
        list.clear();
        for (int i = init; i < init + num; i++) {
            list.add("悠悠" + i);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore() {
//        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
        handler.sendEmptyMessageDelayed(2,2000);
        for (int i = 50; i < 70; i++) {
            list.add("条目" + i);
        }
        adapter.notifyDataSetChanged();
    }
}
