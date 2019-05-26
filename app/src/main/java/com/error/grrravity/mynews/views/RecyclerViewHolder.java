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

import butterknife.BindView;
import butterknife.ButterKnife;

class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private static final String SUBSECTION = " > ";

    // IDs
    @BindView(R.id.fragment_page_item_title)
    TextView mTextViewTitle;
    @BindView(R.id.fragment_page_item_date)
    TextView mTextViewDate;
    @BindView(R.id.fragment_page_item_image)
    ImageView mImageView;
    @BindView(R.id.fragment_page_item_section)
    TextView mTextViewSection;
    @BindView(R.id.relativeLayout)
    RelativeLayout mRelativeLayout;

    RecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    //Update items
    void updateWithResult(final APIResult article, RequestManager glide,
                          final RecyclerViewAdapter.onPageAdapterListener callback) {
        this.mTextViewTitle.setText(article.getTitle());
        // getting right subsection
        if (article.getSubsection() == null) {
            this.mTextViewSection.setText(String.format("%s%s%s",
                    article.getSection(),
                    SUBSECTION,
                    "Most Viewed"));
        } else {
            this.mTextViewSection.setText(String.format("%s%s%s",
                    article.getSection(),
                    SUBSECTION,
                    article.getSubsection()));
        }
        //getting update date
        if (article.getPublishedDate() != null) {
            this.mTextViewDate.setText(article.getPublishedDate().substring(0, 10));
        }
        //getting multimedia
        if (article.getMultimedia() != null && article.getMultimedia().size() >= 1) {
            glide.load(article.getMultimedia().get(0).getUrl())
                    .apply(RequestOptions.centerInsideTransform())
                    .into(mImageView);
        }
        //getting media
        if (article.getMedia() != null && article.getMedia().size() >= 1) {
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
