package com.chinastis.gankimage;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;

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
    private Toolbar toolbar;

    private int page = 1;
    private int tempPage;

    private boolean isLoading;

    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        setMenuClick();

        initDialog();

        mRecyclerView = (RecyclerView) findViewById(recycler_main);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,RecyclerView.VERTICAL));
        mRecyclerView.setOnScrollChangeListener(this);

        getData(1,false);
    }


    private void getData(int page, final boolean isSelect) {
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
            adapter = new MyRecyclerAdapter(this,list);
            adapter.setListener(this);
            mRecyclerView.setAdapter(adapter);
        } else {
            adapter.addData(list);
        }

    }

    private void replaceData(List<ImageBean.ResultsBean> list) {
        if(adapter == null){
            adapter = new MyRecyclerAdapter(this,list);
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
            getData(++page,false);
        }
    }

    @Override
    public void click(String url) {

        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra("url",url);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    public void setMenuClick(){
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                dialog.show();

                return true;
            }
        });
    }

    private void initDialog() {
        dialog = new Dialog(this);
        View view = getLayoutInflater().inflate(R.layout.number_picker,null);
        dialog.setContentView(view);
        NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.number_picker_dialog);
        Button button = (Button) view.findViewById(R.id.ok_dialog);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(25);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tempPage = newVal;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = tempPage;
                getData(page,true);
                dialog.dismiss();
            }
        });


    }

}
