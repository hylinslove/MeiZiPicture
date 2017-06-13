package com.chinastis.gankimage.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chinastis.gankimage.R;
import com.chinastis.gankimage.bean.ImageBean;
import com.chinastis.gankimage.listener.ImageClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<ImageBean.ResultsBean> images;
    private LayoutInflater inflater;

    private ImageClickListener listener;

    public void setListener(ImageClickListener listener) {
        this.listener = listener;
    }

    public MyRecyclerAdapter(Context context, List<ImageBean.ResultsBean> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.image_item,parent,false);

        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Picasso
                .with(context)
                .load(images.get(position).getUrl())
                .resize(400,500)
                .centerCrop()
                .into(holder.imageItem);

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void addData(List<ImageBean.ResultsBean> list) {
        this.images.addAll(list);
        this.notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageItem = (ImageView) itemView.findViewById(R.id.image_item);
            imageItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.click(images.get(getLayoutPosition()).getUrl());
                }
            });

        }
    }
}
