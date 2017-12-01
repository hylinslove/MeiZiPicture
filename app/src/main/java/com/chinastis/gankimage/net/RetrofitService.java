package com.chinastis.gankimage.net;

import com.chinastis.gankimage.bean.ImageBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by MENG on 2017/6/13.
 */

public interface RetrofitService {

//    @GET("福利/{num}/{page}")
//    Observable<ImageBean> getImageData(@Path("num") int num, @Path("page") int page);

    @GET("random/data/福利/{num}")
    Observable<ImageBean> getImageData(@Path("num") int num);

    @GET("random/data/休息视频/{num}")
    Observable<ImageBean> getVideoData(@Path("num") int num);
}
