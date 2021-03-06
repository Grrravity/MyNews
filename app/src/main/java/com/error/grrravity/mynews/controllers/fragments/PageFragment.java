package com.error.grrravity.mynews.controllers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.error.grrravity.mynews.R;
import com.error.grrravity.mynews.models.APIArticles;
import com.error.grrravity.mynews.models.APIResult;
import com.error.grrravity.mynews.utils.NYTStreams;
import com.error.grrravity.mynews.utils.NetworkHelper;
import com.error.grrravity.mynews.utils.Preferences;
import com.error.grrravity.mynews.views.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static android.support.constraint.Constraints.TAG;

public class PageFragment extends Fragment implements RecyclerViewAdapter.onPageAdapterListener {

    // IDs
    @BindView(R.id.fragment_page_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_page_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.textView_bad_request)
    TextView textView;
    @BindView(R.id.fragment_progress_bar)
    ProgressBar progressBar;

    // Keys for bundle
    private static final String KEY_POSITION = "position";

    private int position;
    private List<APIResult> mArticlesResults;
    private Disposable mDisposable;
    private RecyclerViewAdapter mAdapter;
    private PageFragmentListener mListener;
    private String mSelectedSection = "viewed";

    private Preferences mPreferences;

    public PageFragment() {

    }

    // Create a new instance of PageFragment, and add data to its bundle.
    public static PageFragment newInstance(int position) {

        // Create new fragment
        PageFragment frag = new PageFragment();

        // Create bundle and add some data
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);

        return (frag);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPreferences = Preferences.getInstance(context);
        if (context instanceof PageFragmentListener) {
            mListener = (PageFragmentListener) context;
        } else {
            Log.d(TAG, context.getResources().getString(R.string.on_attach_page)
                    + getString(R.string.my_log));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Get layout of PageFragment
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar.setVisibility(View.VISIBLE);
        mArticlesResults = new ArrayList<>();

        // Get data from Bundle (created in method newInstance)

        if (getArguments() != null) {
            position = getArguments().getInt(KEY_POSITION);
        }

        fragmentSwitchRequest();
        configureRecyclerView();
        configureSwipeRefreshLayout();
        Log.i(getClass().getSimpleName(),
                view.getResources().getString(R.string.on_create_frag_number) + position
        + getString(R.string.my_log));
    }

    private void fragmentSwitchRequest() {
        switch (position) {
            case 0:
                if (NetworkHelper.isNetworkAvailable(getActivity())) {
                    executeHttpRequestTopStories();
                    Log.i(this.getResources().getString(R.string.tag_topstories),
                            this.getResources().getString(R.string.position) + position
                                    + getString(R.string.my_log));
                } else {
                    infoNoInternet();
                }
                break;
            case 1:
                if (NetworkHelper.isNetworkAvailable(getActivity())) {
                    executeHttpRequestMostPopular();
                    Log.i(this.getResources().getString(R.string.tag_mostpopular),
                            this.getResources().getString(R.string.position) + position
                                    + getString(R.string.my_log));
                } else {
                    infoNoInternet();
                }
                break;
            case 2:
                if (NetworkHelper.isNetworkAvailable(getActivity())) {
                    ArrayList<String> category = mPreferences.getCategory(0);
                    if (category.contains(getString(R.string.section_viewed)) || category.size() < 1) {
                        progressBar.setVisibility(View.GONE);
                    } else {
                        mSelectedSection = category.get(0);
                        executeHttpRequestSelectedSection(mSelectedSection);
                        Log.i(this.getResources().getString(R.string.tag_selectedcat),
                                this.getResources().getString(R.string.position) + position
                                        + getString(R.string.my_log));
                    }
                } else {
                    infoNoInternet();
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    private void disposeWhenDestroy() {
        if (this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();
    }

    //
    //Configuration

    //

    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragmentSwitchRequest();
            }
        });
    }

    private void configureRecyclerView() {
        this.mArticlesResults = new ArrayList<>();
        // Create mAdapter with user data sample
        this.mAdapter = new RecyclerViewAdapter(this.mArticlesResults, Glide.with(this),
                this);
        // Attach the Adapter to the recyclerView to create items
        this.mRecyclerView.setAdapter(this.mAdapter);
        // Set layout manager
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    //
    // HTTP REQUESTS
    //

    //API Request for TopStories
    private void executeHttpRequestTopStories() {
        mDisposable = NYTStreams.streamFetchArticles(
                this.getResources().getString(R.string.section_home))
                .subscribeWith(new DisposableObserver<APIArticles>() {
                    @Override
                    public void onNext(APIArticles articles) {
                        updateUI(articles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.setVisibility(View.VISIBLE);
                        Log.i(getClass().getSimpleName(), getString(R.string.onErrorTopStories)
                                + getString(R.string.my_log));
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                        Log.i(getClass().getSimpleName(),
                                getString(R.string.onCompleteTopStories) + position
                                        + getString(R.string.my_log));
                    }
                });
    }

    //API Request for MostPopular
    private void executeHttpRequestMostPopular() {
        mDisposable = NYTStreams.streamFetchArticlesMP(
                this.getResources().getString(R.string.section_viewed))
                .subscribeWith(new DisposableObserver<APIArticles>() {
                    @Override
                    public void onNext(APIArticles articles) {
                        updateUI(articles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.setVisibility(View.VISIBLE);
                        Log.i(getClass().getSimpleName(), getString(R.string.onErrorMostPopular)
                                + getString(R.string.my_log));
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                        Log.i(getClass().getSimpleName(),
                                getString(R.string.onCompleteMostPopular)+ position
                                        + getString(R.string.my_log));
                    }
                });
    }

    // API Request for SearchArticles
    private void executeHttpRequestSelectedSection(final String selectedSection) {
        mDisposable = NYTStreams.streamFetchArticles(selectedSection)
                .subscribeWith(new DisposableObserver<APIArticles>() {
                    @Override
                    public void onNext(APIArticles articles) {
                        updateUI(articles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.setVisibility(View.VISIBLE);
                        Log.i(getClass().getSimpleName(),
                                getString(R.string.onErrorSelectedSection)
                                        + getString(R.string.my_log));
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                        Log.i(getClass().getSimpleName(), selectedSection +
                                getString(R.string.onCompleteSelectedSection) + position
                                + getString(R.string.my_log));
                    }
                });

    }

    //
    // UI Management
    //

    //Update mAdapter to recyclerView
    private void updateUI(APIArticles articles) {
        if (mArticlesResults != null) {
            mArticlesResults.clear();
        }
        if (articles.getResult() != null) {
            mArticlesResults.addAll(articles.getResult());
            textView.setVisibility(View.GONE);
            if (mArticlesResults.size() == 0) {
                {
                    mArticlesResults.clear();
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(R.string.list_empty);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
            mAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        } else {
            Log.i(getClass().getSimpleName(), this.getResources().getString(R.string.articles_null)
                    + getString(R.string.my_log));
    }
    }

    // Stop refreshing when no internet.
    private void infoNoInternet() {
        textView.setVisibility(View.VISIBLE);
        textView.setText(R.string.no_internet);
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    //
    // ADAPTER STUFF
    //

    //Configure item click on RecyclerView
    @Override
    public void onArticleClicked(APIResult resultArticle) {
        Log.i(getClass().getSimpleName(),
                this.getResources().getString(R.string.position)+ position
                        + getString(R.string.my_log));
        mListener.callbackArticle(resultArticle);
    }

    public void updateContent(String section) {
        mSelectedSection = section;
        executeHttpRequestSelectedSection(mSelectedSection);
    }

    //Callback to PageFragment
    public interface PageFragmentListener {
        void callbackArticle(APIResult resultArticle);
    }

}