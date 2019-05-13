package com.error.grrravity.mynews.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.error.grrravity.mynews.R;
import com.error.grrravity.mynews.models.APIDoc;

import butterknife.BindView;
import butterknife.ButterKnife;

class SearchViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_search_item_title) TextView mTVTitle;
    @BindView(R.id.fragment_search_item_date) TextView mTVDate;
    @BindView(R.id.fragment_search_item_image) ImageView mImageView;
    @BindView(R.id.fragment_search_item_section) TextView mTVSection;
    @BindView(R.id.relativeLayoutSearch) RelativeLayout mRelativeLayout;

    static final String URL = "https://static01.nyt.com/";

    SearchViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    //Update search item

    void updateWithResult (final APIDoc articles, RequestManager glide,
                           final SearchRecyclerViewAdapter.onSearchArticleAdapterListener callback){
        if(articles.getHeadline() != null) {
            this.mTVTitle.setText(articles.getHeadline().getMain());
        }
        if(articles.getSectionName() != null) {
            this.mTVSection.setText(articles.getSectionName());
        } else {
            mTVSection.setText("");
        }
        if(articles.getPubDate() != null){
            String date = articles.getPubDate().substring(0,10);
            this.mTVDate.setText(date);
        }

        if(!articles.getMultimedia().isEmpty()){
            String mUrl = URL + articles.getMultimedia().get(0).getUrl();
            glide.load(mUrl).apply(RequestOptions.centerCropTransform()).into(mImageView);
        } else{
            mImageView.setImageResource(R.drawable.y_u_no_image);
        }
        this.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onArticleClicked(articles);
            }
        });
    }
}
