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

    @BindView(R.id.fragment_page_item_title) TextView mTVTitle;
    @BindView(R.id.fragment_page_item_date) TextView mTVDate;
    @BindView(R.id.fragment_page_item_image) ImageView mImageView;
    @BindView(R.id.fragment_page_item_section) TextView mTVSection;
    @BindView(R.id.relativeLayout) RelativeLayout mRelativeLayout;

    static final String URL = "https://api.nytimes.com/";

    SearchViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    //Update search item

    void updateWithResult (final APIDoc article, RequestManager glide,
                           final SearchRecyclerViewAdapter.onSearchArticleAdapterListener callback){
        this.mTVTitle.setText((article).getAPIHeadline().getMain());
        this.mTVSection.setText(article.getSectionName());
        if(article.getPubDate() != null){
            String date = article.getPubDate().substring(0,10);
            this.mTVDate.setText(date);
        }

        if(!article.getMultimedia().isEmpty()){
            String mUrl = URL + article.getMultimedia().get(0).getUrl();
            glide.load(mUrl).apply(RequestOptions.centerCropTransform()).into(mImageView);
        } else{
            mImageView.setImageResource(R.mipmap.ic_launcher);
        }
        this.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onArticleClicked(article);
            }
        });
    }
}
