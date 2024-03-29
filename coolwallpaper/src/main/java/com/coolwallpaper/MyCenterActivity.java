package com.coolwallpaper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coolwallpaper.bean.IUserInfo;
import com.coolwallpaper.bean.IUserOperator;
import com.coolwallpaper.bmob.BmobUtil;
import com.coolwallpaper.bmob.MyBmobLogin;
import com.coolwallpaper.bmob.MyBmobUser;
import com.coolwallpaper.utils.TimeUtil;
import com.coolwallpaper.utils.ToastUtil;
import com.coolwallpaper.utils.UmengUtil;
import com.coolwallpaper.utils.UserUtil;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 个人中心
 * Created by fuchao on 2016/4/29.
 */
public class MyCenterActivity extends BaseActivity {

    private LoginPopupWindow popupWindow;
    private MyBmobUser user;
    private IUserInfo mUser;

    @Bind(R.id.iv_face)
    ImageView ivFace;

    @Bind(R.id.tv_name)
    TextView tvName;

    @Bind(R.id.tv_tip)
    TextView tvTip;

    /**
     * 启动方法
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MyCenterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_center);

        this.init();
    }

    public void init() {
        mUser = UserUtil.getInstance().getUser();
        //判断用户是否登录
        if(mUser != null ){
            //如果用户登录过了，则显示用户信息
            Glide.with(this).load(mUser.getImg()).into(ivFace);
            tvName.setText(mUser.getName());

        }
    }

    //刷新
    public void refresh(IUserInfo userInfo) {
        //null judge
        if (userInfo == null) {
            return;
        }
        //show name
        tvName.setText(userInfo.getName());
        //show img
        Glide.with(this).load(userInfo.getImg()).into(ivFace);
    }

    @OnClick({R.id.ly_left_arrow, R.id.iv_face, R.id.ly_my_wallpaper, R.id.ly_my_favour, R.id.ly_my_download, R.id.ly_my_upload})
    public void onClick(View v) {
        //标题栏上返回键
        if(v.getId() == R.id.ly_left_arrow){
            finish();
            return;
        }
        //其余按钮要检查是否登录
        if(mUser == null){
            //没有登录则要调到登录界面
            showLoginWindow();
            return;
        }
        //登录后则处理正常的逻辑
        switch (v.getId()) {
            //我的头像
            case R.id.iv_face:
                showLoginWindow();
                break;
            //我的壁纸
            case R.id.ly_my_wallpaper:
                break;
            //我的收藏
            case R.id.ly_my_favour:
                break;
            //我的下载
            case R.id.ly_my_download:
                break;
            //我的上传
            case R.id.ly_my_upload:
                break;
        }
    }

    //弹出登录窗口
    private void showLoginWindow() {
        if (popupWindow == null) {
            popupWindow = new LoginPopupWindow(this);
        }
        popupWindow.setOnLoginListener(new LoginPopupWindow.OnLoginListener() {
            @Override
            public void onLoginClick(int loginType) {
                switch (loginType) {
                    //qq login
                    case LoginPopupWindow.LOGIN_TYPE_QQ:
                        qqLogin();
                        break;
                    //sina login
                    case LoginPopupWindow.LOGIN_TYPE_SINA:
                        break;
                }
            }
        });
        popupWindow.showBottomDilog(getWindow().getDecorView());
    }

    //QQ登录
    public void qqLogin() {
        UmengUtil.getInstence().qqLogin(this, new UmengUtil.Callback() {

            @Override
            public void onSuccess() {
                //get user info after login success
                getQQUserInfo();
            }

            @Override
            public void onFailure(String reason) {
                Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Get qq user info
    public void getQQUserInfo() {
        UmengUtil.getInstence().getQQUserInfo(this, new UmengUtil.InfoCallBack() {
            @Override
            public void getUserInfo(IUserInfo userInfo) {
                // null judge
                if (userInfo == null) {
                    //get user info failure
                    Toast.makeText(getApplicationContext(), "获取用户信息失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                // get user info success,refresh view
                refresh(userInfo);
                //登录成功后要做一些业务处理
                dealLoginSuccess(userInfo);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //must rewrite this method and add next code for the third login
        UmengUtil.getInstence().setActivityResult(requestCode, resultCode, data);
    }
    private void login(IUserInfo userInfo){
        user = new MyBmobUser();
        user.setUsername(userInfo.getName());
        user.setPassword(userInfo.getName());
        user.login(this, new SaveListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }
    //登录成功后处理一下后续逻辑
    private void dealLoginSuccess(IUserInfo userInfo) {
        //查询是否存在这个用户
        BmobQuery userQuery = BmobUtil.getMyUserQuery();
        userQuery.addWhereEqualTo("account", userInfo.getAccount());
        userQuery.findObjects(this, new FindListener<IUserInfo>() {
            @Override
            public void onSuccess(List list) {
                //不存在这个用户
                if (list == null || list.size() == 0) {
                    //添加这个用户
                    user = new MyBmobUser();
                    user.setImgUrl(userInfo.getImg());
                    user.setSex(userInfo.getSex());
                    user.setUsername(userInfo.getName());
                    user.setAccount(userInfo.getAccount());
                    user.setPassword(userInfo.getName());
                    user.signUp(MyApplication.getInstance(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            login(userInfo);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            if(i==202){
                                login(userInfo);
                            }
                        }
                    });
                }
                //存在这个用户
                else {
                    user = (MyBmobUser) list.get(0);
                }
                //写入登录记录
                MyBmobLogin myBmobLogin = new MyBmobLogin();
                myBmobLogin.setTime(TimeUtil.toString(new Date()));
                myBmobLogin.setType(userInfo.getUserType());
                myBmobLogin.setUser(user);
                myBmobLogin.save(MyApplication.getInstance());
            }

            @Override
            public void onError(int i, String s) {

            }
        });
        //写入本地文件
        IUserOperator operator = UserUtil.getInstance();
        boolean result = operator.addUser(userInfo);
        if (result) {
            ToastUtil.showDebug("保存用户到本地成功,name=" + userInfo.getName());
        } else {
            ToastUtil.showDebug("保存用户到本地失败,name=" + userInfo.getName());
        }
    }

}
