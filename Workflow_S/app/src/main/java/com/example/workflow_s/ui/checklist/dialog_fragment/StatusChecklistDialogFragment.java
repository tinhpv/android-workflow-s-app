package com.example.workflow_s.ui.checklist.dialog_fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workflow_s.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class StatusChecklistDialogFragment extends BottomSheetDialogFragment implements StatusChecklistDialogAdapter.EventListener {

    View view;
    private RecyclerView statusChecklistRecyclerView;
    private StatusChecklistDialogAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String selectedStatus;
    DataBackContract listener;
    @Override
    public void onEvent(String status) {
        listener.onFinishSelectStatus(status);
        dismiss();
    }

    public interface DataBackContract {
        void onFinishSelectStatus(String status);
    }

    public static StatusChecklistDialogFragment newInstance() {return new StatusChecklistDialogFragment();}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (DataBackContract) getTargetFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        view = View.inflate(getContext(), R.layout.fragment_template_dialog, null);
        dialog.setContentView(view);
        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        setupStatusRV();
        loadData();
    }

    private void setupStatusRV() {
        statusChecklistRecyclerView = view.findViewById(R.id.rv_template_category);
        statusChecklistRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        statusChecklistRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new StatusChecklistDialogAdapter(this);
        statusChecklistRecyclerView.setAdapter(mAdapter);
    }

    private void loadData() {
        Bundle args = getArguments();
        selectedStatus = args.getString("selected_status");
        mAdapter.setSelectedStatus(selectedStatus);
        mAdapter.setSelectedStatusChecklist();
    }
}
