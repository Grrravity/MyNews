package com.error.grrravity.mynews.controllers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.error.grrravity.mynews.R;
import com.error.grrravity.mynews.models.APIArticles;
import com.error.grrravity.mynews.models.APIResult;
import com.error.grrravity.mynews.models.APISearch;
import com.error.grrravity.mynews.utils.NYTStreams;
import com.error.grrravity.mynews.views.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static android.support.constraint.Constraints.TAG;

public class PageFragment extends Fragment implements RecyclerViewAdapter.onPageAdapterListener {

    // IDs
    @BindView(R.id.fragment_page_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.fragment_page_swipe_container) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.textView_bad_request) TextView textView;
    @BindView(R.id.fragment_progress_bar) ProgressBar progressBar;



    // Keys for bundle
    private static final String KEY_POSITION = "position";

    private int position;
    private List<APIResult> mArticlesResults;
    private Disposable mDisposable;
    private RecyclerViewAdapter mAdapter;
    private PageFragmentListener mListener;
    private String mSelectedSection = "viewed";


    public PageFragment() { }

    // Create a new instance of PageFragment, and add data to its bundle.
    public static PageFragment newInstance(int position) {

        // Create new fragment
        PageFragment frag = new PageFragment();

        // Create bundle and add some data
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);

        return(frag);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof PageFragmentListener) {
            mListener = (PageFragmentListener)context;
        }
        else {
            Log.d(TAG, "onAttach: parent activity must implement PageFragmentListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Get layout of PageFragment
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        ButterKnife.bind(this, view);
        progressBar.setVisibility(View.VISIBLE);
        mArticlesResults = new ArrayList<>();

        // Get data from Bundle (created in method newInstance)

        if (getArguments() != null) {
            position = getArguments().getInt(KEY_POSITION);
        }

        switch (position) {
            case 0:
                executeHttpRequestTopStories();
                break;
            case 1:
                executeHttpRequestMostPopular();
                break;
            case 2:
                executeHttpRequestSelectedSection(mSelectedSection);
                break;
        }

        configureRecyclerView();
        configureSwipeRefreshLayout();
        Log.e(getClass().getSimpleName(), "onCreateView called for fragment number "+position);

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
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

    //Configure SwipeRefreshLayout
    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                switch (position) {
                    case 0:
                        executeHttpRequestTopStories();
                        break;
                    case 1:
                        executeHttpRequestMostPopular();
                        break;
                    case 2:
                        executeHttpRequestSelectedSection(mSelectedSection);
                        break;
                }
            }
        });
    }

    //
    // HTTP REQUESTS
    //

    //API Request for TopStories
    private void executeHttpRequestTopStories( ){
        mDisposable = NYTStreams.streamFetchArticles( "home")
                .subscribeWith(new DisposableObserver<APIArticles>() {
                    @Override
                    public void onNext(APIArticles articles) {
                        updateUI(articles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.setVisibility(View.VISIBLE);
                        Log.e(getClass().getSimpleName(), getString(R.string.onErrorTopStories));
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                        Log.e("Test", getString(R.string.onCompleteTopStories));
                    }
                });
    }
    //API Request for MostPopular
    private void executeHttpRequestMostPopular( ){
        mDisposable = NYTStreams.streamFetchArticlesMP( "viewed")
                .subscribeWith(new DisposableObserver <APIArticles>() {
                    @Override
                    public void onNext(APIArticles articles) {
                        updateUI(articles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.setVisibility(View.VISIBLE);
                        Log.e(getClass().getSimpleName(), getString(R.string.onErrorMostPopular));
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                        Log.e("Test", getString(R.string.onCompleteMostPopular));
                    }
                });
    }

    // API Request for SearchArticles
    private void executeHttpRequestSelectedSection(final String selectedSection){
        mDisposable = NYTStreams.streamFetchArticles(selectedSection)
                .subscribeWith(new DisposableObserver<APIArticles>() {
            @Override
            public void onNext(APIArticles articles) {
                updateUI(articles);
            }

            @Override
            public void onError(Throwable e) {
                textView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onComplete() {
                progressBar.setVisibility(View.GONE);
                Log.e("Test", "Selected section, section " +selectedSection+ " is charged");
            }
        });

    }

    private void disposeWhenDestroy() {
        if (this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();
    }

    //
    // UI Management
    //

    //Update mAdapter to recyclerView
    private void updateUI(APIArticles articles){
        if(mArticlesResults != null){
            mArticlesResults.clear();
        }
        if(articles.getResult() != null)
        {
            mArticlesResults.addAll(articles.getResult());
            textView.setVisibility(View.GONE);
            if(mArticlesResults.size() == 0){
                {
                    mArticlesResults.clear();
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(R.string.list_empty);}
            }
            mAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
        else
        {
            Log.e("Test", "articles.getResult() is null");
        }
    }

    //Update mAdapter to recyclerView
    private void updateUISearch(APISearch search){
        if(mArticlesResults != null){
            mArticlesResults.clear();
        }
        if(search.getResult() != null)
        {
            mArticlesResults.addAll(search.getResult());
            textView.setVisibility(View.GONE);
            if(mArticlesResults.size() == 0){
                {
                    mArticlesResults.clear();
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(R.string.list_empty);}
            }
            mAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
        else
        {
            Log.e("Test", "articles.getResult() is null");
        }
    }


    //
    // ADAPTER STUFF
    //

    //Configure item click on RecyclerView
    @Override
    public void onArticleClicked(APIResult resultTopStories) {
        Log.e("TAG", "Position : ");
        mListener.callbackArticle(resultTopStories);
    }

    public void updateContent(String section) {
        mSelectedSection = section;
        executeHttpRequestSelectedSection(mSelectedSection);
    }

    //Callback to PageFragment
    public interface PageFragmentListener{
        void callbackArticle(APIResult resultTopStories);
    }

}