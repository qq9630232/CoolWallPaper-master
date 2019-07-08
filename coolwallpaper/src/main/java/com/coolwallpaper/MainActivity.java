package com.coolwallpaper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coolwallpaper.fragment.FindFragment;
import com.coolwallpaper.fragment.HomeFragment;
import com.coolwallpaper.fragment.MeFragment;
import com.coolwallpaper.fragment.SearchFragment;
import com.coolwallpaper.fragment.SortFragment;
import com.coolwallpaper.utils.MainModel;
import com.lidroid.xutils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 启动页面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    TextView mTvContentHead;
    ConstraintLayout mTitleBar;
    FrameLayout mHomeContainer;



    private FragmentManager manager;

    private MainModel model = new MainModel();

    private List<Fragment> fragmentList = new ArrayList<>();

    private List<TextView> tvList = new ArrayList<>();

    private List<ImageView> imgList = new ArrayList<>();

    public static final int home_Home_Tag = 0;
    public static final int home_Sort_tag = 1;

    public static final int home_Search_tag = 2;

    //    public static final int home_head_news_tag = 2;
    public static final int home_Find_Tag = 3;
    public static final int home_me_tag = 4;

    private ImageView mHomeHeadDreamImg;
    /**
     * 首页
     */
    ImageView mHomeImg;
    TextView mHomeTv;
    LinearLayout mHomeLl;
    /**
     * 搜索
     */
    ImageView mSearchImg;
    TextView mSearchTv;
    LinearLayout mSearchLl;
    /**
     * 我的
     */
    ImageView mMeImg;
    TextView mMeTv;
    LinearLayout mMeLl;
    private ImageView mHomeSortIcon;
    /**
     * 分类
     */
    private TextView mHomeSortTv;
    private LinearLayout mHomeSortLl;
    private ImageView mHomeTuijianImg;
    /**
     * 推荐
     */
    private TextView mHomeTuijianTv;
    private LinearLayout mHomeTuijianLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        ViewUtils.inject(this);
        perrmissions();
        manager = getSupportFragmentManager();

        fragmentList.add(new HomeFragment());
        fragmentList.add(new SortFragment());
        fragmentList.add(new SearchFragment());
        fragmentList.add(new FindFragment());
        fragmentList.add(new MeFragment());

        tvList.add(mHomeTv);
        tvList.add(mHomeSortTv);
        tvList.add(mSearchTv);
        tvList.add(mHomeTuijianTv);
        tvList.add(mMeTv);

        imgList.add(mHomeImg);
        imgList.add(mHomeSortIcon);
        imgList.add(mSearchImg);
        imgList.add(mHomeTuijianImg);
        imgList.add(mMeImg);
        //设置页面布局显示
        model.setShowFragment(manager, fragmentList, home_Home_Tag, true);
        //设置显示的页面的button样式
        model.setShowFragmentTextStyle(this, tvList, imgList, home_Home_Tag);
        mTvContentHead.setText("首页");
    }

    private void perrmissions() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager
                .PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager
                .PERMISSION_GRANTED) {
//            HomePageActivity.startActivity(this);
//            finish();
        } else {
            //不具有获取权限，需要进行权限申请
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults != null) {
                if (grantResults.length > 0) {
//                    HomePageActivity.startActivity(this);
//                    finish();
//                    startCameraAct(EncyclopediaActivity.this, CAMERA_INTENT_CODE);
                }
            }
        }
    }

    private void initView() {
//        mIbLeftHead = (ImageButton) findViewById(R.id.ib_left_head);
        mTvContentHead = (TextView) findViewById(R.id.tv_content_head);
        mTitleBar = (ConstraintLayout) findViewById(R.id.title_bar);
        mHomeContainer = (FrameLayout) findViewById(R.id.home_container);
        mHomeHeadDreamImg = (ImageView) findViewById(R.id.home_head_dream_img);

        mHomeImg = (ImageView) findViewById(R.id.home_head_dream_img);
        mHomeTv = (TextView) findViewById(R.id.home_head_dream_tv);
        mHomeLl = (LinearLayout) findViewById(R.id.home_head_dream);
        mHomeLl.setOnClickListener(this);
        mSearchImg = (ImageView) findViewById(R.id.home_constellation_img);
        mSearchTv = (TextView) findViewById(R.id.home_constellation_tv);
        mSearchLl = (LinearLayout) findViewById(R.id.home_constellation);
        mSearchLl.setOnClickListener(this);
        mMeImg = (ImageView) findViewById(R.id.home_head_news_img);
        mMeTv = (TextView) findViewById(R.id.home_head_news_tv);
        mMeLl = (LinearLayout) findViewById(R.id.home_head_news);
        mMeLl.setOnClickListener(this);
        mHomeSortIcon = (ImageView) findViewById(R.id.home_sort_icon);
        mHomeSortTv = (TextView) findViewById(R.id.home_sort_tv);
        mHomeSortLl = (LinearLayout) findViewById(R.id.home_sort_ll);
        mHomeSortLl.setOnClickListener(this);
        mHomeTuijianImg = (ImageView) findViewById(R.id.home_tuijian_img);
        mHomeTuijianTv = (TextView) findViewById(R.id.home_tuijian_tv);
        mHomeTuijianLl = (LinearLayout) findViewById(R.id.home_tuijian_ll);
        mHomeTuijianLl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.home_head_news:
                //设置页面布局显示
                model.setShowFragment(manager, fragmentList, home_me_tag, false);
                //设置显示的页面的button样式
                model.setShowFragmentTextStyle(this, tvList, imgList, home_me_tag);
                mTvContentHead.setText("我的");

                break;
            case R.id.home_head_dream:

                //设置页面布局显示
                model.setShowFragment(manager, fragmentList, home_Home_Tag, false);
                //设置显示的页面的button样式
                model.setShowFragmentTextStyle(this, tvList, imgList, home_Home_Tag);
                mTvContentHead.setText("首页");

                break;
            case R.id.home_constellation:

                //设置页面布局显示
                model.setShowFragment(manager, fragmentList, home_Search_tag, false);
                //设置显示的页面的button样式
                model.setShowFragmentTextStyle(this, tvList, imgList, home_Search_tag);
                mTvContentHead.setText("搜索");
                break;
            case R.id.home_sort_ll:
                //设置页面布局显示
                model.setShowFragment(manager, fragmentList, home_Sort_tag, false);
                //设置显示的页面的button样式
                model.setShowFragmentTextStyle(this, tvList, imgList, home_Sort_tag);
                mTvContentHead.setText("分类");
                break;
            case R.id.home_tuijian_ll:
                //设置页面布局显示
                model.setShowFragment(manager, fragmentList, home_Find_Tag, false);
                //设置显示的页面的button样式
                model.setShowFragmentTextStyle(this, tvList, imgList, home_Find_Tag);
                mTvContentHead.setText("推荐");
                break;
        }
    }
}
