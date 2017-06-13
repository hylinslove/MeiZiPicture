package com.chinastis.gankimage;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;


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

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity implements ImageClickListener,RecyclerView.OnScrollChangeListener {

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;

    private int page = 1;

    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(recycler_main);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,RecyclerView.VERTICAL));
        mRecyclerView.setOnScrollChangeListener(this);

        getData(1);
    }

    private void getData(int page) {
        isLoading = true;
        RetrofitClient.getRetrofit()
                .create(RetrofitService.class)
                .getImageData(20,page)
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
                        Snackbar.make(mRecyclerView,"网络连接失败",Snackbar.LENGTH_LONG).show();
                        isLoading = false;
                    }

                    @Override
                    public void onNext(ImageBean imageBean) {
                        List<ImageBean.ResultsBean> images = imageBean.getResults();
                        dataChanged(images);
                    }
                });
    }

    private void dataChanged(List<ImageBean.ResultsBean> list) {
        if(adapter == null){
            adapter = new MyRecyclerAdapter(this,list);
            adapter.setListener(this);
            mRecyclerView.setAdapter(adapter);
        } else {
            adapter.addData(list);
        }

    }



    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        return recyclerView != null && recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange();
    }


    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if(isSlideToBottom(mRecyclerView) && !isLoading){
            getData(++page);
        }
    }

    @Override
    public void click(String url) {

        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra("url",url);

        startActivity(intent);
    }
}
