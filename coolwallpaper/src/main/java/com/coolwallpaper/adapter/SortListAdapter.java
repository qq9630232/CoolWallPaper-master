package com.coolwallpaper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.coolwallpaper.DetatilsActivity;
import com.coolwallpaper.R;
import com.coolwallpaper.bean.SortListBean;

import java.util.List;

/**
 * Created by SDC on 2019/6/14.
 */

public class SortListAdapter extends RecyclerView.Adapter<SortListAdapter.ViewHolder> {
    private Context context;
    List<SortListBean.ResBean.VerticalBean> list ;
    public SortListAdapter(Context context, List<SortListBean.ResBean.VerticalBean> list) {
        this.context=context;
        this.list=list;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.sort_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg()).placeholder(R.drawable.coolwallpaper_empty).into(holder.mImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetatilsActivity.class);
                intent.putExtra("img",list.get(position).getImg());
                intent.putExtra("url",list.get(position).getWp());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImg;

        public ViewHolder(View view) {
            super(view);
            mImg = view.findViewById(R.id.sort_list_img);

        }
    }

}
