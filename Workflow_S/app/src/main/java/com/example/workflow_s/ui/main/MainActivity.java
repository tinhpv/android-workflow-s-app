package com.example.workflow_s.ui.main;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.workflow_s.R;
import com.example.workflow_s.ui.activity.ActivityFragment;
import com.example.workflow_s.ui.home.HomeFragment;
import com.example.workflow_s.ui.organization.OrganizationFragment;
import com.example.workflow_s.ui.template.TemplateFragment;
import com.example.workflow_s.utils.SharedPreferenceUtils;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView nvDrawer;
    private String[] mNavigationDrawerItemTitles;

    // on Navigation Drawer
    private ImageView mUserProfileImageView;
    private TextView mUserDisplayName, mUserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupNavigationDrawer();
        setupDefaultFragment();
    }

    private void setupDefaultFragment() {
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.flContent, homeFragment)
                .commit();
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.Toolbar);
    }

    private void setupNavigationDrawer() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nav_home_view);

        // HEADER VIEW ON NAV_VIEW
        View headerView = nvDrawer.getHeaderView(0);
        mUserProfileImageView = headerView.findViewById(R.id.img_header_menu);
        mUserDisplayName = headerView.findViewById(R.id.tv_header_username);
        mUserEmail = headerView.findViewById(R.id.tv_header_email);



        String profileUrlString = SharedPreferenceUtils.retrieveData(this, getString(R.string.pref_avatar));
        String userDisplayName = SharedPreferenceUtils.retrieveData(this, getString(R.string.pref_username));
        String userEmail = SharedPreferenceUtils.retrieveData(this, getString(R.string.pref_email));

        if (profileUrlString == null) {
            mUserProfileImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_avatar));
        } else {
            Glide.with(this).load(profileUrlString).into(mUserProfileImageView);
        }
        mUserDisplayName.setText(userDisplayName);
        mUserEmail.setText(userEmail);


        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.menu_items_title);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.app_name, R.string.app_name);

        setupDrawerContent(nvDrawer);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
    }


    // TODO - SET FRAGMENT TO MAIN HERE
    private void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_organization_fragment:
                fragmentClass = OrganizationFragment.class;
                break;
            case R.id.nav_home_fragment:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_template_fragment:
                fragmentClass = TemplateFragment.class;
                break;
//            case R.id.nav_checklist_fragment:
//                break;
            case R.id.nav_activity_fragment:
                fragmentClass = ActivityFragment.class;
                break;
//            case R.id.nav_settings_fragment:
//                break;
            default:
                fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.flContent, fragment)
                .commit();

        menuItem.setChecked(true); // Highlight the selected item has been done by NavigationView
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "SEARCH", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_notif:
                Toast.makeText(this, "NOFITICATION", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                mDrawerLayout.openDrawer(GravityCompat.START);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
