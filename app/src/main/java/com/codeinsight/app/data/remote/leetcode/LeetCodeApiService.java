package com.codeinsight.app.data.remote.leetcode;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import java.util.concurrent.TimeUnit;

public interface LeetCodeApiService {

    @Headers({
        "Content-Type: application/json",
        "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) CodeInsight-Android/1.0"
    })
    @POST("graphql")
    Call<LeetCodeResponse> queryGraphQL(@Body GraphQLRequest request);

    class Factory {
        private static final String BASE_URL = "https://leetcode.com/";

        public static LeetCodeApiService create() {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

            OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

            return retrofit.create(LeetCodeApiService.class);
        }
    }
}

