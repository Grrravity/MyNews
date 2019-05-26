package com.error.grrravity.mynews.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.error.grrravity.mynews.R;
import com.error.grrravity.mynews.controllers.fragments.PageFragment;
import com.error.grrravity.mynews.models.APIResult;
import com.error.grrravity.mynews.utils.Preferences;
import com.error.grrravity.mynews.views.PagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;

import static com.error.grrravity.mynews.models.APIResult.TOPSTORIES_EXTRA;

public class MainActivity extends AppCompatActivity implements PageFragment.PageFragmentListener,
        NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.activity_main_viewpager)
    public ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_main_tabs)
    TabLayout mTabLayout;

    private DrawerLayout mDrawerLayout;
    private List<PageFragment> mPageFragment;
    private PagerAdapter mPagerAdapter;

    private static Preferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        this.configureToolbar();
        mPageFragment = new ArrayList<>();
        mPageFragment.add(PageFragment.newInstance(0));
        mPageFragment.add(PageFragment.newInstance(1));
        mPageFragment.add(PageFragment.newInstance(2));

        mPreferences = Preferences.getInstance(this);

        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureViewPagerAndTabs();
        this.configureAndShowPageFragment();

    }

    // Inflate the option menu and add to the Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    // Switching on different option menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Starting corresponding activity once clicked on items
        switch (item.getItemId()) {
            case R.id.menu_activity_main_params:
                return true;
            case R.id.menu_activity_main_params_Notification:
                Intent notificationIntent = new Intent(MainActivity.this,
                        SearchAndNotifActivity.class);
                notificationIntent.putExtra("boolean", false);
                startActivity(notificationIntent);
                return true;
            case R.id.menu_activity_main_params_help:
                Intent helpIntent = new Intent(MainActivity.this,
                        HelpActivity.class);
                startActivity(helpIntent);
                return true;
            case R.id.menu_activity_main_params_about:
                Intent aboutIntent = new Intent(MainActivity.this,
                        AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            case R.id.menu_activity_main_search:
                Intent searchActivityIntent = new Intent(MainActivity.this,
                        SearchAndNotifActivity.class);
                searchActivityIntent.putExtra("boolean", true);
                startActivity(searchActivityIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Manage drawerLayout when back button is used
    @Override
    public void onBackPressed() {
        // Close menu when back clicked
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Menu Drawer Item selection
    @Override
    public boolean onNavigationItemSelected
    (@android.support.annotation.NonNull @NonNull MenuItem item) {
        // 4 - Handle Navigation Item Click from drawer
        int id = item.getItemId();
        item.getTitle();
        String selectedSection;

        switch (id) {
            case R.id.menu_drawer_arts_section:
                selectedSection = "arts";
                updateSelectedSection(selectedSection);
                break;
            case R.id.menu_drawer_business_section:
                selectedSection = "business";
                updateSelectedSection(selectedSection);
                break;
            case R.id.menu_drawer_food_section:
                selectedSection = "food";
                updateSelectedSection(selectedSection);
                break;
            case R.id.menu_drawer_science_section:
                selectedSection = "science";
                updateSelectedSection(selectedSection);
                break;
            case R.id.menu_drawer_sports_section:
                selectedSection = "sports";
                updateSelectedSection(selectedSection);
                break;
            case R.id.menu_drawer_politics_section:
                selectedSection = "politics";
                updateSelectedSection(selectedSection);
                break;
            case R.id.menu_drawer_technology_section:
                selectedSection = "technology";
                updateSelectedSection(selectedSection);
                break;
            default:
                break;
        }

        // close the drawer once an item is selected
        this.mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // Update Selected article section in position 2 with the good section and save section in
    // Preferences
    private void updateSelectedSection(String selectedSection) {
        mViewPager.setCurrentItem(2);
        Objects.requireNonNull(mTabLayout.getTabAt(2)).setText(selectedSection);
        ((PageFragment) mPagerAdapter.getItem(2)).updateContent(selectedSection);

        //Save selected section in sharedPreference
        ArrayList<String> category = mPreferences.getCategory(0);
        if (category.size() > 0) {
            category.remove(0);
        }
        category.add(selectedSection);
        mPreferences.storeCategory(0, category);
    }

    //
    // CONFIGURATION
    //

    // setting toolbar
    private void configureToolbar() {
        setSupportActionBar(mToolbar);
    }

    // setting ViewPager + tabs
    private void configureViewPagerAndTabs() {
        ButterKnife.bind(this);
        //Set the Adapter for PagerAdapter and link it together
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mPageFragment, this);
        mViewPager.setAdapter(mPagerAdapter);
        // Link TabLayout and ViewPager together
        mTabLayout.setupWithViewPager(mViewPager);
        // Sets same width for each tabs
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    // setting NavigationView for drawer
    private void configureNavigationView() {
        NavigationView navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // setting drawerLayout
    private void configureDrawerLayout() {
        this.mDrawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Setting PageFragment
    private void configureAndShowPageFragment() {
        PageFragment pageFragment = (PageFragment) getSupportFragmentManager().
                findFragmentById(R.id.activity_main_frame_layout);
        if (pageFragment == null) {
            pageFragment = new PageFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_frame_layout, pageFragment)
                    .commit();
        }
    }

    // PageFragment callback
    @Override
    public void callbackArticle(APIResult article) {
        startDetailActivity(article);
    }

    private void startDetailActivity(APIResult article) {
        Intent detailActivityIntent = new Intent(MainActivity.this,
                ArticleActivity.class);
        detailActivityIntent.putExtra(TOPSTORIES_EXTRA, article.getUrl());
        startActivity(detailActivityIntent);
    }
}