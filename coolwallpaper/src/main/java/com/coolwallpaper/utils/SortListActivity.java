package com.coolwallpaper.utils;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coolwallpaper.BaseActivity;
import com.coolwallpaper.R;
import com.coolwallpaper.adapter.SortListAdapter;
import com.coolwallpaper.bean.SortListBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mLyLeft;
    /**
     * 分类
     */
    private TextView mTvTitle;
    private LinearLayout mLyTitle;
    private RecyclerView mLvPaper;
    private SortListAdapter sortListAdapter;
    private List<SortListBean.ResBean.VerticalBean> list = new ArrayList<>();
    //    http://service.picasso.adesk.com/v1/vertical/category/4e4d610cdf714d2966000003/vertical?limit=30&adult=false&first=1&order=new
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_list);
        initView();
    }

    private void initView() {
        String id = getIntent().getStringExtra("id");
        String title = getIntent().getStringExtra("title");

        mLyLeft = (ImageView) findViewById(R.id.ly_left);
        mLyLeft.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mLyTitle = (LinearLayout) findViewById(R.id.ly_title);
        mLvPaper = (RecyclerView) findViewById(R.id.lv_paper);
        if(!TextUtils.isEmpty(title)){
            mTvTitle.setText(title);
        }
        loadData(id);
        sortListAdapter = new SortListAdapter(this,list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mLvPaper.setLayoutManager(gridLayoutManager);
        mLvPaper.setAdapter(sortListAdapter);

    }

    private void loadData(String id) {
        Map<String,String> map = new HashMap<>();
        HttpUtils.getInstance().get("http://service.picasso.adesk.com/v1/vertical/category/" + id + "/vertical?limit=30&adult=false&first=1&order=new", map, new CallBack() {
            @Override
            public void success(Object o) {
                SortListBean bean = (SortListBean) o;
                List<SortListBean.ResBean.VerticalBean> vertical = bean.getRes().getVertical();
                list.addAll(vertical);
                sortListAdapter.notifyDataSetChanged();
            }

            @Override
            public void failed(Exception e) {

            }
        }, SortListBean.class,"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ly_left:
                finish();
                break;
        }
    }
}
