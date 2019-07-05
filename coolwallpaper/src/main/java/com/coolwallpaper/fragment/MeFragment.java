package com.coolwallpaper.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coolwallpaper.FavoriteActivity;
import com.coolwallpaper.LoginActivity;
import com.coolwallpaper.R;
import com.coolwallpaper.SettingActivity;
import com.coolwallpaper.base.BaseFragment;
import com.coolwallpaper.bean.IUserInfo;
import com.coolwallpaper.event.MessageEvent;
import com.coolwallpaper.utils.UserUtil;
import com.joooonho.SelectableRoundedImageView;
import com.library.common.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by SDC on 2019/6/12.
 */

public class MeFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private SelectableRoundedImageView mIvFace;
    /**
     * 立即登录
     */
    private TextView mTvName;
    /**
     * 登录可关注跟多潮流热点
     */
    private TextView mTvTip;
    private LinearLayout mLyMyWallpaper;
    private LinearLayout mLyMyFavour;
    //    private BmobUser currentUser;
    private IUserInfo mUser;
    private LinearLayout mLyMyDownload;
    private LinearLayout mLyMyUpload;

    @Override
    protected void initData() {
//        currentUser = MyBmobUser.getCurrentUser(getContext());
        info();
    }

    private void info() {
        mUser = UserUtil.getInstance().getUser();
        if (mUser != null) {
            String img = mUser.getImg();
            String name = mUser.getName();
            Glide.with(getContext()).load(img).into(mIvFace);
            mTvName.setText(name);
            if (mUser.getSex() == 0) {
                mTvTip.setText("男");
            } else {
                mTvTip.setText("女");
            }
        }else {
            Glide.with(getContext()).load(R.drawable.example).into(mIvFace);
            mTvName.setText("立即登录");
//            if (mUser.getSex() == 0) {
                mTvTip.setText("登录可关注更多潮流热点");
//            } else {
//                mTvTip.setText("女");
//            }
        }
    }

    @Override
    protected void initView(View view) {

        mIvFace = (SelectableRoundedImageView) view.findViewById(R.id.iv_face);
        mIvFace.setOnClickListener(this);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mTvTip = (TextView) view.findViewById(R.id.tv_tip);
        mLyMyWallpaper = (LinearLayout) view.findViewById(R.id.ly_my_wallpaper);
        mLyMyWallpaper.setOnClickListener(this);
        mLyMyFavour = (LinearLayout) view.findViewById(R.id.ly_my_favour);
        mLyMyFavour.setOnClickListener(this);
        mLyMyDownload = (LinearLayout) view.findViewById(R.id.ly_my_download);
        mLyMyDownload.setOnClickListener(this);
        mLyMyUpload = (LinearLayout) view.findViewById(R.id.ly_my_upload);
        mLyMyUpload.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int setContentView() {
        return R.layout.activity_my_center;
    }

    @Override
    protected void onClickListener(View v) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onMessageEvent(MessageEvent event) {
        /* Do something */
        Log.e("zxz", "我登录了" + event.isLogin());
        if (event.isLogin()) {
            info();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void clearUser(boolean event) {
        /* Do something */
        Log.e("zxz", "我登录了" + event);
        if (event) {
//            info();

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_face:
                if (mUser == null) {
                    LoginActivity.launch(getContext());
                }
                break;
            case R.id.ly_my_wallpaper:

                if(mUser!=null){
                    Intent intent1 = new Intent(getContext(), FavoriteActivity.class);
                    startActivity(intent1);
                }else {
                    ToastUtils.show(getContext(),"请先登录");
                }
                break;
            case R.id.ly_my_favour:
                //跳转到本机相册
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, 0);
                break;
            case R.id.ly_my_download:
                ToastUtils.show(getContext(),"当前已是最新版本!");
                break;
            case R.id.ly_my_upload:
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


}
