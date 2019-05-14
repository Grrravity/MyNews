package com.error.grrravity.mynews.controllers.activities;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.error.grrravity.mynews.R;
import com.error.grrravity.mynews.models.APIArticles;
import com.error.grrravity.mynews.utils.NYTStreams;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.TestObserver;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    //Test View of NavigationDrawer
    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is(
                                                        "android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction checkedTextView = onView(
                allOf(withId(R.id.design_menu_item_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_navigation_view),
                                        4),
                                0),
                        isDisplayed()));
        checkedTextView.check(matches(isDisplayed()));

        ViewInteraction checkedTextView2 = onView(
                allOf(withId(R.id.design_menu_item_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_navigation_view),
                                        8),
                                0),
                        isDisplayed()));
        checkedTextView2.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public void dllNYTArticles() throws Exception {
        // Get the stream
        Observable<APIArticles> observableNYTStories =
                NYTStreams.streamFetchArticles("home");
        // Create a new TestObserver
        TestObserver<APIArticles> testObserver = new TestObserver<>();
        // Launch observable
        observableNYTStories.subscribeWith(testObserver)
                .assertNoErrors()// Check if no errors
                .assertNoTimeout() // Check if no Timeout
                .awaitTerminalEvent(); // Await the stream terminated before continue

        // Get list of articles results
        APIArticles articleResultsDownload = testObserver.values().get(0);

        assertThat("article téléchargé",
                articleResultsDownload.getResult() != null);

    }

    @Test
    public void checkTabLayoutDisplayed() {
        onView(withId(R.id.toolbar))
                .perform(click())
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkNavHeaderDisplay() throws Exception {
        onView(withContentDescription(R.string.nav_drawer_open)).perform(click());
        onView(withId(R.id.activity_main_nav_view)).check(matches(isDisplayed()));
    }

    @Test
    public void clickSearchTest() throws Exception {
        onView(withId(R.id.menu_activity_main_search)).perform(click());
        onView(withId(R.id.searchButton)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void toolbarMenuClickTest() throws Exception {
        onView(withId(R.id.menu_activity_main_params)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.menu_activity_main_params)).perform(click());
        onView(withText("Notifications")).inRoot(isPlatformPopup()).check(matches(isCompletelyDisplayed()));
        onView(withText("Notifications")).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.switchButtonNotif)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void swipeFragmentTest() throws Exception {
        onView(allOf(isDescendantOfA(withId(R.id.activity_main_tabs))
                ,withText("Section not\r\nyet selected"))).perform(click());
        onView(withId(R.id.activity_main_viewpager)).check(matches(inPage(2)));
        for (int i = 0; i < 2 ; i++){
            onView(withId(R.id.activity_main_viewpager)).perform(swipeRight());
        }
        onView(withId(R.id.activity_main_viewpager)).check(matches(inPage(0)));
    }

    //Currently not working due to espresso error on recyclerview items
   // @Test
   // public void clickRecyclerView() {
   //     // Click on the RecyclerView item at position 2
   //     onView(allOf(withId(R.id.fragment_page_recycler_view), isDisplayed()))
   //             .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
   // }

    @NonNull
    public static Matcher<View> inPage(final int page) {

        return new BoundedMatcher<View, ViewPager>(ViewPager.class) {

            @Override
            public void describeTo(final Description description) {
                description.appendText("in page: " + page);
            }

            @Override
            public boolean matchesSafely(final ViewPager viewPager) {
                return viewPager.getCurrentItem() == page;
            }
        };
    }

}