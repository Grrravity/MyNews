package com.error.grrravity.mynews;

import com.error.grrravity.mynews.models.APIArticles;
import com.error.grrravity.mynews.models.APISearch;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface NYTServiceTest {

    @GET("/topStories_200_response.json")
    Observable<APIArticles> getTopStoriesArticles();


    @GET("/mostPopular_200_response.json")
    Observable<APIArticles> getMostPopularArticles();

    @GET("/searchArticle_200_response.json")
    Observable<APISearch> getSearch();

    OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ArticlesFragmentTest.server.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClientBuilder.build())
            .build();

}
