package com.error.grrravity.mynews.controllers.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.error.grrravity.mynews.models.APIResult.TOPSTORIES_EXTRA;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener,
        SearchResultFragment.SearchResultFragmentListener {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.searchField) EditText mSearchField;

    @BindView(R.id.tv_notif_txt) TextView mTVNotif;
    @BindView(R.id.switchButtonNotif) Switch mSwitchNotif;
    @BindView(R.id.tv_notif_time_txt) TextView mTVNotifTime;
    @BindView(R.id.edit_time_notif) TextView mTVEditNotif;

    @BindView(R.id.searchButton) TextView mSearchButton;
    @BindView(R.id.editBeginDateTV) TextView mEditBegin;
    @BindView(R.id.searchBeginDateTV) TextView mTVBegin;
    @BindView(R.id.editEndDateTV) TextView mEditEnd;
    @BindView(R.id.searchEndDateTV) TextView mTVEnd;

    @BindView(R.id.cbArts) CheckBox mCBArts;
    @BindView(R.id.cbBusiness) CheckBox mCBBusiness;
    @BindView(R.id.cbFood) CheckBox mCBFood;
    @BindView(R.id.cbPolitics) CheckBox mCBPolitics;
    @BindView(R.id.cbSciences) CheckBox mCBSciences;
    @BindView(R.id.cbSports) CheckBox mCBSports;
    @BindView(R.id.cbTechnology) CheckBox mCBTechnology;

    private static Preferences mPreferences;
    public static final String SEARCHED_ARTICLE = "searched_article";
    private String mBeginDate = "";
    private String mEndDate = "";
    public String mKeywords;
    public String mQuery;
    public String mTime;
    public Boolean mNotifEnable;
    public Boolean mTargetActivity;
    public List<String> mCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mTargetActivity = Objects.requireNonNull(getIntent().getExtras())
                .getBoolean("boolean");
        configureToolbar();

        mCategories = new ArrayList<>();

        initListener();
        hide_keyboard(this);

        mPreferences = Preferences.getInstance(this);

        configureView();

        configureSearch();

        // Only for notif
        if (!mTargetActivity) {
            configureCheckbox();
            configureTime();
            configureSwitch();
        }


    }

    //To hide keyboard
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

        mTVEditNotif.setOnClickListener(this);

        mSearchButton.setOnClickListener(this);
        mCBArts.setOnClickListener(this);
        mCBBusiness.setOnClickListener(this);
        mCBFood.setOnClickListener(this);
        mCBPolitics.setOnClickListener(this);
        mCBSciences.setOnClickListener(this);
        mCBSports.setOnClickListener(this);
        mCBTechnology.setOnClickListener(this);
    }

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

    private void configureView(){
        if (mTargetActivity){
            mTVNotif.setVisibility(View.GONE);
            mSwitchNotif.setVisibility(View.GONE);
            mTVNotifTime.setVisibility(View.GONE);
            mTVEditNotif.setVisibility(View.GONE);

            mSearchButton.setVisibility(View.VISIBLE);
            mEditBegin.setVisibility(View.VISIBLE);
            mTVBegin.setVisibility(View.VISIBLE);
            mEditEnd.setVisibility(View.VISIBLE);
            mTVEnd.setVisibility(View.VISIBLE);
        } else {
            mTVNotif.setVisibility(View.VISIBLE);
            mSwitchNotif.setVisibility(View.VISIBLE);
            mTVNotifTime.setVisibility(View.VISIBLE);
            mTVEditNotif.setVisibility(View.VISIBLE);

            mSearchButton.setVisibility(View.GONE);
            mEditBegin.setVisibility(View.GONE);
            mTVBegin.setVisibility(View.GONE);
            mEditEnd.setVisibility(View.GONE);
            mTVEnd.setVisibility(View.GONE);
        }
    }

    private void configureSearch() {
        if (!mTargetActivity){
            mNotifEnable = mPreferences.getNotifBoolean();
            if (mNotifEnable) {
                mKeywords = mPreferences.getNotifQuery();
                if (!mKeywords.equals("") || mKeywords.isEmpty()) {
                    mSearchField.setText(mKeywords);
                }
            }
        }
        mSearchField.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mTargetActivity){
                    mQuery = mSearchField.getText().toString();
                } else {
                    mKeywords = mSearchField.getText().toString();
                }
            }
        });

    }

    private void configureCheckbox() {
        mCategories = mPreferences.getNotifCategories();
        if (mCategories != null) {
            if (mCategories.contains("arts")) {
                mCBArts.setChecked(true);
            }
            if (mCategories.contains("business")) {
                mCBBusiness.setChecked(true);
            }
            if (mCategories.contains("food")) {
                mCBFood.setChecked(true);
            }
            if (mCategories.contains("politics")) {
                mCBPolitics.setChecked(true);
            }
            if (mCategories.contains("sciences")) {
                mCBSciences.setChecked(true);
            }
            if (mCategories.contains("sports")) {
                mCBSports.setChecked(true);
            }
            if (mCategories.contains("technology")) {
                mCBTechnology.setChecked(true);
            }
        }
    }

    private void configureTime() {
        mTime = mPreferences.getNotifTime();
        if (mTime != null && !mTime.isEmpty()) {
            mTVEditNotif.setText(mTime);
        } else {
            mTVEditNotif.setText("");
        }
    }

    // sauvegarder si veux notif ou pas. Retirer check si choses enregistrés.
    public void configureSwitch() {
        if (mNotifEnable) {
            mSwitchNotif.setChecked(true);
        } else {
            mSwitchNotif.setChecked(false);
        }
        mSwitchNotif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged (CompoundButton compoundButton,boolean isChecked){

                if(mKeywords != null && mKeywords.isEmpty()) {
                    Toast.makeText(SearchActivity.this,
                            "Please enter something to search",
                            Toast.LENGTH_LONG).show();
                    mSwitchNotif.setChecked(false);
                }
                if (mCategories.size() == 0) {
                    Toast.makeText(SearchActivity.this,
                            "Please select at least one category",
                            Toast.LENGTH_LONG).show();
                    mSwitchNotif.setChecked(false);
                }
                if (mTime.equals("") || mTime.isEmpty()){
                    Toast.makeText(SearchActivity.this,
                            "Please select the time when you want to get notify",
                            Toast.LENGTH_LONG).show();
                    mSwitchNotif.setChecked(false);
                }

                if (mSwitchNotif.isChecked()) {
                    mNotifEnable = true;
                    savePrefs();
                }
                if (!mSwitchNotif.isChecked()) {
                    mNotifEnable = false;
                    savePrefs();
                }
            }
        });
    }

    public void addCategory (String selectedCategories){
        ArrayList<String> category = mPreferences.getCategory(1);
        if (!category.contains(selectedCategories)){
        category.add(selectedCategories);
        mPreferences.storeCategory(1,category);
        }
    }

    public void removeCategory (String selectedCategories) {
        ArrayList<String> category = mPreferences.getCategory(1);
        if (category.contains(selectedCategories)) {
            category.remove(selectedCategories);
            mPreferences.storeCategory(1,category);
        }
    }

    private void savePrefs(){
        mPreferences.storeNotifQuery(mKeywords);
        mPreferences.storeNotifBoolean(mNotifEnable);
        mPreferences.storeNotifCategories(mCategories);
        mPreferences.storeNotifTime(mTime);
    }

    //Make onclick happens when Search and Checkbox are clicked
    @Override
    public void onClick(View view) {

        //set default date on today's date
        final Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);

        DatePickerDialog setDate;
        if(view != mSearchField) {
            hide_keyboard(this);
        }

        switch (view.getId()) {
            //Check if there's a mQuery in search field. If ok, start search
            case R.id.searchButton:
                if (Helper.validateParameters(this, mSearchField, mCBArts, mCBBusiness,
                        mCBFood, mCBPolitics, mCBSciences, mCBSports, mCBTechnology)
                        && Helper.datesAreValid(this, mBeginDate, mEndDate)) {
                    executeSearchRequest();
                }
                break;
            case R.id.edit_time_notif:
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mTime = hourOfDay + ":" + minute;
                                mTVEditNotif.setText(mTime);
                            }
                        }, hour, min, false);
                timePickerDialog.show();
                break;
            //adding categories to sharedPref when box are checked
            case R.id.cbArts:
                if (mCBArts.isChecked()){
                    // Check if it's search or notif
                    if (mTargetActivity) {
                        // case search, store for http request
                        addCategory("arts");
                    } else {
                        // case notification, store for notifications.
                        if (!mCategories.contains("arts")) {
                            mCategories.add("arts");
                        } else {
                            mCategories.remove("arts");
                        }
                    }
                } else {
                    //Removing if unchecked.
                    if (mTargetActivity){
                        removeCategory("arts");
                    } else {
                        mCategories.remove("arts");
                    }
                }
                break;
            case R.id.cbBusiness:
                if(mCBBusiness.isChecked()){
                    if (mTargetActivity) {
                        addCategory("business");
                    } else {
                        if (!mCategories.contains("business")) {
                            mCategories.add("business");
                        }else {
                            mCategories.remove("business");
                        }
                    }
                } else{
                    if (mTargetActivity) {
                        removeCategory("business");
                    } else {
                        mCategories.remove("business");
                    }
                }
                break;
            case R.id.cbFood:
                if(mCBFood.isChecked()){
                    if (mTargetActivity) {
                        addCategory("food");
                    } else {
                        if (!mCategories.contains("food")) {
                            mCategories.add("food");
                        } else {
                            mCategories.remove("food");
                        }
                    }
                } else {
                    if (mTargetActivity) {
                        removeCategory("food");
                    } else {
                        mCategories.remove("food");
                    }
                }
                break;
            case R.id.cbPolitics:
                if(mCBPolitics.isChecked()){
                    if (mTargetActivity) {
                        addCategory("politics");
                    } else {
                        if (!mCategories.contains("politics")) {
                            mCategories.add("politics");
                        } else {
                            mCategories.remove("politics");
                        }
                    }
                } else {
                    if (mTargetActivity) {
                        removeCategory("politics");
                    } else {
                        mCategories.remove("politics");
                    }
                }
                break;
            case R.id.cbSciences:
                if (mCBSciences.isChecked()){
                    if (mTargetActivity) {
                        addCategory("sciences");
                    } else {
                        if (!mCategories.contains("sciences")) {
                            mCategories.add("sciences");
                        } else {
                            mCategories.remove("sciences");
                        }
                    }
                } else {
                    if (mTargetActivity) {
                        removeCategory("sciences");
                    } else {
                        mCategories.remove("sciences");
                    }
                }
                break;
            case R.id.cbSports:
            if(mCBSports.isChecked()){
                    if (mTargetActivity) {
                        addCategory("sports");
                    } else {
                        if (!mCategories.contains("sports")) {
                            mCategories.add("sports");
                        } else {
                            mCategories.remove("sports");
                        }
                    }
                } else {
                    if (mTargetActivity) {
                        removeCategory("sports");
                    } else {
                        mCategories.remove("sports");
                    }
                }
            break;
            case R.id.cbTechnology:
                if(mCBTechnology.isChecked()){
                    if (mTargetActivity) {
                        addCategory("technology");
                    } else {
                        if (!mCategories.contains("technology")) {
                            mCategories.add("technology");
                        } else {
                            mCategories.remove("technology");
                        }
                    }
                } else {
                    if (mTargetActivity) {
                        removeCategory("technology");
                    } else {
                        mCategories.remove("technology");
                    }
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
        ArrayList<String> category = mPreferences.getCategory(1);
        Disposable disposable = NYTStreams.streamFetchSearchArticles(mQuery, category,
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

    @Override
    protected void onPause() {
        super.onPause();
        if (!mTargetActivity) {
            savePrefs();
        }
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
// TODO Remplacer par calendar
}
