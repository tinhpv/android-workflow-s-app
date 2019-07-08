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
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.TaskMember;
import com.example.workflow_s.ui.checklist.ChecklistContract;
import com.example.workflow_s.ui.checklist.ChecklistInteractor;
import com.example.workflow_s.ui.checklist.ChecklistPresenterImpl;
import com.example.workflow_s.ui.checklist.adapter.CurrentChecklistAdapter;
import com.example.workflow_s.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class CurrentChecklistFragment extends Fragment implements ChecklistContract.ChecklistView {

    private static final String NAME_ARG = "CurrentChecklist";

    View view;
    private CurrentChecklistAdapter mCurrentChecklistAdapter;
    private RecyclerView checklistRecyclerView;
    private RecyclerView.LayoutManager checklistLayoutManager;

    private ArrayList<Checklist> checklists, currentChecklist;
    private Checklist tmpChecklist;

    private ChecklistContract.ChecklistPresenter mPresenter;
    //private LinearLayout mChecklistDataStatusMessage;

    public CurrentChecklistFragment() {}

    //static constructor
    public static CurrentChecklistFragment newInstance() {
        final CurrentChecklistFragment currentChecklistFragment = new CurrentChecklistFragment();
        // The 1 below is an optimization, being the number of arguments that will
        // be added to this bundle.  If you know the number of arguments you will add
        // to the bundle it stops additional allocations of the backing map.  If
        // unsure, you can construct Bundle without any arguments
        //final Bundle args = new Bundle(1);
        // This stores the argument as an argument in the bundle.  Note that even if
        // the 'name' parameter is NULL then this will work, so you should consider
        // at this point if the parameter is mandatory and if so check for NULL and
        // throw an appropriate error if so
        return currentChecklistFragment;
    }

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
        String orgId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_orgId));
        mPresenter.loadAllChecklist(orgId);
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
//        String userId = SharedPreferenceUtils.retrieveData(getContext(),getContext().getString(R.string.pref_userId));
//        //Log.i("userId", userId);
//        if (datasource.isEmpty()) {
//
//        }
//        ArrayList<Checklist> arrayList = new ArrayList<>();
//        for (int i = 0; i < datasource.size(); i ++) {
//            //FIXME HARDCODE FOR TESTING
//            if (datasource.get(i).getUserId().equals(userId)) {
//                arrayList.add(datasource.get(i));
//            }
//        }
//        Log.i("My Checklist", arrayList.size() + "");
//        mCurrentChecklistAdapter.setChecklists(arrayList);
        checklists = new ArrayList<>();
        checklists = datasource;
        checkData();
        if (currentChecklist != null) {
            for (Checklist item : currentChecklist) {
                Log.i("My current checklist: ", item.getName());
            }
        } else {
            Log.i("My current checklist: ", "don't have data");
        }

    }

    @Override
    public void finishFirstTaskFromChecklist(Task task) {
        if (task != null) {
            String userId = SharedPreferenceUtils.retrieveData(getContext(),getContext().getString(R.string.pref_userId));
            List<TaskMember> taskMemberList = task.getTaskMemberList();
            //taskMemberList = task.getTaskMemberList();
            if (taskMemberList != null) {
                for (int i = 0; i < taskMemberList.size(); i++) {
                    if (taskMemberList.get(i).getUserId().equals(userId)) {
                        if (currentChecklist.isEmpty()) {
                            currentChecklist = new ArrayList<>();
                        }
                        currentChecklist.add(tmpChecklist);
                        Log.i("Current checklist", tmpChecklist.getName());
                    }
                }
            }
        }
        //mCurrentChecklistAdapter.setChecklists(currentChecklist);
    }

    private void checkData() {
        for (int i = 0; i < checklists.size(); i++) {
            tmpChecklist = checklists.get(i);
            mPresenter = new ChecklistPresenterImpl(this, new ChecklistInteractor());
            mPresenter.loadFirstTaskFromChecklist(tmpChecklist.getId());
            Log.i("MyId current: ", tmpChecklist.getId() + "");
        }
    }


}
