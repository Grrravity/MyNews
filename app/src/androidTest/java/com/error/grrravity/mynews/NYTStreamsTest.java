package com.error.grrravity.mynews;

import com.error.grrravity.mynews.models.APIArticles;
import com.error.grrravity.mynews.models.APISearch;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NYTStreamsTest {

    public static Observable<APIArticles> streamFetchTopStoriesTest(){
        NYTServiceTest nytServiceTest = NYTServiceTest.retrofit.create(NYTServiceTest.class);
        return nytServiceTest.getTopStoriesArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10000, TimeUnit.SECONDS);
    }

    public static Observable<APIArticles> streamFetchMostPopular(){
        NYTServiceTest nytServiceTest = NYTServiceTest.retrofit.create(NYTServiceTest.class);
        return nytServiceTest.getMostPopularArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10000, TimeUnit.SECONDS);
    }

    public static Observable<APISearch>  streamSearch(){
        NYTServiceTest nytServiceTest = NYTServiceTest.retrofit.create(NYTServiceTest.class);
        return nytServiceTest.getSearch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10000, TimeUnit.SECONDS);
    }

}