package com.coolwallpaper;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coolwallpaper.bean.IUserInfo;
import com.coolwallpaper.bmob.MyBmobUser;
import com.coolwallpaper.event.LoadingFinishEvent;
import com.coolwallpaper.fragment.PaperListFragment;
import com.coolwallpaper.utils.ToastUtil;
import com.coolwallpaper.utils.UserUtil;
import com.library.common.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.special.ResideMenu.ResideMenu;
import com.squareup.otto.Subscribe;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;

/**
 * 首页
 * Created by fuchao on 2016/3/29.
 */
public class HomePageActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_UPLOAD = 0x1002;
    private PaperViewPagerAdapter adapter;
    private String title1;//一级标题
    private String[] subTitles;//二级标题
    private ResideMenu resideMenu;//侧滑菜单
    private View leftMenuView;//左边菜单
    private View rightMenuView;//右边菜单
    private RightMenuListener rightMenuListener;//右边菜单按钮监听
    private LeftMenuListener leftMenuListener;//左边菜单按钮监听
    private float startY;//按下的时候y坐标
    private int DISTANCE = 100;//上滑的距离
    private IUserInfo mUser;//本地保存的用户
    //private DataSouece dataSouece = new SogouDataSource();//数据来源,默认来自搜狗

    //标题栏
    @Bind(R.id.ly_title)
    View lyTitle;

    //标题
    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Bind(R.id.vp_paper)
    ViewPager viewPager;

    //指示器
    @Bind(R.id.tb_indicator)
    TitlePageIndicator indicator;

    //进度页面
    @Bind(R.id.rl_loading)
    View rlLoading;

    //标题栏+指示器
    @Bind(R.id.ly_action_bar)
    View lyActionBar;

    /**
     * 启动方法
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HomePageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_2);
        this.init();
        this.addListener();
        //检测升级
        //MyApplication.getInstance().checkUpdate(this);
    }

    //初始化
    private void init() {
        this.mUser = UserUtil.getInstance().getUser();
        //创建ResidMenu
        this.resideMenu = new ResideMenu(this, R.layout.menu_main_left, R.layout.menu_main_right);
        this.resideMenu.setBackground(R.drawable.coolwallpaper_main_bg);
        this.resideMenu.attachToActivity(this);
        this.resideMenu.setScaleValue(0.5f);
        this.leftMenuView = resideMenu.getLeftMenuView();
        this.rightMenuView = resideMenu.getRightMenuView();
        //关闭左滑右滑开关
//        this.resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);

        this.resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        //初始化的数据
        title1 = "壁纸";
        subTitles = new String[]{"世界风光", "动物", "明星", "影视", "日韩明星", "摄影", "游戏"};
        //设置适配器
        adapter = new PaperViewPagerAdapter(this, title1, subTitles);
        viewPager.setAdapter(adapter);
        //设置指示器
        indicator.setViewPager(viewPager);
        //创建监听器
        this.rightMenuListener = new RightMenuListener();
        this.leftMenuListener = new LeftMenuListener();
        //初始化左边菜单
        this.initLeftMenu();
    }

    //初始化左边菜单
    private void initLeftMenu() {
        //用户登录过了
        if (mUser != null && mUser.getImg() != null) {
            ImageView ivFace = (ImageView) leftMenuView.findViewById(R.id.iv_face);
            TextView tvName = (TextView) leftMenuView.findViewById(R.id.tv_name);
            //显示头像
            Glide.with(this).load(mUser.getImg()).into(ivFace);
            tvName.setText(mUser.getName());
        }
    }

    //添加监听器
    private void addListener() {
        //左边的menu
        leftMenuView.findViewById(R.id.ly_my_wallpaper).setOnClickListener(leftMenuListener);
        leftMenuView.findViewById(R.id.ly_local_paper).setOnClickListener(leftMenuListener);
        leftMenuView.findViewById(R.id.ly_provide_paper).setOnClickListener(leftMenuListener);
        leftMenuView.findViewById(R.id.ly_user_upload).setOnClickListener(leftMenuListener);
        leftMenuView.findViewById(R.id.ly_check_update).setOnClickListener(leftMenuListener);
        leftMenuView.findViewById(R.id.ly_more_set).setOnClickListener(leftMenuListener);
        leftMenuView.findViewById(R.id.ly_my_center).setOnClickListener(leftMenuListener);
        //右边的menu
        rightMenuView.findViewById(R.id.ly_home).setOnClickListener(rightMenuListener);
        rightMenuView.findViewById(R.id.ly_hot).setOnClickListener(rightMenuListener);
        rightMenuView.findViewById(R.id.ly_scenery).setOnClickListener(rightMenuListener);
        rightMenuView.findViewById(R.id.ly_girl).setOnClickListener(rightMenuListener);
        rightMenuView.findViewById(R.id.ly_star).setOnClickListener(rightMenuListener);
        rightMenuView.findViewById(R.id.ly_select).setOnClickListener(rightMenuListener);
        rightMenuView.findViewById(R.id.ly_car).setOnClickListener(rightMenuListener);
        rightMenuView.findViewById(R.id.ly_film).setOnClickListener(rightMenuListener);
        rightMenuView.findViewById(R.id.ly_furniture).setOnClickListener(rightMenuListener);
        rightMenuView.findViewById(R.id.ly_beautiful).setOnClickListener(rightMenuListener);
        //顶部菜单
        findViewById(R.id.ly_set).setOnClickListener(this);
        findViewById(R.id.iv_search).setOnClickListener(this);
        findViewById(R.id.iv_menu).setOnClickListener(this);
        //给viewpager添加滑动监听器,以免resideMenu和ViewPager滑动冲突
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //滑动到最左边
                if (position == 0) {
                    resideMenu.clearIgnoredViewList();
                    resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
                    resideMenu.setSwipeDirectionEnable(ResideMenu.DIRECTION_LEFT);
                }
                //滑动到最右边
                else if (position == adapter.getCount() - 1) {
                    resideMenu.clearIgnoredViewList();
                    resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
                    resideMenu.setSwipeDirectionEnable(ResideMenu.DIRECTION_RIGHT);
                }
                //其他位置
                else {
                    resideMenu.addIgnoredView(viewPager);
                }
                //显示正在加载
                //rlLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //顶部左边的设置按钮
            case R.id.ly_set:
                //展开左边的菜单
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                break;
            //顶部右边的搜索按钮
            case R.id.iv_search:
                //跳转到搜索界面
                SearchActivity.startActivity(this);
                break;
            //顶部右边的菜单按钮
            case R.id.iv_menu:
                //展开右边的菜单
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
                //左边和右边有点不一样，右边的菜单展开后要让viewpager可以滑动
                resideMenu.clearIgnoredViewList();
                resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
                resideMenu.setSwipeDirectionEnable(ResideMenu.DIRECTION_RIGHT);
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Logger.d("startY = " + startY + " event.getY() = " + event.getY() + " DISTANCE = " + DISTANCE + " startY-event.getY()=" + (startY - event.getY()));
                //上滑超过一定距离
                if (startY - event.getY() > DISTANCE && lyTitle.getTop() >= 0) {
                    //标题栏上滑的动画
                    hideTitle();
                }
                //下滑超过一定距离
                else if (event.getY() - startY > DISTANCE && lyTitle.getBottom() <= 0) {
                    //标题栏下滑
                    showTitle();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        //使用滑动开启/关闭菜单
        return resideMenu.dispatchTouchEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUser = UserUtil.getInstance().getUser();
        //初始化左边菜单
        initLeftMenu();
    }

    //viewpager适配器
    class PaperViewPagerAdapter extends FragmentStatePagerAdapter {

        private String title;
        private String[] subTitles;
        private List<Fragment> fragmentList = new ArrayList<>();

        public PaperViewPagerAdapter(Context context, String title, String[] subTitles) {
            super(getFragmentManager());
            this.createFragmentList(title, subTitles);
        }

        //创建Fragment列表
        private void createFragmentList(String title, String[] subTitles) {
            this.title = title;
            this.subTitles = subTitles;
            //清空列表
            fragmentList.clear();
            //创建新的列表
            for (int i = 0; i < subTitles.length; i++) {
                Fragment fg = PaperListFragment.newInstance(title, subTitles[i]);
                fragmentList.add(fg);
            }
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return subTitles[position];
        }
    }

    //右边菜单的按钮监听器
    private class RightMenuListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //右边菜单点击
            switch (v.getId()) {
                //首页
                case R.id.ly_home:
                    title1 = "壁纸";
                    tvTitle.setText(title1);
                    subTitles = new String[]{"世界风光", "动物", "明星", "影视", "日韩明星", "摄影", "游戏"};
                    break;
                //热门
                case R.id.ly_hot:
                    title1 = "家居";
                    tvTitle.setText(title1);
                    subTitles = new String[]{"全部", "现代", "田园", "中式", "欧式"};
                    break;
                //风景
                case R.id.ly_scenery:
                    title1 = "壁纸";
                    tvTitle.setText(title1);
                    subTitles = new String[]{"全部", "军事", "微软", "萌宠", "静物", "卡通", "清新", "优质", "日历", "手绘"};
                    break;
                //美女
                case R.id.ly_girl:
                    title1 = "搞笑";
                    tvTitle.setText(title1);
                    subTitles = new String[]{"全部", "搞笑人物", "招牌标语", "萌宠", "屌丝", "错觉", "情侣", "创意"};
                    break;
                //明星
                case R.id.ly_star:
                    title1 = "明星";
                    tvTitle.setText(title1);
                    subTitles = new String[]{"全部", "宋仲基", "鹿晗", "霍建华", "林心如", "陈学冬", "杨幂", "angelababy", "赵丽颖", "刘诗诗", "刘涛", "唐嫣"};
                    break;
                //精选
                case R.id.ly_select:
                    title1 = "全景";
                    tvTitle.setText(title1);
                    subTitles = new String[]{"全部", "冰激凌", "客厅", "巧克力", "奔驰","澳门","阿尔卑斯山"};
                    break;
                //名车
                case R.id.ly_car:
                    title1 = "汽车";
                    subTitles = new String[]{"全部", "超跑", "官方车图", "经典名车", "豪车", "车模", "车展"};
                    break;
                //影视
                case R.id.ly_film:
                    title1 = "壁纸";
                    tvTitle.setText(title1);
                    subTitles = new String[]{"影视", "日韩明星", "欧美影视", "大陆明星"};
                    break;
                //家具
                case R.id.ly_furniture:
                    title1 = "家具";
                    tvTitle.setText(title1);
                    subTitles = new String[]{"全部", "新古典", "乡村田园", "现代", "混搭", "美式", "北欧", "复式", "别墅"};
                    break;
                //唯美
                case R.id.ly_beautiful:
                    title1 = "LOFTER";
                    tvTitle.setText(title1);
                    subTitles = new String[]{"全部", "最文艺", "拍私房", "LOFTER少女", "旅行微攻略", "遇见世界", "今天穿什么", "美妆潮流", "慢食堂", "元气早餐", "萌宠"};
                    break;
            }
            //设置加载中
            //rlLoading.setVisibility(View.VISIBLE);
            //创建新的adapter
            adapter = new PaperViewPagerAdapter(getActivity(), title1, subTitles);
            viewPager.setAdapter(adapter);
            //关闭菜单
            resideMenu.closeMenu();
            resideMenu.clearIgnoredViewList();
            resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
            resideMenu.setSwipeDirectionEnable(ResideMenu.DIRECTION_LEFT);
            //定位到第一页
            viewPager.setCurrentItem(0);
        }
    }

    //左边菜单按钮监听器
    private class LeftMenuListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //我的壁纸
                case R.id.ly_my_wallpaper:
//                    LocalPaperActivity.startActivity(getActivity());
                    BmobUser currentUser = MyBmobUser.getCurrentUser(HomePageActivity.this);
                    if(currentUser!=null){
                        Intent intent1 = new Intent(HomePageActivity.this, FavoriteActivity.class);
                        startActivity(intent1);
                    }else {
                        ToastUtils.show(HomePageActivity.this,"请先登录");
                    }

                    break;
                //本机相册
                case R.id.ly_local_paper:
                    //跳转到本机相册
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, 0);
                    break;
                //我要供图
                case R.id.ly_provide_paper:
                    onMyUploadClick();
                    break;
                //用户上传
                case R.id.ly_user_upload:
                    ShowUserUploadPictureList.startActivity(getActivity());
                    break;
                //检查升级
                case R.id.ly_check_update:
//                    MyApplication.getInstance().checkUpdate(getActivity());
                    ToastUtils.show(HomePageActivity.this,"当前已是最新版本");
                    break;
                //更多设置
                case R.id.ly_more_set:
                    break;
                //个人中心
                case R.id.ly_my_center:
                    MyCenterActivity.startActivity(getActivity());
                    break;
            }
        }
    }

    //接收加载完毕事件
    @Subscribe
    public void loadingFinish(LoadingFinishEvent event) {
        //隐藏加载界面
        rlLoading.setVisibility(View.GONE);
        Log.d(TAG, "收到加载完毕消息，隐藏加载界面");
    }

    //隐藏标题
    public void hideTitle() {
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -lyTitle.getHeight());
        animation.setDuration(500);
        animation.setFillAfter(false);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画完成后调整布局
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lyTitle.getLayoutParams();
                params.topMargin = -lyTitle.getHeight();
                lyTitle.setLayoutParams(params);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        lyActionBar.startAnimation(animation);
    }

    //显示标题
    public void showTitle() {
        lyActionBar.clearAnimation();
        TranslateAnimation animation = new TranslateAnimation(0, 0, -lyTitle.getHeight(), 0);
        animation.setDuration(500);
        animation.setFillAfter(false);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画完成后调整布局
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lyTitle.getLayoutParams();
                params.topMargin = 0;
                params.bottomMargin = 0;
                lyTitle.setLayoutParams(params);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        lyActionBar.startAnimation(animation);
    }

    //点击了我要供图
    private void onMyUploadClick() {
        //如果用户没有登录，则首先跳转到登录界面
        if (mUser == null) {
            LoginActivity.startActivityForResult(this, REQUEST_CODE_UPLOAD);
        }
        //用户登录了，则直接跳转到上传界面
        else {
            UploadActivity.startActivity(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //登录后返回
            if (requestCode == REQUEST_CODE_UPLOAD) {
                ToastUtil.show("登录成功");
                //获取当前的用户
                mUser = UserUtil.getInstance().getUser();
                //登录成功之后要刷新侧边栏
                initLeftMenu();
                //继续跳转到上传界面
                UploadActivity.startActivity(this);
            }
        }
        //登录失败
        if (resultCode == LoginActivity.RESULT_CODE_FAILURE) {
            ToastUtil.show("登录失败，请重试");
        }
    }
}
