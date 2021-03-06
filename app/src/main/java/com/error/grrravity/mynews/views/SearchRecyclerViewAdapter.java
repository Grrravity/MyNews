package com.error.grrravity.mynews.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.error.grrravity.mynews.R;
import com.error.grrravity.mynews.models.APIDoc;

import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private final onSearchArticleAdapterListener mListener;

    // DATA
    private List<APIDoc> mSearch;
    private RequestManager mGlide;

    //Constructor
    public SearchRecyclerViewAdapter(List<APIDoc> search, RequestManager glide,
                                     onSearchArticleAdapterListener searchArticleAdapterListener) {
        this.mListener = searchArticleAdapterListener;
        this.mSearch = search;
        this.mGlide = glide;

    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Creating view holder and inflate layout
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_search_item, parent, false);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder viewHolder, int position) {
        if (mSearch != null && mSearch.size() >= position + 1 && mSearch.get(position) != null) {
            viewHolder.updateWithResult(this.mSearch.get(position), this.mGlide, mListener);
        }
    }

    @Override
    public int getItemCount() {
        return this.mSearch.size();
    }

    public interface onSearchArticleAdapterListener {
        void onArticleClicked(APIDoc resultTopStories);
    }
}