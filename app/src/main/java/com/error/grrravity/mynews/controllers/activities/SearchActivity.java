package com.error.grrravity.mynews.controllers.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.error.grrravity.mynews.R;
import com.error.grrravity.mynews.controllers.fragments.SearchResultFragment;
import com.error.grrravity.mynews.models.APIDoc;

import java.util.List;

import butterknife.BindView;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener,
        SearchResultFragment.SearchResultFragmentListener {

    public static final String SEARCHED_ARTICLE = "searched_article";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void callbackSearchArticle(APIDoc APISearch) {

    }
}
