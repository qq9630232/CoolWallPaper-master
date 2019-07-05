package com.coolwallpaper.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.coolwallpaper.R;
import com.coolwallpaper.adapter.SortListAdapter;
import com.coolwallpaper.bean.SortListBean;
import com.coolwallpaper.utils.CallBack;
import com.coolwallpaper.utils.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by SDC on 2019/6/14.
 */

public class FindFragment extends com.coolwallpaper.base.BaseFragment {

    private RecyclerView mFindRcy;
    private SortListAdapter sortListAdapter;
//    http://service.picasso.adesk.com/v1/vertical/vertical?limit=30&skip=180&adult=false&first=0&order=hot
    private int page = 1;
    List<SortListBean.ResBean.VerticalBean> verticalList = new ArrayList<>();
    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

        mFindRcy = (RecyclerView) view.findViewById(R.id.find_rcy);
        sortListAdapter = new SortListAdapter(getContext(),verticalList);
        mFindRcy.setLayoutManager(new GridLayoutManager(getContext(),3));
        mFindRcy.setAdapter(sortListAdapter);
        loadData(page);
    }

    private void loadData(int page) {
        HttpUtils.getInstance().get("http://service.picasso.adesk.com/v1/vertical/vertical?limit=30&skip=180&adult=false&first=" + page + "&order=hot", new HashMap<>(), new CallBack() {
            @Override
            public void success(Object o) {
                SortListBean bean = (SortListBean) o;
                if(bean!=null){
                    List<SortListBean.ResBean.VerticalBean> vertical = bean.getRes().getVertical();
                    verticalList.addAll(vertical);
                    sortListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failed(Exception e) {

            }
        }, SortListBean.class,"");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_find_layout;
    }

    @Override
    protected void onClickListener(View v) {

    }


}
