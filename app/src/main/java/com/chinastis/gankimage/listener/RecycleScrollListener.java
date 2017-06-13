package com.chinastis.gankimage.listener;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by MENG on 2017/6/13.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class RecycleScrollListener implements RecyclerView.OnScrollChangeListener {
    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

    }
}
