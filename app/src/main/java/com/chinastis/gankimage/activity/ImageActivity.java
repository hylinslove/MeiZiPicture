package com.chinastis.gankimage.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.chinastis.gankimage.R;
import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {

    private ImageView bigImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        bigImage = (ImageView) findViewById(R.id.big_image_image);

        Picasso
                .with(this)
                .load(getIntent().getStringExtra("url"))
                .into(bigImage);
    }
}
