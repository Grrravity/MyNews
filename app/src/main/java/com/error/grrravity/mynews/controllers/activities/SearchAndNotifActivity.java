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
import com.error.grrravity.mynews.utils.AlarmHelper;
import com.error.grrravity.mynews.utils.DateHelper;
import com.error.grrravity.mynews.utils.ErrorListener;
import com.error.grrravity.mynews.utils.SearchAndNotifHelper;
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

public class SearchAndNotifActivity extends AppCompatActivity implements View.OnClickListener,
        SearchResultFragment.SearchResultFragmentListener,
        ErrorListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    // Notif views
    @BindView(R.id.tv_notif_txt)
    TextView mTVNotif;
    @BindView(R.id.switchButtonNotif)
    Switch mSwitchNotif;
    @BindView(R.id.tv_notif_time_txt)
    TextView mTVNotifTime;
    @BindView(R.id.edit_time_notif)
    TextView mTVEditNotifTime;

    // Search views
    @BindView(R.id.searchButton)
    TextView mSearchButton;
    @BindView(R.id.editBeginDateTV)
    TextView mEditBegin;
    @BindView(R.id.searchBeginDateTV)
    TextView mTVBegin;
    @BindView(R.id.editEndDateTV)
    TextView mEditEnd;
    @BindView(R.id.searchEndDateTV)
    TextView mTVEnd;

    // Both view
    @BindView(R.id.searchField)
    EditText mSearchField;
    @BindView(R.id.cbArts)
    CheckBox mCBArts;
    @BindView(R.id.cbBusiness)
    CheckBox mCBBusiness;
    @BindView(R.id.cbFood)
    CheckBox mCBFood;
    @BindView(R.id.cbPolitics)
    CheckBox mCBPolitics;
    @BindView(R.id.cbSciences)
    CheckBox mCBSciences;
    @BindView(R.id.cbSports)
    CheckBox mCBSports;
    @BindView(R.id.cbTechnology)
    CheckBox mCBTechnology;

    private static Preferences mPreferences;
    public static final String SEARCHED_ARTICLE = "searched_article";

    // Search and notif criteria
    private String mBeginDate = "";
    private String mEndDate = "";
    public String mSearchQuery;
    public List<String> mCategories;
    public String mNotifQuery;
    public String mTime;

    public Boolean mNotifEnable;
    public Boolean mTargetActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_and_notif);
        ButterKnife.bind(this);

        // To know if its search or notification
        mTargetActivity = Objects.requireNonNull(getIntent().getExtras())
                .getBoolean(getString(R.string.bool_intent));

        configureToolbar();

        mCategories = new ArrayList<>();

        initListener();
        hide_keyboard(this);

        mPreferences = Preferences.getInstance(this);

        configureView();

        //For both search and notif
        configureSearchField();


        // Only for notif
        if (!mTargetActivity) {
            configureCheckbox();
            configureTime();
            configureSwitch();
        }
    }

    private void initListener() {

        // Search listener
        mSearchButton.setOnClickListener(this);
        mEditBegin.setOnClickListener(this);
        mEditEnd.setOnClickListener(this);

        // Notif listener
        mTVEditNotifTime.setOnClickListener(this);

        // Both listener
        mSearchField.setOnClickListener(this);
        mCBArts.setOnClickListener(this);
        mCBBusiness.setOnClickListener(this);
        mCBFood.setOnClickListener(this);
        mCBPolitics.setOnClickListener(this);
        mCBSciences.setOnClickListener(this);
        mCBSports.setOnClickListener(this);
        mCBTechnology.setOnClickListener(this);
    }

    //To hide keyboard
    public static void hide_keyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(view.getWindowToken(),
                0);
    }

    //
    // CONFIGURATION
    //
    // GENERAL

    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
    }

    // display views according to asked activity
    private void configureView() {
        // for search
        if (mTargetActivity) {
            mTVNotif.setVisibility(View.GONE);
            mSwitchNotif.setVisibility(View.GONE);
            mTVNotifTime.setVisibility(View.GONE);
            mTVEditNotifTime.setVisibility(View.GONE);

            mSearchButton.setVisibility(View.VISIBLE);
            mEditBegin.setVisibility(View.VISIBLE);
            mTVBegin.setVisibility(View.VISIBLE);
            mEditEnd.setVisibility(View.VISIBLE);
            mTVEnd.setVisibility(View.VISIBLE);
        }
        //for notification
        else {
            mTVNotif.setVisibility(View.VISIBLE);
            mSwitchNotif.setVisibility(View.VISIBLE);
            mTVNotifTime.setVisibility(View.VISIBLE);
            mTVEditNotifTime.setVisibility(View.VISIBLE);

            mSearchButton.setVisibility(View.GONE);
            mEditBegin.setVisibility(View.GONE);
            mTVBegin.setVisibility(View.GONE);
            mEditEnd.setVisibility(View.GONE);
            mTVEnd.setVisibility(View.GONE);
        }
    }

    private void configureSearchField() {
        // Set text for notification
        if (!mTargetActivity) {
            mNotifEnable = mPreferences.getNotifBoolean();
            mNotifQuery = mPreferences.getNotifQuery();
            if (!mNotifQuery.equals("") || mNotifQuery.isEmpty()) {
                mSearchField.setText(mNotifQuery);
            }
        }
        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mTargetActivity) {
                    mSearchQuery = mSearchField.getText().toString();
                } else {
                    mNotifQuery = mSearchField.getText().toString();
                }
            }
        });

    }

    private void configureCheckbox() {
        mCategories = mPreferences.getNotifCategories();
        if (mCategories != null) {
            if (mCategories.contains(getString(R.string.arts_lower))) {
                mCBArts.setChecked(true);
            }
            if (mCategories.contains(getString(R.string.business_lower))) {
                mCBBusiness.setChecked(true);
            }
            if (mCategories.contains(getString(R.string.food_lower))) {
                mCBFood.setChecked(true);
            }
            if (mCategories.contains(getString(R.string.politics_lower))) {
                mCBPolitics.setChecked(true);
            }
            if (mCategories.contains(getString(R.string.sciences_lower))) {
                mCBSciences.setChecked(true);
            }
            if (mCategories.contains(getString(R.string.sports_lower))) {
                mCBSports.setChecked(true);
            }
            if (mCategories.contains(getString(R.string.technology_lower))) {
                mCBTechnology.setChecked(true);
            }
        }
    }

    //FOR NOTIFICATION

    // Get notification times from preferences and set text if a time was stored
    private void configureTime() {
        mTime = mPreferences.getNotifTime();
        if (mTime != null && !mTime.isEmpty()) {
            mTVEditNotifTime.setText(mTime);
        } else {
            mTVEditNotifTime.setText("");
        }
    }

    // Save if notifications is enable
    // set a listener on the switch and verify if all criteria are filled
    public void configureSwitch() {
        if (mNotifEnable) {
            mSwitchNotif.setChecked(true);
        } else {
            mSwitchNotif.setChecked(false);
        }
        mSwitchNotif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mSwitchNotif.setChecked(validationNotif());
                saveNotifPrefs();
            }
        });
    }

    //To use error listener within onCheckedChangedListener
    public boolean validationNotif(){
        return SearchAndNotifHelper.validateNotifParam (mNotifQuery, mCategories, mTime,
                mSwitchNotif.isChecked(),this);
    }

    //
    // PREFERENCES PURPOSE
    //
    // FOR SEARCH

    // Add categories to prefs if not already in
    public void addCategory(String selectedCategories) {
        ArrayList<String> category = mPreferences.getCategory(1);
        if (!category.contains(selectedCategories)) {
            category.add(selectedCategories);
            mPreferences.storeCategory(1, category);
        }
    }

    // remove categories from prefs
    public void removeCategory(String selectedCategories) {
        ArrayList<String> category = mPreferences.getCategory(1);
        if (category.contains(selectedCategories)) {
            category.remove(selectedCategories);
            mPreferences.storeCategory(1, category);
        }
    }

    //FOR NOTIFICATION

    // Save to preferences
    // Also configure the alarm.
    private void saveNotifPrefs() {
        mPreferences.storeNotifQuery(mNotifQuery);
        mNotifEnable = mSwitchNotif.isChecked();
        mPreferences.storeNotifBoolean(mNotifEnable);
        mPreferences.storeNotifCategories(mCategories);
        mPreferences.storeNotifTime(mTime);
        (new AlarmHelper()).configureAlarmNotif(SearchAndNotifActivity.this,
                mPreferences.getNotifBoolean(),
                DateHelper.setTimeNotif(mPreferences.getNotifTime()));
    }


    //
    // OnClick
    //

    // Make onclick happens when Query and Checkbox are filled
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
        if (view != mSearchField) {
            hide_keyboard(this);
        }

        switch (view.getId()) {
            //Check if there's a query in search field. If ok, start search
            case R.id.searchButton:
                if (SearchAndNotifHelper.validateSearchParam( mSearchField.getText().toString(), mCBArts.isChecked(),
                        mCBBusiness.isChecked(), mCBFood.isChecked(), mCBPolitics.isChecked(),
                        mCBSciences.isChecked(), mCBSports.isChecked(), mCBTechnology.isChecked(),
                        this)
                        && DateHelper.datesAreValid(this, mBeginDate, mEndDate)) {
                    executeSearchRequest();
                } else {
                    if (SearchAndNotifHelper.getFocus()){
                        mSearchField.requestFocus();
                    }
                }
                break;
            // Time picker for notification
            case R.id.edit_time_notif:
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mTime = hourOfDay + ":" + minute;
                                mTVEditNotifTime.setText(mTime);
                            }
                        }, hour, min, false);
                timePickerDialog.show();
                break;
            //adding categories to sharedPref when box are checked
            case R.id.cbArts:
                if (mCBArts.isChecked()) {
                    // Check if it's search or notif
                    if (mTargetActivity) {
                        // case search, store for http request
                        addCategory(getString(R.string.arts_lower));
                    } else {
                        // case notification, store for notifications.
                        if (!mCategories.contains(getString(R.string.arts_lower))) {
                            mCategories.add(getString(R.string.arts_lower));
                        } else {
                            mCategories.remove(getString(R.string.arts_lower));
                        }
                    }
                } else {
                    //Removing if unchecked.
                    if (mTargetActivity) {
                        removeCategory(getString(R.string.arts_lower));
                    } else {
                        mCategories.remove(getString(R.string.arts_lower));
                    }
                }
                break;
            case R.id.cbBusiness:
                if (mCBBusiness.isChecked()) {
                    if (mTargetActivity) {
                        addCategory(getString(R.string.business_lower));
                    } else {
                        if (!mCategories.contains(getString(R.string.business_lower))) {
                            mCategories.add(getString(R.string.business_lower));
                        } else {
                            mCategories.remove(getString(R.string.business_lower));
                        }
                    }
                } else {
                    if (mTargetActivity) {
                        removeCategory(getString(R.string.business_lower));
                    } else {
                        mCategories.remove(getString(R.string.business_lower));
                    }
                }
                break;
            case R.id.cbFood:
                if (mCBFood.isChecked()) {
                    if (mTargetActivity) {
                        addCategory(getString(R.string.food_lower));
                    } else {
                        if (!mCategories.contains(getString(R.string.food_lower))) {
                            mCategories.add(getString(R.string.food_lower));
                        } else {
                            mCategories.remove(getString(R.string.food_lower));
                        }
                    }
                } else {
                    if (mTargetActivity) {
                        removeCategory(getString(R.string.food_lower));
                    } else {
                        mCategories.remove(getString(R.string.food_lower));
                    }
                }
                break;
            case R.id.cbPolitics:
                if (mCBPolitics.isChecked()) {
                    if (mTargetActivity) {
                        addCategory(getString(R.string.politics_lower));
                    } else {
                        if (!mCategories.contains(getString(R.string.politics_lower))) {
                            mCategories.add(getString(R.string.politics_lower));
                        } else {
                            mCategories.remove(getString(R.string.politics_lower));
                        }
                    }
                } else {
                    if (mTargetActivity) {
                        removeCategory(getString(R.string.politics_lower));
                    } else {
                        mCategories.remove(getString(R.string.politics_lower));
                    }
                }
                break;
            case R.id.cbSciences:
                if (mCBSciences.isChecked()) {
                    if (mTargetActivity) {
                        addCategory(getString(R.string.sciences_lower));
                    } else {
                        if (!mCategories.contains(getString(R.string.sciences_lower))) {
                            mCategories.add(getString(R.string.sciences_lower));
                        } else {
                            mCategories.remove(getString(R.string.sciences_lower));
                        }
                    }
                } else {
                    if (mTargetActivity) {
                        removeCategory(getString(R.string.sciences_lower));
                    } else {
                        mCategories.remove(getString(R.string.sciences_lower));
                    }
                }
                break;
            case R.id.cbSports:
                if (mCBSports.isChecked()) {
                    if (mTargetActivity) {
                        addCategory(getString(R.string.sports_lower));
                    } else {
                        if (!mCategories.contains(getString(R.string.sports_lower))) {
                            mCategories.add(getString(R.string.sports_lower));
                        } else {
                            mCategories.remove(getString(R.string.sports_lower));
                        }
                    }
                } else {
                    if (mTargetActivity) {
                        removeCategory(getString(R.string.sports_lower));
                    } else {
                        mCategories.remove(getString(R.string.sports_lower));
                    }
                }
                break;
            case R.id.cbTechnology:
                if (mCBTechnology.isChecked()) {
                    if (mTargetActivity) {
                        addCategory(getString(R.string.technology_lower));
                    } else {
                        if (!mCategories.contains(getString(R.string.technology_lower))) {
                            mCategories.add(getString(R.string.technology_lower));
                        } else {
                            mCategories.remove(getString(R.string.technology_lower));
                        }
                    }
                } else {
                    if (mTargetActivity) {
                        removeCategory(getString(R.string.technology_lower));
                    } else {
                        mCategories.remove(getString(R.string.technology_lower));
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

    // SEARCH HTTP PURPOSE

    private void executeSearchRequest() {
        ArrayList<String> category = mPreferences.getCategory(1);
        Disposable disposable = NYTStreams.streamFetchSearchArticles(mSearchQuery, category,
                mBeginDate, mEndDate)
                .subscribeWith(new DisposableObserver<APISearch>() {
                    @Override
                    public void onNext(APISearch articles) {
                        setSearchFragment(articles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(getClass().getSimpleName(), getString(R.string.onErrorSearch)
                                + getString(R.string.my_log));
                    }

                    @Override
                    public void onComplete() {
                        Log.i(getClass().getSimpleName(), getString(R.string.onCompleteSearch)
                                + getString(R.string.my_log));
                    }
                });
    }

    private void setSearchFragment(APISearch articles) {
        if (articles.getResponse().getDocs().isEmpty()) {
            Toast.makeText(this, R.string.list_empty,
                    Toast.LENGTH_SHORT).show();
        } else {
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

    // OTHER

    @Override
    public void callbackSearchArticle(APIDoc APISearch) {
        startArticleActivity(APISearch);
    }

    private void startArticleActivity(APIDoc APISearch) {
        Intent articleActivityIntent = new Intent(SearchAndNotifActivity.this,
                ArticleActivity.class);
        articleActivityIntent.putExtra(TOPSTORIES_EXTRA, APISearch.getWebUrl());
        startActivity(articleActivityIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!mTargetActivity) {
            saveNotifPrefs();
        }
    }

    // DatePicker Listeners

    DatePickerDialog.OnDateSetListener dateSettingListenerBegin
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            mBeginDate = DateHelper.pickerFormatDate(year, month, day, mEditBegin);
        }
    };
    DatePickerDialog.OnDateSetListener dateSettingListenerEnd
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            mEndDate = DateHelper.pickerFormatDate(year, month, day, mEditEnd);
        }
    };

    @Override
    public void onShowErrorString(String error) {
        Toast.makeText(this,
                error,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowErrorFromResources(int error) {
        Toast.makeText(this,
                getString(error),
                Toast.LENGTH_SHORT).show();
    }
}
