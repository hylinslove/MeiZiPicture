package com.chinastis.gankimage.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinastis.gankimage.R;
import com.chinastis.gankimage.bean.ImageBean;
import com.chinastis.gankimage.listener.ImageClickListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;


public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ImageBean.ResultsBean> images;
    private LayoutInflater inflater;

    private ImageClickListener listener;

    private Transformation transformation;

    public void setListener(ImageClickListener listener) {
        this.listener = listener;
    }

    public MyRecyclerAdapter(Context context, List<ImageBean.ResultsBean> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);

        transformation = getTrans();

    }

    @Override
    public int getItemViewType(int position) {
        return images.get(position).getType().equals("福利")?0:1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == 0) {
            View view = inflater.inflate(R.layout.image_item,parent,false);

            MyViewHolder viewHolder = new MyViewHolder(view);

            return viewHolder;
        } else {
            View view = inflater.inflate(R.layout.video_item,parent,false);

            VideoViewHolder viewHolder = new VideoViewHolder(view);

            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == 0) {
            Picasso.with(context)
                    .load(images.get(position).getUrl())
                    .transform(transformation)
                    .into(((MyViewHolder)holder).imageItem);
            ((MyViewHolder)holder).describe.setText(images.get(position).getDesc());
        } else {
            ((VideoViewHolder)holder).title.setText(images.get(position).getDesc());
            ((VideoViewHolder)holder).date.setText(images.get(position).getPublishedAt());
        }

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void addData(List<ImageBean.ResultsBean> list) {
        this.images.addAll(list);
        this.notifyDataSetChanged();
    }

    public void replace(List<ImageBean.ResultsBean> list) {
        this.images = list;
        this.notifyDataSetChanged();
    }


    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageItem;
        TextView describe;

        private MyViewHolder(View itemView) {
            super(itemView);
            imageItem = (ImageView) itemView.findViewById(R.id.image_item);
            describe = (TextView) itemView.findViewById(R.id.describe_item);
            imageItem.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.click(images.get(getLayoutPosition()).getUrl());
        }
    }

    private class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView date;

        private VideoViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_video_item);
            date = (TextView) itemView.findViewById(R.id.date_video_item);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.click(images.get(getLayoutPosition()).getUrl());
        }
    }


    private Transformation getTrans() {

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        final int width = wm.getDefaultDisplay().getWidth()/2;


        return new Transformation() {

            @Override
            public Bitmap transform(Bitmap source) {

                if (source.getWidth() == 0) {
                    return source;
                }

                //如果图片小于设置的宽度，则返回原图
                if (source.getWidth() < width) {
                    return source;
                } else {
                    //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                    double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                    int targetHeight = (int) (width * aspectRatio);
                    if (targetHeight != 0 && width != 0) {
                        Bitmap result = Bitmap.createScaledBitmap(source, width, targetHeight, false);
                        if (result != source) {
                            // Same bitmap is returned if sizes are the same
                            source.recycle();
                        }
                        return result;
                    } else {
                        return source;
                    }
                }

            }

            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }
        };
    }
}
