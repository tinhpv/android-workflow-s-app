package com.example.workflow_s.ui.home;

import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.ui.home.adapter.ChecklistProgressAdapter;
import com.example.workflow_s.ui.home.adapter.TodayTaskAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Button btnTemplate, btnChecklist, btnActivity;
    private RecyclerView checklistProgressRecyclerView, todayTaskRecyclerView;
    private RecyclerView.Adapter mChecklistProgressAdapter, mTodayTaskAdapter;
    private RecyclerView.LayoutManager checklistProgressLayoutManager, todayTaskLayoutManager;
    private List<Checklist> mChecklists, mTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        makeCollapsingToolbarLookGood();
        makeDashboardButtonLookGood();
        setupRecyclerView();
    }

    private void setupRecyclerView() {


        // checklist recycler view
        checklistProgressRecyclerView = findViewById(R.id.rv_checklist_progress);
//        checklistProgressRecyclerView.setHasFixedSize(true);
        checklistProgressLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        checklistProgressRecyclerView.setLayoutManager(checklistProgressLayoutManager);
        mChecklists = new ArrayList<>();
        mChecklistProgressAdapter = new ChecklistProgressAdapter(mChecklists);
        checklistProgressRecyclerView.setAdapter(mChecklistProgressAdapter);

        // checklist today's task recycler view
        todayTaskRecyclerView = findViewById(R.id.rv_today_task);
        todayTaskRecyclerView.setHasFixedSize(true);
        todayTaskLayoutManager = new LinearLayoutManager(this);
        todayTaskRecyclerView.setLayoutManager(todayTaskLayoutManager);
        mTasks = new ArrayList<>();
        mTodayTaskAdapter = new TodayTaskAdapter(mTasks);
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.menu_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Side menu selected", Toast.LENGTH_SHORT).show();
            }
        });
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.avenir_black);
        collapsingToolbarLayout.setCollapsedTitleTypeface(typeface);
        collapsingToolbarLayout.setExpandedTitleTypeface(typeface);
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
}
