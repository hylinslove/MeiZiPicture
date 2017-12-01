package com.chinastis.gankimage.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chinastis.gankimage.R;
import com.chinastis.gankimage.activity.ImageActivity;
import com.chinastis.gankimage.adapter.MyRecyclerAdapter;
import com.chinastis.gankimage.bean.ImageBean;
import com.chinastis.gankimage.listener.ImageClickListener;
import com.chinastis.gankimage.net.RetrofitClient;
import com.chinastis.gankimage.net.RetrofitService;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.chinastis.gankimage.R.id.recycler_main;

/**
 * Created by xianglong on 2017/11/30.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class ImageFragment extends Fragment implements ImageClickListener,RecyclerView.OnScrollChangeListener{

    private RecyclerView mRecyclerView;
    private Context mContext;
    private MyRecyclerAdapter adapter;
    private boolean isLoading;
    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image,null);

        mRecyclerView = (RecyclerView) view.findViewById(recycler_main);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,RecyclerView.VERTICAL));
        mRecyclerView.setOnScrollChangeListener(this);
        getData(1,false);
        return view;
    }

    private void getData(int page, final boolean isSelect) {
        isLoading = true;
        RetrofitClient.getRetrofit()
                .create(RetrofitService.class)
                .getImageData(20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ImageBean>() {
                    @Override
                    public void onCompleted() {
                        Snackbar.make(mRecyclerView,"加载完成",Snackbar.LENGTH_SHORT).show();
                        isLoading = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Snackbar.make(mRecyclerView,"网络连接失败",Snackbar.LENGTH_LONG).show();
                        isLoading = false;

                    }

                    @Override
                    public void onNext(ImageBean imageBean) {
                        List<ImageBean.ResultsBean> images = imageBean.getResults();

                        if(isSelect){
                            replaceData(images);
                        } else {
                            dataChanged(images);
                        }
                    }
                });
    }

    private void dataChanged(List<ImageBean.ResultsBean> list) {
        if(adapter == null){
            adapter = new MyRecyclerAdapter(mContext,list);
            adapter.setListener(this);
            mRecyclerView.setAdapter(adapter);
        } else {
            adapter.addData(list);
        }

    }

    private void replaceData(List<ImageBean.ResultsBean> list) {
        if(adapter == null){
            adapter = new MyRecyclerAdapter(mContext,list);
            adapter.setListener(this);
            mRecyclerView.setAdapter(adapter);
        } else {
            adapter.replace(list);
        }

    }

    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        return recyclerView != null && recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange();
    }


    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if(isSlideToBottom(mRecyclerView) && !isLoading){
            getData(0,false);
        }
    }

    @Override
    public void click(String url) {

        Intent intent = new Intent(mContext, ImageActivity.class);
        intent.putExtra("url",url);

        startActivity(intent);
    }
}
