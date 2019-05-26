package com.error.grrravity.mynews.utils;

import com.error.grrravity.mynews.models.APIArticles;
import com.error.grrravity.mynews.models.APISearch;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NYTServices {

    @GET("svc/topstories/v2/{section}.json?")
    Observable<APIArticles> getBySection(@Path("section") String section, @Query("api-key") String API_KEY);

    @GET("svc/mostpopular/v2/{section}/1.json?")
    Observable<APIArticles> getBySectionMP(@Path("section") String section, @Query("api-key") String API_KEY);

    @GET("svc/search/v2/articlesearch.json?sort=newest&fl=web_url,headline,multimedia,news_desk,pub_date")
    Observable<APISearch> getSearch(@Query("api-key") String API_KEY,
                                    @Query("q") String search,
                                    @Query("fq") List<String> category,
                                    @Query("begin_date") String beginDate,
                                    @Query("end_date") String endDate,
                                    @Query("sort") String sort

    );

    ThreadLocal<Retrofit> retrofit = new ThreadLocal<Retrofit>() {
        @Override
        protected Retrofit initialValue() {
            return new Retrofit.Builder()
                    .baseUrl("https://api.nytimes.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
    };

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();
}