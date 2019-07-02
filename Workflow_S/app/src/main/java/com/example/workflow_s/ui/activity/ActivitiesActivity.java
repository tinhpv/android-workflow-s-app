package com.example.workflow_s.ui.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.workflow_s.R;
import com.example.workflow_s.ui.activity.adapters.TodayActivitiesAdapter;
import com.example.workflow_s.ui.activity.adapters.UpcomingActivitiesAdapter;
import com.example.workflow_s.ui.activity.adapters.ViewPagerAdapter;
import com.example.workflow_s.ui.activity.fragments.TodayActivitiesFragment;
import com.example.workflow_s.ui.activity.fragments.UpcomingActivitiesFragment;

public class ActivitiesActivity extends AppCompatActivity {

    private final static int NUMBER_OF_TABS = 2;
    private final static String TAG = "ACTIVITIES";

    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabItem mTodayTabItem, mUpcomingTabItem;
    private ViewPagerAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        init();
    }

    private void init() {
        mTabLayout = findViewById(R.id.tab_layout);
        mToolbar = findViewById(R.id.activity_toolbar);
        mToolbar.setTitle(getString(R.string.activity_name));
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextAppearance(this, R.style.Toolbar);

        // TAB ITEM
        mTodayTabItem = findViewById(R.id.tab_today);
        mUpcomingTabItem = findViewById(R.id.tab_upcoming);

        mViewPager = findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), NUMBER_OF_TABS);

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void setUpTodayActivitiesRV() {

    }

}
