package com.coolwallpaper.fragment;

import android.view.View;

import com.coolwallpaper.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by SDC on 2019/6/13.
 */

public class JokeFragment extends com.coolwallpaper.base.BaseFragment {
    private PullToRefreshListView mJokeList;
    public static final String baseJokeUrl = "http://v.juhe.cn/joke/content/list.php";//笑话数据的base
    private final String SORT_TYPE_DESC = "desc";//desc:指定时间之前发布的
    private int page = 1;
    private int pageSize = 10;
    @Override
    protected void initData() {
        loadData(page);
    }

    private void loadData(int page) {
        OkHttpClient client = new OkHttpClient();
        String time = System.currentTimeMillis() / 1000 + "";
        Request request = new Request.Builder().
                url("http://v.juhe.cn/joke/content/list.php?key=85a62029fad79fcaf84fa029d7fa1861&"+
                "sort=desc&pagesize=10&time="+time+"&page="+page).
        build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    @Override
    protected void initView(View view) {

        mJokeList = (PullToRefreshListView) view.findViewById(R.id.joke_list);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_joke_layout;
    }

    @Override
    protected void onClickListener(View v) {

    }


}
