package com.steve.wanqureader.network;

import com.steve.wanqureader.WanquApplication;
import com.steve.wanqureader.utils.Constant;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by steve on 3/28/16.
 * This is the main entry point for network communication. Use this class for instancing REST services which do the
 * actual communication.
 */
public class RestClient {
    private static Retrofit mRetrofit;

    static {
        // enable logging
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        final Interceptor cacheControlInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                int maxAge = Constant.CACHE_TIME;
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            }
        };

        //setup cache
        File httpCacheDirectory = new File(WanquApplication.getContext().getCacheDir(), "responses");
        Cache cache = new Cache(httpCacheDirectory, Constant.CACHE_SIZE);

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(cacheControlInterceptor)
                .addInterceptor(interceptor)
//                .addNetworkInterceptor(new StethoInterceptor())
                .cache(cache) //add cache to the client
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static <T> T getService(Class<T> serviceClass) {
        return mRetrofit.create(serviceClass);
    }
}
