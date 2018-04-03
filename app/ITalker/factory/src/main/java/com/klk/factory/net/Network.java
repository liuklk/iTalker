package com.klk.factory.net;

import com.klk.common.Common;
import com.klk.factory.Factory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/4/3  13:56
 */

public class Network {
    /**
     *
     * @return
     */
    public static Retrofit getRetrofit(){

        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit.Builder builder = new Retrofit.Builder();

        return builder.baseUrl(Common.Constance.ROOT_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();

    }
}
