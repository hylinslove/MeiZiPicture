package com.chinastis.gankimage.net;

import com.chinastis.gankimage.Constant.MyUrl;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MENG on 2017/6/13.
 */

public class RetrofitClient {

    private static Retrofit retrofit;

    public static  Retrofit getRetrofit() {
        if(retrofit == null) {
            synchronized (RetrofitClient.class) {
                if(retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(MyUrl.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }

        return retrofit;
    }



}
