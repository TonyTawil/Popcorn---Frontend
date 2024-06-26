package com.example.popcorn.Networking;

import android.content.Context;
import java.io.File;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {
            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(httpCacheDirectory, cacheSize);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.cache(cache);

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://popcorn-4gmf.onrender.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
