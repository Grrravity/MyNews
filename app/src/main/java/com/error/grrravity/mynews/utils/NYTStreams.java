package com.error.grrravity.mynews.utils;

import com.error.grrravity.mynews.models.APIArticles;
import com.error.grrravity.mynews.models.APISearch;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NYTStreams {

    static String API_KEY = "KbIgXrJKRwL9e8BQHLQum6PLaxjeRC2k";

    // getting articles
    public static Observable<APIArticles> streamFetchArticles (String section){
        NYTServices nytService = NYTServices.retrofit.get().create(NYTServices.class);
        return nytService.getBySection(section, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<APIArticles> streamFetchArticlesMP (String section){
        NYTServices nytService = NYTServices.retrofit.get().create(NYTServices.class);
        return nytService.getBySectionMP(section, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }


    // public static Observable<APIArticles>
    // streamFetchMostPopularArticles(String section, String api_key){
    //     NYTServices nytService = NYTServices.retrofit.get().create(NYTServices.class);
    //     return nytService.getBySectionMp(section, api_key)
    //             .subscribeOn(Schedulers.io())
    //             .observeOn(AndroidSchedulers.mainThread())
    //             .timeout(10, TimeUnit.SECONDS);
    // }

    // getting searched articles
    public static Observable<APISearch> streamFetchSearchArticles
    (String search, List<String> category, String beginDate, String endDate){
        NYTServices nytService = NYTServices.retrofit.get().create(NYTServices.class);
        return nytService.getSearch(API_KEY, search, category, beginDate, endDate, "relevance")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

}