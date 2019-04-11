package com.error.grrravity.mynews;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.error.grrravity.mynews.models.APIArticles;
import com.error.grrravity.mynews.models.APIDoc;
import com.error.grrravity.mynews.models.APIResult;
import com.error.grrravity.mynews.models.APISearch;
import com.error.grrravity.mynews.utils.ExampleJsonArticles;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ArticlesFragmentTest extends InstrumentationTestCase {

        //Mockito mock the server
        @ClassRule
        public static MockWebServer server;

        @BeforeClass
        public static void setUpClass() throws Exception {
        server = new MockWebServer();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        @Before
        public void setUp() throws Exception{
        try {
            super.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    }

        //Test MostPopular
        @Test
        public void fetchMostPopularTest() throws InterruptedException {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(ExampleJsonArticles.jsonMostPopular));

        Observable<APIArticles> observableArticles = NYTStreamsTest.streamFetchMostPopular();
        TestObserver<APIArticles> testObserver = new TestObserver<>();

        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<APIResult> articles = testObserver.values().get(0).getResult();

        assertThat("The result list is not empty", !articles.isEmpty());

    }

        //Test TopStories
        @Test
        public void fetchTopStoriesTest()  {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(ExampleJsonArticles.jsonTopStories));

        Observable<APIArticles> observableArticles = NYTStreamsTest.streamFetchTopStoriesTest();
        TestObserver<APIArticles> testObserver = new TestObserver<>();

        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        List<APIResult> articles = testObserver.values().get(0).getResult();

        assertThat("The result list is not empty", !articles.isEmpty());
    }

        @Test
        public void fetchSearchTest()  {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(ExampleJsonArticles.jsonSearch));

        Observable<APISearch> observableResult = NYTStreamsTest.streamSearch();
        TestObserver<APISearch> testObserver = new TestObserver<>();

        observableResult.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        List<APIDoc> docs = testObserver.values().get(0).getResponse().getDocs();
        Log.e("test", "test");
        assertThat("The result list is not empty", !docs.isEmpty());
    }

}
