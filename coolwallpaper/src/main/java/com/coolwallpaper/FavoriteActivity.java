package com.coolwallpaper;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coolwallpaper.adapter.FavoriteAdapter;
import com.coolwallpaper.bean.IUserInfo;
import com.coolwallpaper.bmob.MyBmobFavourite;
import com.coolwallpaper.model.Picture;
import com.coolwallpaper.utils.UserUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by SDC on 2019/6/11.
 */

public class FavoriteActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mLyLeft;
    /**
     * 搜索结果
     */
    private TextView mTvTitle;
    private LinearLayout mLyTitle;
    private RecyclerView mFavoriteRl;
    private List<Picture> favouriteArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        initView();

    }

    private void initView() {
        mLyLeft = (ImageView) findViewById(R.id.ly_left);
        mLyLeft.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setOnClickListener(this);
        mLyTitle = (LinearLayout) findViewById(R.id.ly_title);
        mFavoriteRl = (RecyclerView) findViewById(R.id.favorite_rl);
        mTvTitle.setText("我的收藏");
        FavoriteAdapter favoriteAdapter = new FavoriteAdapter(this,favouriteArrayList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(FavoriteActivity.this, 2);
        mFavoriteRl.setLayoutManager(gridLayoutManager);
        mFavoriteRl.setAdapter(favoriteAdapter);
//        BmobUser currentUser =  MyBmobUser.getCurrentUser(this);
        IUserInfo user = UserUtil.getInstance().getUser();
        if(user!=null){
            String account = user.getName();
            BmobQuery<MyBmobFavourite> categoryBmobQuery = new BmobQuery<>();
            categoryBmobQuery.addWhereEqualTo("name",account);

            categoryBmobQuery.findObjects(this,new FindListener<MyBmobFavourite>() {

                private List<Picture> lis;

                @Override
                public void onSuccess(List<MyBmobFavourite> list) {
                    if(favouriteArrayList!=null&&favouriteArrayList.size()>0){
                        favouriteArrayList.clear();
                    }
                    lis = new ArrayList<>();
                    for (MyBmobFavourite myBmobFavourite : list) {
                        Picture picture = new Picture();
                        picture.setDesc(myBmobFavourite.getDescribe());
                        picture.setHeight(myBmobFavourite.getHeight());
                        picture.setDownloadUrl(myBmobFavourite.getUrl());
                        picture.setFromUrl(myBmobFavourite.getThumbUrl());
                        picture.setThumbUrl(myBmobFavourite.getThumbUrl());
                        picture.setWidth(myBmobFavourite.getWidth());
                        lis.add(picture);
                    }
                    favouriteArrayList.addAll(lis);
                    favoriteAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(int i, String s) {

                }


            });
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ly_left:
                finish();
                break;
            case R.id.tv_title:
                break;
        }
    }
}
