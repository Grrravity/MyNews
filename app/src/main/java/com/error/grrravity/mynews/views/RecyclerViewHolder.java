package com.error.grrravity.mynews.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.error.grrravity.mynews.R;
import com.error.grrravity.mynews.models.APIResult;

import butterknife.BindView;
import butterknife.ButterKnife;

class RecyclerViewHolder extends RecyclerView.ViewHolder {

    // IDs
    @BindView(R.id.fragment_page_item_title)
    TextView textViewTitle;
    @BindView(R.id.fragment_page_item_date) TextView textViewDate;
    @BindView(R.id.fragment_page_item_image)
    ImageView imageView;
    @BindView(R.id.fragment_page_item_section) TextView textViewSection;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    //

    RecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    //Update items
    void updateWithResult (final APIResult article, RequestManager glide,
                           final RecyclerViewAdapter.onPageAdapterListener callback){
        this.textViewTitle.setText(article.getTitle());

    }
}
