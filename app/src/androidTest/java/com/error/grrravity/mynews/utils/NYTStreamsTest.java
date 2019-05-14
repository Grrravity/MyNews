package com.error.grrravity.mynews.utils;

import com.error.grrravity.mynews.models.APIArticles;
import com.error.grrravity.mynews.models.APISearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class NYTStreamsTest {


    @Test
    public void streamTopStories() {

        // Stream
        Observable<APIArticles> mTopStoriesObservable = NYTStreams.streamFetchArticles("home");

        // Create Observer
        TestObserver<APIArticles> mTestObserver = new TestObserver<>();

        // Launch Observable
        mTopStoriesObservable.subscribeWith(mTestObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();


        // Get List
        APIArticles mTopStories = mTestObserver.values().get(0);

        // Check if connection status is OK
        assertEquals("OK", mTopStories.getStatus());

        // Check if results exist
        assertNotNull(mTopStories.getNumResults());

    }


    @Test
    public void streamMostPopular() {
        // Stream
        Observable<APIArticles> mMostPopularObservable = NYTStreams.streamFetchArticlesMP("viewed");

        // Create Observer
        TestObserver<APIArticles> mTestObserver = new TestObserver<>();

        // Launch Observable
        mMostPopularObservable.subscribeWith(mTestObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();


        // Get List
        APIArticles mMostPopular = mTestObserver.values().get(0);

        // Check if connection status is OK
        assertEquals("OK", mMostPopular.getStatus());

        // Check if results exist
        assertNotNull(mMostPopular.getNumResults());
    }

    @Test
    public void streamArticleSearch() {

        // Query terms
        String mQuery = "Trump";

        // Stream
        Observable<APISearch> mArticleSearchObservable = NYTStreams.streamFetchSearchArticles
                (mQuery, Collections.singletonList("business"), "20190501", "20190514" );

        // Create Observer
        TestObserver<APISearch> mTestObserver = new TestObserver<>();

        // Create Observable
        mArticleSearchObservable.subscribeWith(mTestObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        // Get List
        APISearch mArticleSearch = mTestObserver.values().get(0);

        // Check if status is OK
        assertEquals("OK", mArticleSearch.getStatus());

        // Check if results exist
        assertNotNull(mArticleSearch.getResponse());
    }

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