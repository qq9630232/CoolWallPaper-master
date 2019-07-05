package com.coolwallpaper.utils;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.TextView;

import com.coolwallpaper.MainActivity;
import com.coolwallpaper.R;

import java.util.List;

/**
 * Created by Administrator on 2017/11/13.
 */

public class MainModel {

    /**
     * @param manager
     * @param fragmentList
     * @param showTag
     * @param needAdd
     */
    public void setShowFragment(FragmentManager manager, List<Fragment> fragmentList, int showTag, boolean needAdd) {

        FragmentTransaction transaction = manager.beginTransaction();

        for (int i = 0; i < fragmentList.size(); i++) {
            //判断是否需要添加fragment
            if (needAdd) {
                //添加fragment
                transaction.add(R.id.home_container, fragmentList.get(i));
            }
            //判断显示 和 隐藏
            if (i == showTag) {
                transaction.show(fragmentList.get(i));
            } else {
                transaction.hide(fragmentList.get(i));
            }
        }
        transaction.commitAllowingStateLoss();

    }


    //设置图标的颜色
    private void setIconColor(ImageView icon, int r, int g, int b, int a) {
        float[] colorMatrix = new float[]{
                0, 0, 0, 0, r,
                0, 0, 0, 0, g,
                0, 0, 0, 0, b,
                0, 0, 0, (float) a / 255, 0
        };
        icon.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }
    /**
     * @param tvList
     * @param imgList
     * @param showTag
     */
    public void setShowFragmentTextStyle(Context context, List<TextView> tvList, List<ImageView> imgList, int showTag) {

        if (tvList.size() != imgList.size()) {
            return;
        }

        //设置字体颜色   设置选择的button背景图片
        for (int i = 0; i < tvList.size(); i++) {
            //设置字体颜色
            if (i == showTag) {
                tvList.get(i).setTextColor(ResourceTools.getColor(context, R.color.orange));
            } else {
                tvList.get(i).setTextColor(ResourceTools.getColor(context, R.color.gray));
            }

        }


        //设置选择的button背景图片
        if (showTag == MainActivity.home_Home_Tag) {
            //首页选中
            ImageView mDreamView = imgList.get(MainActivity.home_Home_Tag);
            setIconColor(mDreamView,255,165,0,255);
            ImageView mSearchView = imgList.get(MainActivity.home_Search_tag);
            setIconColor(mSearchView,128,128,128,255);
            ImageView mMeView = imgList.get(MainActivity.home_me_tag);
            setIconColor(mMeView,128,128,128,255);
            ImageView mSortView = imgList.get(MainActivity.home_Sort_tag);
            setIconColor(mSortView,128,128,128,255);
            ImageView mFindView = imgList.get(MainActivity.home_Find_Tag);
            setIconColor(mFindView,128,128,128,255);
//            imgList.get(MainActivity.home_head_news_tag).setImageDrawable(ResourceTools.getDrawable(context, R.mipmap.gray_head_news));
//            imgList.get(MainActivity.home_me_tag).setImageDrawable(ResourceTools.getDrawable(context, R.mipmap.gray_me));


        } else if (showTag == MainActivity.home_Search_tag) {
            //星座选中
//            imgList.get(MainActivity.home_head_news_tag).setImageDrawable(ResourceTools.getDrawable(context, R.mipmap.gray_head_news));
//            imgList.get(MainActivity.home_me_tag).setImageDrawable(ResourceTools.getDrawable(context, R.mipmap.gray_me));
            ImageView mDreamView = imgList.get(MainActivity.home_Home_Tag);
            setIconColor(mDreamView,128,128,128,255);

            ImageView mConView = imgList.get(MainActivity.home_Search_tag);
            setIconColor(mConView,255,165,0,255);
            ImageView mMeView = imgList.get(MainActivity.home_me_tag);
            setIconColor(mMeView,128,128,128,255);
            ImageView mSortView = imgList.get(MainActivity.home_Sort_tag);
            setIconColor(mSortView,128,128,128,255);
            ImageView mFindView = imgList.get(MainActivity.home_Find_Tag);
            setIconColor(mFindView,128,128,128,255);
        } else if (showTag == MainActivity.home_me_tag) {
            //头条选中
//            imgList.get(MainActivity.home_head_news_tag).setImageDrawable(ResourceTools.getDrawable(context, R.mipmap.blue_head_news));
//            imgList.get(MainActivity.home_me_tag).setImageDrawable(ResourceTools.getDrawable(context, R.mipmap.gray_me));

            ImageView mDreamView = imgList.get(MainActivity.home_Home_Tag);
            setIconColor(mDreamView,128,128,128,255);

            ImageView mSearchView = imgList.get(MainActivity.home_Search_tag);
            setIconColor(mSearchView,128,128,128,255);
            ImageView mMeView = imgList.get(MainActivity.home_me_tag);
            setIconColor(mMeView,255,165,0,255);
            ImageView mSortView = imgList.get(MainActivity.home_Sort_tag);
            setIconColor(mSortView,128,128,128,255);
            ImageView mFindView = imgList.get(MainActivity.home_Find_Tag);
            setIconColor(mFindView,128,128,128,255);


        } else if(showTag == MainActivity.home_Sort_tag){
            //我的选中
            ImageView mDreamView = imgList.get(MainActivity.home_Home_Tag);
            setIconColor(mDreamView,128,128,128,255);

            ImageView mSearchView = imgList.get(MainActivity.home_Search_tag);
            setIconColor(mSearchView,128,128,128,255);
            ImageView mMeView = imgList.get(MainActivity.home_me_tag);
            setIconColor(mMeView,128,128,128,255);
            ImageView mSortView = imgList.get(MainActivity.home_Sort_tag);
            setIconColor(mSortView,255,165,0,255);
            ImageView mFindView = imgList.get(MainActivity.home_Find_Tag);
            setIconColor(mFindView,128,128,128,255);

        } else if(showTag == MainActivity.home_Find_Tag){
            //我的选中
            ImageView mDreamView = imgList.get(MainActivity.home_Home_Tag);
            setIconColor(mDreamView,128,128,128,255);

            ImageView mSearchView = imgList.get(MainActivity.home_Search_tag);
            setIconColor(mSearchView,128,128,128,255);
            ImageView mMeView = imgList.get(MainActivity.home_me_tag);
            setIconColor(mMeView,128,128,128,255);
            ImageView mSortView = imgList.get(MainActivity.home_Sort_tag);
            setIconColor(mSortView,128,128,128,255);
            ImageView mFindView = imgList.get(MainActivity.home_Find_Tag);
            setIconColor(mFindView,255,165,0,255);

        }

    }

}
