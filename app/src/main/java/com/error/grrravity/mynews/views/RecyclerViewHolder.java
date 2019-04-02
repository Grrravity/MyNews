package com.error.grrravity.mynews.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.error.grrravity.mynews.R;
import com.error.grrravity.mynews.models.APIResult;

class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private final TextView mTextViewTitle;
    private final TextView mTextViewDate;
    private final ImageView mImageView;
    private final TextView mTextViewSection;
    private final RelativeLayout mRelativeLayout;

    private static final String SUBSECTION = " > ";

    // IDs
    // @BindView(R.id.fragment_page_item_title) TextView mTextViewTitle;
    // @BindView(R.id.fragment_page_item_date) TextView mTextViewDate;
    // @BindView(R.id.fragment_page_item_image) ImageView mImageView;
    // @BindView(R.id.fragment_page_item_section) TextView mTextViewSection;
    // @BindView(R.id.mRelativeLayout) RelativeLayout mRelativeLayout;
    //

    RecyclerViewHolder(View itemView) {
        super(itemView);
        mTextViewTitle = itemView.findViewById(R.id.fragment_page_item_title);
        mTextViewDate = itemView.findViewById(R.id.fragment_page_item_date);
        mImageView = itemView.findViewById(R.id.fragment_page_item_image);
        mTextViewSection = itemView.findViewById(R.id.fragment_page_item_section);
        mRelativeLayout = itemView.findViewById(R.id.relativeLayout);
    }

    //Update items
    void updateWithResult (final APIResult article, RequestManager glide,
                           final RecyclerViewAdapter.onPageAdapterListener callback){
        this.mTextViewTitle.setText(article.getTitle());
        // getting right subsection
        if (article.getSubsection() == null){
            this.mTextViewSection.setText(String.format("%s%s%s",
                    article.getSection(),
                    SUBSECTION,
                    "Most Viewed"));
        } else{
            this.mTextViewSection.setText(String.format("%s%s%s",
                    article.getSection(),
                    SUBSECTION,
                    article.getSubsection()));
        }
        //getting update date
        if(article.getUpdatedDate() != null){
            this.mTextViewDate.setText(article.getUpdatedDate().substring(0,10));
        }
        //getting multimedia
        if (article.getMultimedia() != null && article.getMultimedia().size() >= 1){
            glide.load(article.getMultimedia().get(0).getUrl())
                    .apply(RequestOptions.centerInsideTransform())
                    .into(mImageView);
        }
        //getting media
        if (article.getMedia() != null && article.getMedia().size() >= 1){
            glide.load(article.getMedia().get(0).getMediaMetadata().get(0).getUrl())
                    .apply(RequestOptions.centerInsideTransform())
                    .into(mImageView);
        }

        this.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onArticleClicked(article);
            }
        });

    }
}
