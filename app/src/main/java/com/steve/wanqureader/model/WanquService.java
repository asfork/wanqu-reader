package com.steve.wanqureader.model;

import com.steve.wanqureader.model.entitiy.IssueResult;
import com.steve.wanqureader.model.entitiy.IssuesResult;
import com.steve.wanqureader.model.entitiy.PostWrapper;
import com.steve.wanqureader.util.Constant;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Steve Zhang
 * 2/16/16
 * <p>
 * If it works, I created it. If not, I didn't.
 */
public class WanquService {
    private static WanquInterface wanquInterface;

    public static WanquInterface getClient() {
        if (wanquInterface == null) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(Constant.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            wanquInterface = client.create(WanquInterface.class);
        }

        return wanquInterface;
    }

    public interface WanquInterface {
        @GET("posts/random")
        Call<IssueResult> getRandomPost();

        @GET("issues")
        Call<IssuesResult> getIssues();

        @GET("issue/latest")
        Call<IssueResult> getLatestIssue();
     }
}
