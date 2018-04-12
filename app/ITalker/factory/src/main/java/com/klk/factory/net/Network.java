package com.klk.factory.net;

import android.text.TextUtils;

import com.klk.common.Common;
import com.klk.factory.Factory;
import com.klk.factory.persistence.Account;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/4/3  13:56
 */

public class Network {

    private static Network instance;
    private Retrofit retrofit;
    static {
        instance = new Network();
    }

    private Network(){}
    /**
     *构建一个Retrofit
     */
    public static Retrofit getRetrofit(){

        if(instance.retrofit!=null){
            return instance.retrofit ;
        }
        OkHttpClient client = new OkHttpClient.Builder().
                addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        Request.Builder builder = request.newBuilder();
                        String token = Account.getToken();
                        if(!TextUtils.isEmpty(token)){
                            //在所有的请求中注入token
                            builder.addHeader("token", Account.getToken());
                        }
                        builder.addHeader("Content-Type","application/json");
                        Request newRequest = builder.build();
                        return chain.proceed(newRequest);
                    }
                }).build();

        Retrofit.Builder builder = new Retrofit.Builder();

        instance.retrofit = builder.baseUrl(Common.Constance.ROOT_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();

        return instance.retrofit;

    }

    public static RemoteService remote(){
        return Network.getRetrofit().create(RemoteService.class);
    }
}
