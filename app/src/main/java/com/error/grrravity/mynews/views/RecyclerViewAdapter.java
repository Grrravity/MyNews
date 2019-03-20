package com.error.grrravity.mynews.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.error.grrravity.mynews.R;
import com.error.grrravity.mynews.controllers.fragments.PageFragment;
import com.error.grrravity.mynews.models.APIResult;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private final onPageAdapterListener mListener;
    // DATAs
    private RequestManager glide;
    private List<APIResult> topStories;

    //Constructor
    public RecyclerViewAdapter(List<APIResult> topStories, RequestManager glide,
                               PageFragment pageAdapterListener) {
        this.mListener = pageAdapterListener;
        this.topStories = topStories;
        this.glide = glide;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        //Create view holder and inflate the xml layout
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_page, parent, false);

        return new RecyclerViewHolder(view);
    }

    // Update viewholder with topstorie
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder viewHolder, int position) {
        viewHolder.updateWithResult(this.topStories.get(position), this.glide, mListener);
    }

    // Return the number of items in the list
    @Override
    public int getItemCount() {
        return this.topStories.size();
    }


    //Callback to items position
    public interface onPageAdapterListener {
        void onArticleClicked(APIResult resultTopStories);
    }
}