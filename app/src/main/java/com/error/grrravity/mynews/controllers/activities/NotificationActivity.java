package com.error.grrravity.mynews.controllers.activities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.error.grrravity.mynews.R;
import com.error.grrravity.mynews.utils.Helper;
import com.error.grrravity.mynews.utils.Preferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.cbArtsNotif)
    CheckBox mCBArts;
    @BindView(R.id.cbBusinessNotif) CheckBox mCBBusiness;
    @BindView(R.id.cbFoodNotif) CheckBox mCBFood;
    @BindView(R.id.cbPoliticsNotif) CheckBox mCBPolitics;
    @BindView(R.id.cbSciencesNotif) CheckBox mCBSciences;
    @BindView(R.id.cbSportsNotif) CheckBox mCBSports;
    @BindView(R.id.cbTechnologyNotif) CheckBox mCBTechnology;
    @BindView(R.id.editSearchNotif)
    EditText mETSearch;
    @BindView(R.id.switchNotif)
    Switch mSwitchNotif;
    @BindView(R.id.edit_notif_time)
    TextView mEditNotifTime;

    private Preferences mPreferences;
    private String mKeywords;
    private String mTime;
    private boolean mSwitchOnOff;
    public static final String NOTIF_PREFS = "notif_pref";
    private List<String> mCategories;
    private String mFormat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        configureToolbar();
        ButterKnife.bind(this);
        initListener();
        //configureSwitch();
        configureETSearch();
        //configureCheckBox();

        //categories = new ArrayList<>();
        mPreferences = Preferences.getInstance(NotificationActivity.this);
    }

    private void configureToolbar() {
        setSupportActionBar(mToolbar);
    }

    private void initListener() {
        mETSearch.setOnClickListener(this);
        mSwitchNotif.setOnClickListener(this);
        mEditNotifTime.setOnClickListener(this);
        mCBArts.setOnClickListener(this);
        mCBBusiness.setOnClickListener(this);
        mCBFood.setOnClickListener(this);
        mCBPolitics.setOnClickListener(this);
        mCBSciences.setOnClickListener(this);
        mCBSports.setOnClickListener(this);
        mCBTechnology.setOnClickListener(this);

    }

    private void configureETSearch() {
        mETSearch.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //keywords = mETSearch.getText().toString();
            }
        });
    }

    @Override
    public void onClick(View view) {

        final Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePicker;

        if (view != mETSearch) {
            hide_keyboard(this);
        }

        switch (view.getId()) {
            //Check if there's a query in search field. If ok, start search
            case R.id.searchButton:
                if (Helper.validateParameters(this, mETSearch, mCBArts, mCBBusiness,
                        mCBFood, mCBPolitics, mCBSciences, mCBSports, mCBTechnology)
                        && Helper.timeIsValid(this, mTime)) {
                    //setNotification();
                }
                break;
                    //adding categories to sharedPref when box are checked
            case R.id.cbArts:
                if (mCBArts.isChecked()) {
                    addToPref("arts", 2);
                } else {
                    removeFromPref("arts", 2);
                }
                break;
                case R.id.cbBusiness:
                    if (mCBBusiness.isChecked()) {
                        addToPref("business", 2);
                    } else {
                        removeFromPref("business", 2);
                    }
                    break;
                case R.id.cbFood:
                    if (mCBFood.isChecked()) {
                        addToPref("food", 2);
                    } else {
                        removeFromPref("food", 2);
                    }
                    break;
                case R.id.cbPolitics:
                    if (mCBPolitics.isChecked()) {
                        addToPref("politics", 2);
                    } else {
                        removeFromPref("politics", 2);
                    }
                    break;
                case R.id.cbSciences:
                    if (mCBSciences.isChecked()) {
                        addToPref("sciences", 2);
                    } else {
                        removeFromPref("sciences", 2);
                    }
                    break;
                case R.id.cbSports:
                    if (mCBSports.isChecked()) {
                        addToPref("sports", 2);
                    } else {
                        removeFromPref("sports", 2);
                    }
                    break;
                case R.id.cbTechnology:
                    if (mCBTechnology.isChecked()) {
                        addToPref("technology", 2);
                    } else {
                        removeFromPref("technology", 2);
                    }
                    break;
                case R.id.edit_notif_time:
                    timePicker = new TimePickerDialog(this, timeSettingListener,
                            hour, minute, true);
                    mEditNotifTime.setText(new StringBuilder().append(hour).append(" : ")
                            .append(minute).append(" ").append(mFormat));
                    break;
            }

        }

    private void hide_keyboard(Activity activity) {
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

    public void addToPref(String string, int source){
        ArrayList<String> category = mPreferences.getPref(source);
        if (!category.contains(string)){
            category.add(string);
            mPreferences.storePref(1,category);
        }
    }

    public void removeFromPref(String string, int source) {
        ArrayList<String> category = mPreferences.getPref(source);
        if (category.contains(string)) {
            category.remove(string);
            mPreferences.storePref(1,category);
        }
    }

    TimePickerDialog.OnTimeSetListener timeSettingListener
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            mTime = Helper.formatTime(hour, minute, mEditNotifTime);
        }
    };

}
