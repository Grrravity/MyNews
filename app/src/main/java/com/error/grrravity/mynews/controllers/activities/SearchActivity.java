package com.error.grrravity.mynews.controllers.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.error.grrravity.mynews.R;
import com.error.grrravity.mynews.controllers.fragments.SearchResultFragment;
import com.error.grrravity.mynews.models.APIDoc;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener,
        SearchResultFragment.SearchResultFragmentListener {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.editBeginDateTV) Toolbar mEditBegin;
    @BindView(R.id.editEndDateTV) Toolbar mEditEnd;
    @BindView(R.id.searchField) Toolbar mSearchField;
    @BindView(R.id.cbArts) Toolbar mCBArts;
    @BindView(R.id.cbBusiness) Toolbar mCBBusiness;
    @BindView(R.id.cbFood) Toolbar mCBFood;
    @BindView(R.id.cbPolitics) Toolbar mCBPolitics;
    @BindView(R.id.cbSciences) Toolbar mCBSciences;
    @BindView(R.id.cbSports) Toolbar mCBSports;
    @BindView(R.id.cbTechnology) Toolbar mCBTechnology;
    @BindView(R.id.searchButton) Toolbar mSearchButton;


    public static final String SEARCHED_ARTICLE = "searched_article";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        configureToolbar();
        initListener();
        configureSearch();

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void callbackSearchArticle(APIDoc APISearch) {

    }
}
