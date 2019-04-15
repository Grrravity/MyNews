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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.error.grrravity.mynews.R;
import com.error.grrravity.mynews.models.APIDoc;
import com.error.grrravity.mynews.models.APISearch;
import com.error.grrravity.mynews.views.SearchRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;
import static com.error.grrravity.mynews.controllers.activities.SearchActivity.SEARCHED_ARTICLE;

public class SearchResultFragment extends Fragment implements View.OnClickListener,
        SearchRecyclerViewAdapter.onSearchArticleAdapterListener{

    @BindView(R.id.fragment_search_result_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.fragment_search_result_swipe_container) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.sr_textview_empty) TextView mTextView;

    private List<APIDoc> response;
    private APISearch mAPISearch;
    private SearchRecyclerViewAdapter mAdapter;
    private SearchResultFragmentListener mListener;

    public SearchResultFragment(){ }

    // Attach callback


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof SearchResultFragmentListener){
            mListener = (SearchResultFragmentListener)context;
        }
        else {
            Log.d(TAG, "onAttach: parrent Activity must implement MainFragmentListener");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //inflate layout for this fragment
        View view = inflater.inflate(R.layout.activity_search_result_fragment,
                container, false);
        ButterKnife.bind(this, view);
        getBundle();

        configureRecyclerView();
        configureSwipeRefreshLayout();
        updateUI();

        return view;
    }



    // get bundle with user params
    private void getBundle() {
        if(getArguments() != null){
            mAPISearch = getArguments().getParcelable(SEARCHED_ARTICLE);
        }
    }

    // CONFIGs

    //Configure RecyclerView & tabs

    private void configureRecyclerView(){
        this.response = new ArrayList<APIDoc>();
        //creating adapter using user data sample
        this.mAdapter = new SearchRecyclerViewAdapter(this.response,
                Glide.with(this), this);
        // attach adapter to recyclerview
        this.mRecyclerView.setAdapter(this.mAdapter);
        // set layout manager
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    //configure SwipeRefreshLayout
    private void configureSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateUI();
            }
        });
    }

    //UPDATE UI
    private void updateUI(){
        if(response != null){
            response.clear();
        }
        if(mAPISearch.getResponse().getDocs() != null){
            response.addAll(mAPISearch.getResponse().getDocs());

            if(response.size() == 0){
                mTextView.setVisibility(View.VISIBLE);
                mTextView.setText(R.string.list_empty);
            }
            mAdapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
        }
        else  {
            Log.e("Test", "mAPISearch.getResponse().getDocs() is null");
        }
    }

    @Override
    public void onClick(View view) {   }

    // Launch callback

    @Override
    public void onArticleClicked(APIDoc resultTopStories) {
        mListener.callbackSearchArticle(resultTopStories);
    }

    // Callback
    public interface SearchResultFragmentListener{
        void callbackSearchArticle(APIDoc APISearch);
    }

}