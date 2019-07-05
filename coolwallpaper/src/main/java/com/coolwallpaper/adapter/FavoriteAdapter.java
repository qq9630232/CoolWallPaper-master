package com.coolwallpaper.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.coolwallpaper.R;
import com.coolwallpaper.ShowPictureDetailActivity;
import com.coolwallpaper.model.Picture;

import java.util.List;

/**
 * Created by SDC on 2019/6/11.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Context context;
    private List<Picture> favouriteArrayList;
    public FavoriteAdapter(Context context, List<Picture> favouriteArrayList) {
        this.context=context;
        this.favouriteArrayList=favouriteArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(favouriteArrayList.get(position).getThumbUrl()).into(holder.mIvItem);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPictureDetailActivity.startActivity(context,position,favouriteArrayList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favouriteArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mIvItem;

        @SuppressLint("WrongViewCast")
        public ViewHolder(View itemView) {
            super(itemView);
            mIvItem = itemView.findViewById(R.id.iv_item);
        }
    }
}
