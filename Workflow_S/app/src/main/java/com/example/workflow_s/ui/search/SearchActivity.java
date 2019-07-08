package com.example.workflow_s.ui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.example.workflow_s.R;
import com.example.workflow_s.ui.search.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView searchRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SearchAdapter adapter;
    private List<String> dataSearch;
    private List<Integer> listIdSearch;

    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //setupToolbar();
        getData();
        //initData();
        setupSearchDataRV();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        dataSearch = new ArrayList<>();
        dataSearch = bundle.getStringArrayList("listSearch");
        int type = bundle.getInt("Type");
        switch (type) {
            case 1:
                //ArrayList<Checklist> list = new ArrayList<>();
                listIdSearch = bundle.getIntegerArrayList("listId");
                break;
                default:
                    break;
        }

    }

    private void initData() {
        dataSearch = new ArrayList<>();
        dataSearch.add("Tien");
        dataSearch.add("Tinh");
        dataSearch.add("Phat");
        dataSearch.add("Phu");
    }

    private void setupSearchDataRV() {
        searchRecyclerView = findViewById(R.id.rv_search_list);
        searchRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new SearchAdapter(dataSearch, listIdSearch);

        searchRecyclerView.setLayoutManager(layoutManager);
        searchRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        //MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search_item);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }




}
