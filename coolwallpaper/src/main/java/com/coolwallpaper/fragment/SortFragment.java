package com.coolwallpaper.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.coolwallpaper.R;
import com.coolwallpaper.adapter.SortAdapter;
import com.coolwallpaper.bean.SortBean;
import com.coolwallpaper.utils.CallBack;
import com.coolwallpaper.utils.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SDC on 2019/6/13.
 */

public class SortFragment extends com.coolwallpaper.base.BaseFragment {
    private String url = "http://service.picasso.adesk.com/v1/vertical/category?adult=false&first=1";

    private RecyclerView mSortRcy;
    List<SortBean.ResBean.CategoryBean> categoryList = new ArrayList<>();
    private SortAdapter sortAdapter;

    @Override
    protected void initData() {
        sortAdapter = new SortAdapter(getContext(),categoryList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        mSortRcy.setLayoutManager(gridLayoutManager);
        mSortRcy.setAdapter(sortAdapter);
        loadData();

    }

    private void loadData() {
        Map<String,String> map = new HashMap<>();
        HttpUtils.getInstance().get(url, map, new CallBack() {
            @Override
            public void success(Object o) {
                SortBean sortBean = (SortBean) o;
                if(sortBean!=null){
                    SortBean.ResBean res = sortBean.getRes();
                    List<SortBean.ResBean.CategoryBean> category = res.getCategory();
                    categoryList.addAll(category);
                    sortAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failed(Exception e) {

            }
        }, SortBean.class,"");
    }

    @Override
    protected void initView(View view) {

        mSortRcy = (RecyclerView) view.findViewById(R.id.sort_rcy);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_sort_layout;
    }

    @Override
    protected void onClickListener(View v) {

    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        view = View.inflate(getActivity(), R.layout.fragment_sort_layout, null);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        // TODO:OnCreateView Method has been created, run FindViewById again to generate code
//        initView(view);
//        return view;
//    }
}
