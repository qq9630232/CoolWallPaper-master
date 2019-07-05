package com.coolwallpaper.fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.coolwallpaper.R;
import com.coolwallpaper.ShowSearchListActivity;
import com.coolwallpaper.utils.HotTopicUtil;

import java.util.List;

import me.gujun.android.taggroup.TagGroup;
import okhttp3.OkHttpClient;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by SDC on 2019/6/12.
 */

public class SearchFragment extends com.coolwallpaper.base.BaseFragment implements View.OnClickListener {
    private View view;
    /**
     * 搜索
     */
    private Button mSearchBtn;
    /**
     * 关键词
     */
    private EditText mSearchEdt;
    private RelativeLayout mSearchRl;
    /**
     * 热门搜索
     */
//    private TextView mHotTv;
    private TagGroup mTagGroup1;
    private OkHttpClient httpClient;
    private Handler handler;
    private List<String> hotTopicMan;
    private List<String> hotTopicGirl;
    private List<String> hotTopicMove;
    private LinearLayout mLyTag1;
    private TagGroup mTagGroup2;
    private LinearLayout mLyTag2;
    private TagGroup mTagGroup3;
    private LinearLayout mLyTag3;

    @Override
    protected void initData() {
        init();
    }

    @Override
    protected void initView(View view) {

        mSearchBtn = (Button) view.findViewById(R.id.search_btn);
        mSearchBtn.setOnClickListener(this);
        mSearchEdt = (EditText) view.findViewById(R.id.search_edt);
        mSearchRl = (RelativeLayout) view.findViewById(R.id.search_rl);
//        mHotTv = (TextView) view.findViewById(R.id.hot_tv);
        mTagGroup1 = (TagGroup) view.findViewById(R.id.tag_group_1);
        mLyTag1 = (LinearLayout) view.findViewById(R.id.ly_tag_1);
        mTagGroup2 = (TagGroup) view.findViewById(R.id.tag_group_2);
        mLyTag2 = (LinearLayout) view.findViewById(R.id.ly_tag_2);
        mTagGroup3 = (TagGroup) view.findViewById(R.id.tag_group_3);
        mLyTag3 = (LinearLayout) view.findViewById(R.id.ly_tag_3);
        addListener();
    }

    @Override
    protected void initListener() {

    }

    private void init() {
        this.httpClient = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());
        //获取热词
        HotTopicUtil util = new HotTopicUtil();
        //用RxAndroid来切换线程
        //Observable.just("hello").subscribeOn(Schedulers.io()).flatMap(o -> Observable.just(util.getTopic())).observeOn(AndroidSchedulers.mainThread()).subscribe(topics -> tagGroup.setTags(topics));
//        Observable.just("hello").subscribeOn(Schedulers.io()).map(
//                o -> hotTopicMan = util.getTopicMan()).observeOn(AndroidSchedulers.mainThread()).subscribe(o -> showTags());
        Observable.just("hello").subscribeOn(Schedulers.io()).map(o -> hotTopicMan = util.getTopicMan()).map(o -> hotTopicGirl = util.getTopicGirl()).map(o -> hotTopicMove = util.getTopicMove()).observeOn(AndroidSchedulers.mainThread()).subscribe(o -> showTags());

    }

    @Override
    protected int setContentView() {
        return R.layout.search_fragment_layout;
    }

    //添加监听器
    private void addListener() {
        //添加标签点击监听
        this.mTagGroup1.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                //跳转到搜索结果页面
                showSearchResult(tag + "壁纸");
            }
        });
        this.mTagGroup2.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                //跳转到搜索结果页面
                showSearchResult(tag + "壁纸");
            }
        });
        this.mTagGroup3.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                //跳转到搜索结果页面
                showSearchResult(tag + "电影");
            }
        });
    }

    //显示三个热词标签
    private void showTags() {
        mTagGroup1.setTags(hotTopicMan);
        mTagGroup2.setTags(hotTopicGirl);
        mTagGroup3.setTags(hotTopicMove);
        mLyTag1.setVisibility(View.VISIBLE);
        mLyTag2.setVisibility(View.VISIBLE);
        mLyTag3.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onClickListener(View v) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.search_btn:
                showSearchResult(mSearchEdt.getText().toString() + "壁纸");

                break;
        }
    }
    //显示搜索结果
    private void showSearchResult(String queryStr) {
        if (queryStr == null || "".equals(queryStr)) {
            Toast.makeText(getContext(), "关键字不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //跳转到搜索结果页面
        ShowSearchListActivity.startActivity(getContext(), queryStr);
    }
}
