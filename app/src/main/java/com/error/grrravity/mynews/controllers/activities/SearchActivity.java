package com.error.grrravity.mynews.controllers.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.error.grrravity.mynews.R;
import com.error.grrravity.mynews.controllers.fragments.SearchResultFragment;
import com.error.grrravity.mynews.models.APIDoc;
import com.error.grrravity.mynews.models.APISearch;
import com.error.grrravity.mynews.utils.Helper;
import com.error.grrravity.mynews.utils.NYTStreams;
import com.error.grrravity.mynews.utils.Preferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.error.grrravity.mynews.models.APIResult.TOPSTORIES_EXTRA;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener,
        SearchResultFragment.SearchResultFragmentListener {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.editBeginDateTV) TextView mEditBegin;
    @BindView(R.id.editEndDateTV) TextView mEditEnd;
    @BindView(R.id.searchField) EditText mSearchField;
    @BindView(R.id.searchButton) TextView mSearchButton;
    @BindView(R.id.cbArts) CheckBox mCBArts;
    @BindView(R.id.cbBusiness) CheckBox mCBBusiness;
    @BindView(R.id.cbFood) CheckBox mCBFood;
    @BindView(R.id.cbPolitics) CheckBox mCBPolitics;
    @BindView(R.id.cbSciences) CheckBox mCBSciences;
    @BindView(R.id.cbSports) CheckBox mCBSports;
    @BindView(R.id.cbTechnology) CheckBox mCBTechnology;

    private static Preferences mPreferences;
    public static final String SEARCHED_ARTICLE = "searched_article";
    public String query;
    private String mBeginDate = "";
    private String mEndDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        configureToolbar();
        initListener();
        hide_keyboard(this);
        configureSearch();

        mPreferences = Preferences.getInstance(this);

    }

    //To hide keyboard
    public static void hide_keyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if(view == null) {
            view = new View(activity);
        }
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(view.getWindowToken(),
                0);
    }

    private void configureSearch() {
        mSearchField.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                query = mSearchField.getText().toString();
            }
        });

    }

    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void initListener() {
        mSearchField.setOnClickListener(this);
        mEditBegin.setOnClickListener(this);
        mEditEnd.setOnClickListener(this);
        mSearchButton.setOnClickListener(this);
        mCBArts.setOnClickListener(this);
        mCBBusiness.setOnClickListener(this);
        mCBFood.setOnClickListener(this);
        mCBPolitics.setOnClickListener(this);
        mCBSciences.setOnClickListener(this);
        mCBSports.setOnClickListener(this);
        mCBTechnology.setOnClickListener(this);

    }

    public void addCategory (String selectedCategories){
        ArrayList<String> category = mPreferences.getPref(1);
        if (!category.contains(selectedCategories)){
        category.add(selectedCategories);
        mPreferences.storePref(1,category);
        }
    }

    public void removeCategory (String selectedCategories) {
        ArrayList<String> category = mPreferences.getPref(1);
        if (category.contains(selectedCategories)) {
            category.remove(selectedCategories);
            mPreferences.storePref(1,category);
        }
    }

    //Make onclick happens when Search and Checkbox are clicked
    @Override
    public void onClick(View view) {

        //set default date on today's date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog setDate;
        if(view != mSearchField) {
            hide_keyboard(this);
        }

        switch (view.getId()) {
            //Check if there's a query in search field. If ok, start search
            case R.id.searchButton:
                if (Helper.validateParameters(this, mSearchField, mCBArts, mCBBusiness,
                        mCBFood, mCBPolitics, mCBSciences, mCBSports, mCBTechnology)
                        && Helper.datesAreValid(this, mBeginDate, mEndDate)) {
                    executeSearchRequest();
                }
                break;
            //adding categories to sharedPref when box are checked
            case R.id.cbArts:
                if (mCBArts.isChecked()){
                    addCategory("arts");
                }
                else {
                    removeCategory("arts");
                }
                break;
            case R.id.cbBusiness:
                if(mCBBusiness.isChecked()){
                  addCategory("business");
                }
                else{
                    removeCategory("business");
                }
                break;
            case R.id.cbFood:
                if(mCBFood.isChecked()){
                    addCategory("food");
                }
                else{
                    removeCategory("food");
                }
                break;
            case R.id.cbPolitics:
                if(mCBPolitics.isChecked()){
                    addCategory("politics");
                }
                else{
                    removeCategory("politics");
                }
                break;
            case R.id.cbSciences:
                if(mCBSciences.isChecked()){
                    addCategory("sciences");
                }
                else{
                    removeCategory("sciences");
                }
                break;
            case R.id.cbSports:
                if(mCBSports.isChecked()){
                   addCategory("sports");
                }
                else{
                  removeCategory("sports");
                }
                break;
                case R.id.cbTechnology:
                if(mCBTechnology.isChecked()){
                    addCategory("technology");
                }
                else{
                    removeCategory("technology");
                }
                break;
            case R.id.editBeginDateTV:
                setDate = new DatePickerDialog(this, dateSettingListenerBegin,
                        year, month, day);
                setDate.show();
                break;
            case R.id.editEndDateTV:
                setDate = new DatePickerDialog(this, dateSettingListenerEnd,
                        year, month, day);
                setDate.show();
                break;
        }
    }

    private void executeSearchRequest() {
        ArrayList<String> category = mPreferences.getPref(1);
        Disposable disposable = NYTStreams.streamFetchSearchArticles(query, category,
                mBeginDate, mEndDate)
                .subscribeWith(new DisposableObserver<APISearch>(){
            @Override
            public void onNext(APISearch articles) {
                setSearchFragment(articles);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("test", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("Test", "Search is charged");
            }
        });
    }

    private void setSearchFragment (APISearch articles){
        if (articles.getResponse().getDocs().isEmpty()){
            Toast.makeText(this, R.string.list_empty,Toast.LENGTH_LONG).show();
        }else {
            Bundle bundle = new Bundle();
            bundle.putParcelable(SEARCHED_ARTICLE, articles);
            SearchResultFragment searchResultFragment = (SearchResultFragment)
                    getSupportFragmentManager().findFragmentById(R.id.activity_search_FrameLayout);
            if (searchResultFragment == null) {
                searchResultFragment = new SearchResultFragment();
                searchResultFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.activity_search_FrameLayout,
                        searchResultFragment)
                        .commit();
            }
        }
    }

    @Override
    public void callbackSearchArticle(APIDoc APISearch) {
        startArticleActivity(APISearch);
    }

    private void startArticleActivity(APIDoc APISearch){
        Intent articleActivityIntent = new Intent(SearchActivity.this,
                ArticleActivity.class);
        articleActivityIntent.putExtra(TOPSTORIES_EXTRA, APISearch.getWebUrl());
        startActivity(articleActivityIntent);
    }

    //SETTING DATES

    DatePickerDialog.OnDateSetListener dateSettingListenerBegin
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day){
            mBeginDate = Helper.pickerFormatDate(year, month, day, mEditBegin);
        }
    };
    DatePickerDialog.OnDateSetListener dateSettingListenerEnd
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            mEndDate = Helper.pickerFormatDate(year, month, day, mEditEnd);
        }
    };

}
