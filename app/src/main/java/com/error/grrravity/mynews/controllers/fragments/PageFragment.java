package com.error.grrravity.mynews.controllers.fragments;

import android.content.Context;
import android.os.Bundle;
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
    @BindView(R.id.fragment_page_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_page_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.textView_bad_request)
    TextView textView;
    @BindView(R.id.fragment_progress_bar)
    ProgressBar progressBar;



    // Keys for bundle
    private static final String KEY_POSITION="position";

    private int position;
    private List<APIResult> articlesResults;
    private Disposable disposable;
    private RecyclerViewAdapter adapter;
    private PageFragmentListener mListener;
    private String mSelectedSection = "Business";


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Get layout of PageFragment
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        ButterKnife.bind(this, view);
        articlesResults = new ArrayList<>();

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
                //TODO taking values from selected articles and put it in the HTTP request
                executeHttpRequestSearchArticles(mSelectedSection);
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
        this.articlesResults = new ArrayList<>();
        // Create adapter passing in the sample user data
        this.adapter = new RecyclerViewAdapter(this.articlesResults, Glide.with(this), this);
        // Attach the adapter to the recyclerView to populate items
        this.mRecyclerView.setAdapter(this.adapter);
        // Set layout manager to position the items
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
                        executeHttpRequestSearchArticles(mSelectedSection);
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
        disposable = NYTStreams.streamFetchArticles( "home")
                .subscribeWith(new DisposableObserver<APIArticles>() {
                    @Override
                    public void onNext(APIArticles articles) {
                        updateUI(articles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(getClass().getSimpleName(), getString(R.string.onErrorTopStories));
                    }

                    @Override
                    public void onComplete() {
                        Log.e(getClass().getSimpleName(), getString(R.string.onCompleteTopStories));
                    }
                });
    }
    //API Request for MostPopular
    private void executeHttpRequestMostPopular( ){
        disposable = NYTStreams.streamFetchArticlesMP( "home")
                .subscribeWith(new DisposableObserver <APIArticles>() {
                    @Override
                    public void onNext(APIArticles articles) {
                        updateUI(articles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(getClass().getSimpleName(), getString(R.string.onErrorMostPopular));
                    }

                    @Override
                    public void onComplete() {
                        Log.e(getClass().getSimpleName(), getString(R.string.onCompleteMostPopular));
                    }
                });
    }

    // API Request for SearchArticles
    private void executeHttpRequestSearchArticles(String selectedSection){
        String search;
        List<String> category;
        String beginDate;
        String endDate;

        if (selectedSection.equals("Business")){
            search = "Business";
            category = Collections.singletonList("MostPopular");
            beginDate = "01012019";
            endDate = "01032019";
        }
        else {
            search = "Business";
            category = Collections.singletonList("MostPopular");
            beginDate = "01032019";
            endDate = "01012019";
        }

        disposable = NYTStreams.streamFetchSearchArticles("",search, category,
                beginDate, endDate)
                .subscribeWith(new DisposableObserver<APISearch>() {
                    @Override
                    public void onNext(APISearch search) {
                        updateUISearch(search);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                        Log.e("Test", "TopStories, section Business is charged");
                    }
                });

    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    //
    // UI Management
    //

    //Update adapter to recyclerView
    private void updateUI(APIArticles articles){
        if(articlesResults != null){
            articlesResults.clear();
        }
        if(articles.getResult() != null)
        {
            articlesResults.addAll(articles.getResult());
            textView.setVisibility(View.GONE);
            if(articlesResults.size() == 0){
                {
                    articlesResults.clear();
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(R.string.list_empty);}
            }
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
        else
        {
            Log.e("Test", "articles.getResult() is null");
        }
    }

    //Update adapter to recyclerView
    private void updateUISearch(APISearch search){
        if(articlesResults != null){
            articlesResults.clear();
        }
        if(search.getResult() != null)
        {
            articlesResults.addAll(search.getResult());
            textView.setVisibility(View.GONE);
            if(articlesResults.size() == 0){
                {
                    articlesResults.clear();
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(R.string.list_empty);}
            }
            adapter.notifyDataSetChanged();
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
        executeHttpRequestSearchArticles(mSelectedSection);
    }

    //Callback to PageFragment
    public interface PageFragmentListener{
        void callbackArticle(APIResult resultTopStories);
    }

}