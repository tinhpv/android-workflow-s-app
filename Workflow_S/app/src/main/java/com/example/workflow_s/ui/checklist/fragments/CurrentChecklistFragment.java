package com.example.workflow_s.ui.checklist.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.ui.checklist.ChecklistContract;
import com.example.workflow_s.ui.checklist.ChecklistInteractor;
import com.example.workflow_s.ui.checklist.ChecklistPresenterImpl;
import com.example.workflow_s.ui.checklist.adapter.CurrentChecklistAdapter;
import com.example.workflow_s.utils.SharedPreferenceUtils;

import java.util.ArrayList;

public class CurrentChecklistFragment extends Fragment implements ChecklistContract.ChecklistView {

    View view;
    private CurrentChecklistAdapter mCurrentChecklistAdapter;
    private RecyclerView checklistRecyclerView;
    private RecyclerView.LayoutManager checklistLayoutManager;

    private ChecklistContract.ChecklistPresenter mPresenter;
    //private LinearLayout mChecklistDataStatusMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_current_checklists, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //mChecklistDataStatusMessage = view.findViewById(R.id.checklist_data_notfound_message);
        setupChecklistRV();
        initData();
    }

    //FIXME - HARDCODE FOR TESTING
    private void initData() {
        mPresenter = new ChecklistPresenterImpl(this, new ChecklistInteractor());
        mPresenter.loadAllChecklist("1");
    }

    private void setupChecklistRV() {
        checklistRecyclerView = view.findViewById(R.id.rv_checklist);
        checklistRecyclerView.setHasFixedSize(true);
        checklistLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        checklistRecyclerView.setLayoutManager(checklistLayoutManager);

        mCurrentChecklistAdapter = new CurrentChecklistAdapter();
        checklistRecyclerView.setAdapter(mCurrentChecklistAdapter);
    }

    @Override
    public void setDataToChecklistRecyclerView(ArrayList<Checklist> datasource) {
        String userId = SharedPreferenceUtils.retrieveData(getContext(),getContext().getString(R.string.pref_userId));
        //Log.i("userId", userId);
        if (datasource.isEmpty()) {

        }
        ArrayList<Checklist> arrayList = new ArrayList<>();
        for (int i = 0; i < datasource.size(); i ++) {
            //FIXME HARDCODE FOR TESTING
            if (datasource.get(i).getUserId().equals(userId)) {
                arrayList.add(datasource.get(i));
            }
        }
        Log.i("My Checklist", arrayList.size() + "");
        mCurrentChecklistAdapter.setChecklists(arrayList);
    }


}
