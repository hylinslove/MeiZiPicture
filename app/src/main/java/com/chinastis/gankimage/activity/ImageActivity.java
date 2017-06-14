package com.chinastis.gankimage.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.chinastis.gankimage.R;
import com.chinastis.gankimage.util.PermissionUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ImageActivity extends AppCompatActivity {

    private ImageView bigImage;
    private String filePath;
    private File imageFile;

    private String url;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        bigImage = (ImageView) findViewById(R.id.big_image_image);
        registerForContextMenu(bigImage);

        url = getIntent().getStringExtra("url");

        Picasso
                .with(this)
                .load(url)
                .into(bigImage);


        filePath = Environment.getExternalStorageDirectory().getPath()+File.separator+"MENG"+File.separator;

        PermissionUtil.verifyStoragePermissions(this);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.save_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        saveImageToGallery(this);
        return true;
    }


    public  void saveImageToGallery(Context context) {

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                // 首先保存图片

                String fileName = System.currentTimeMillis()+".jpg";

                imageFile = new File(filePath+File.separator+fileName);
                if(!imageFile.getParentFile().exists()){
                    imageFile.getParentFile().mkdirs();
                }

                Log.e("MENG","是否存在"+imageFile.exists());
                try {
                    FileOutputStream fos = new FileOutputStream(imageFile);

                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                // 其次把文件插入到系统图库
//                try {
//                    MediaStore.Images.Media.insertImage(ImageActivity.this.getContentResolver(),
//                            imageFile.getAbsolutePath(), fileName, null);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                // 最后通知图库更新
                ImageActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.fromFile( imageFile)));

                Snackbar.make(bigImage,"保存成功",Snackbar.LENGTH_SHORT).show();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        Picasso.with(this)
                .load(url)
                .into(target);

    }
}
