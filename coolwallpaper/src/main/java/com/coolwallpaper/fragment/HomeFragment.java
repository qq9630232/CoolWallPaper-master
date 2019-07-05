package com.coolwallpaper.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.coolwallpaper.R;

import pl.droidsonroids.gif.GifImageView;

;
;

/**
 * Created by SDC on 2019/6/12.
 */

public class HomeFragment extends com.coolwallpaper.base.BaseFragment {
    private GifImageView mIvLoading;
    private RelativeLayout mRlLoading;
    private TabLayout mTbIndicator;
    private ViewPager mVpPaper;
    private String title1;//一级标题
    private String[] subTitles;//二级标题
    @Override
    protected void initData() {
        init();
    }

    @Override
    protected void initView(View view) {

        mIvLoading = (GifImageView) view.findViewById(R.id.iv_loading);
        mRlLoading = (RelativeLayout) view.findViewById(R.id.rl_loading);
        mTbIndicator = (TabLayout) view.findViewById(R.id.tb_indicator);
        mVpPaper = (ViewPager) view.findViewById(R.id.vp_paper);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int setContentView() {
        return R.layout.home_fragment_layout;
    }
    //初始化
    private void init() {
        //初始化的数据
        title1 = "壁纸";
        subTitles = new String[]{"世界风光", "动物", "明星", "影视", "日韩明星", "摄影", "游戏"};
        //设置适配器
        PaperViewPagerAdapter adapter = new PaperViewPagerAdapter(getContext(), title1, subTitles,getChildFragmentManager());
        mVpPaper.setAdapter(adapter);
        //设置指示器
        mTbIndicator.setupWithViewPager(mVpPaper);

    }
    @Override
    protected void onClickListener(View v) {

    }
    //viewpager适配器
    class PaperViewPagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {

        public PaperViewPagerAdapter(Context context, String title1, String[] subTitles, FragmentManager childFragmentManager) {
            super(childFragmentManager);

        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            ListFragment fragment = new ListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("TITLE_1", title1);
            bundle.putString("TITLE_2", subTitles[position]);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return subTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return subTitles[position];
        }
    }
}
