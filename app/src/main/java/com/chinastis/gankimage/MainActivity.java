package com.chinastis.gankimage;

import android.graphics.Color;
import android.graphics.ImageFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;

import com.chinastis.gankimage.adapter.MediaViewPagerAdapter;
import com.chinastis.gankimage.fragment.ImageFragment;
import com.chinastis.gankimage.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity  {

    private Toolbar toolbar;
    private TabLayout mTab;
    private ViewPager contentViewPager;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();

        initView();

    }

    private void initFragment() {
        fragments = new ArrayList<>();

        ImageFragment imageFragment = new ImageFragment();
        VideoFragment videoFragment = new VideoFragment();
        fragments.add(imageFragment);
        fragments.add(videoFragment);

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);


        mTab = (TabLayout) findViewById(R.id.tab_main);
        contentViewPager = (ViewPager) findViewById(R.id.pager_main);

        MediaViewPagerAdapter adapter = new MediaViewPagerAdapter(getSupportFragmentManager(),fragments);
        contentViewPager.setAdapter(adapter);
        mTab.setupWithViewPager(contentViewPager);

    }


}
