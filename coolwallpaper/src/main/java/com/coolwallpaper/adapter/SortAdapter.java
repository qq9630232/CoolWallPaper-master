package com.coolwallpaper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coolwallpaper.R;
import com.coolwallpaper.bean.SortBean;
import com.coolwallpaper.utils.SortListActivity;

import java.util.List;

/**
 * Created by SDC on 2019/6/13.
 */

public class SortAdapter extends RecyclerView.Adapter <SortAdapter.Vh>{
    private Context context;
    private List<SortBean.ResBean.CategoryBean> categoryList;
    public SortAdapter(Context context, List<SortBean.ResBean.CategoryBean> categoryList) {
        this.context=context;
        this.categoryList=categoryList;
    }

    @Override
    public Vh onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.sort_item, parent, false);
        Vh vh = new Vh(inflate);
        return vh;
    }

    @Override
    public void onBindViewHolder(Vh holder, int position) {
        Glide.with(context).load(categoryList.get(position).getCover()).into(holder.mSortImg);
        holder.mSortTv.setText(categoryList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SortListActivity.class);
                intent.putExtra("id",categoryList.get(position).getId());
                intent.putExtra("title",categoryList.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class Vh extends RecyclerView.ViewHolder{

        private ImageView mSortImg;
        private TextView mSortTv;

        public Vh(View itemView) {
            super(itemView);
            mSortImg = itemView.findViewById(R.id.sort_img);
            mSortTv = itemView.findViewById(R.id.sort_tv);
        }
    }
}
