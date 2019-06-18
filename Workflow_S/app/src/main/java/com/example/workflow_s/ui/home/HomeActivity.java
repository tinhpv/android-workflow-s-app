package com.example.workflow_s.ui.home;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.workflow_s.utils.Constant;
import com.example.workflow_s.R;
import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.ui.home.adapter.ChecklistProgressAdapter;
import com.example.workflow_s.ui.home.adapter.TodayTaskAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements HomeContract.HomeView, NavigationView.OnNavigationItemSelectedListener {

    private Button btnTemplate, btnChecklist, btnActivity;
    private RecyclerView checklistProgressRecyclerView, todayTaskRecyclerView;
    private RecyclerView.Adapter mChecklistProgressAdapter, mTodayTaskAdapter;
    private RecyclerView.LayoutManager checklistProgressLayoutManager, todayTaskLayoutManager;
    private List<Checklist> mChecklists, mTasks;

    Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private String[] mNavigationDrawerItemTitles;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private HomeContract.HomePresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        makeCollapsingToolbarLookGood();
        makeDashboardButtonLookGood();
        setupRecyclerView();
        setupNavigationDrawer();
        initData();
    }

    private void initData() {
        mPresenter = new HomePresenterImpl(this, new HomeInteractor());
        mPresenter.loadDataFromServer();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_organization:
                Toast.makeText(this, "organization", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_home_item:
                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_template_item:
                Toast.makeText(this, "template", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_checklist:
                Toast.makeText(this, "checklist", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_activity:
                Toast.makeText(this, "activity", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_settings:
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO - HANDLE MENU ITEM SELECTED
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_search:
                Toast.makeText(this, "Search selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_notif:
                Toast.makeText(this, "Notification selected", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setDataToRecyclerView(ArrayList<Checklist> datasource) {
        mChecklistProgressAdapter = new ChecklistProgressAdapter((List<Checklist>) datasource);
        checklistProgressRecyclerView.setAdapter(mChecklistProgressAdapter);

    }


    private void setupNavigationDrawer() {
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.menu_items_title);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        NavigationView navigationView = findViewById(R.id.nav_home_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setupRecyclerView() {

        // checklist recycler view
        checklistProgressRecyclerView = findViewById(R.id.rv_checklist_progress);
        checklistProgressRecyclerView.setHasFixedSize(true);
        checklistProgressLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        checklistProgressRecyclerView.setLayoutManager(checklistProgressLayoutManager);

        // TODO - HAVENT'T SET THE ADAPTER FOR TASK RV
        // checklist today's task recycler view
        todayTaskRecyclerView = findViewById(R.id.rv_today_task);
        todayTaskRecyclerView.setHasFixedSize(true);
        todayTaskLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        todayTaskRecyclerView.setLayoutManager(todayTaskLayoutManager);

        mTasks = new ArrayList<>();
        mTodayTaskAdapter = new TodayTaskAdapter((List<Checklist>) mTasks);
        todayTaskRecyclerView.setAdapter(mTodayTaskAdapter);

    }

    private void makeDashboardButtonLookGood() {
        btnTemplate = findViewById(R.id.btn_template);
        btnChecklist = findViewById(R.id.btn_checklist);
        btnActivity = findViewById(R.id.btn_activity);

        btnTemplate.setBackgroundResource(0);
        btnChecklist.setBackgroundResource(0);
        btnActivity.setBackgroundResource(0);
    }

    private void makeCollapsingToolbarLookGood() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.avenir_black);
        collapsingToolbarLayout.setCollapsedTitleTypeface(typeface);
        collapsingToolbarLayout.setExpandedTitleTypeface(typeface);
    }

}
