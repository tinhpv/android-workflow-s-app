package com.example.workflow_s.ui.organization.dialog_fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.workflow_s.R;

import java.util.ArrayList;

public class OrganizationDialogFragment extends BottomSheetDialogFragment implements OrganizationDialogAdapter.EventListener{

    View view;
    private RecyclerView organizationNameRecyclerView;
    private OrganizationDialogAdapter mAdapter;
    private RecyclerView.LayoutManager orgNameLayoutManager;
    private String selectedOrgName;
    DataBackContract listener;

    public interface DataBackContract {
        void onFinishSelectOrgName(String orgName);
    }
    @Override
    public void onEvent(String orgName) {
        listener.onFinishSelectOrgName(orgName);
        dismiss();
    }

    public static OrganizationDialogFragment newInstance() {
        return new OrganizationDialogFragment();
    }

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
        view = View.inflate(getContext(), R.layout.fragment_organization_dialog, null);
        dialog.setContentView(view);
        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        setupOrganizationRV();
        loadData();

    }

    private void loadData() {
        Bundle args = getArguments();
        ArrayList<String> dataList = (ArrayList<String>) args.getSerializable("org_list");
        selectedOrgName = args.getString("selected_org_name");
        mAdapter.setSelectedOrgName(selectedOrgName);
        mAdapter.setmOrganizationNameList(dataList);
    }

    private void setupOrganizationRV() {
        organizationNameRecyclerView = view.findViewById(R.id.rv_org_name);
        organizationNameRecyclerView.setHasFixedSize(true);
        orgNameLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        organizationNameRecyclerView.setLayoutManager(orgNameLayoutManager);
        mAdapter = new OrganizationDialogAdapter(this);
        organizationNameRecyclerView.setAdapter(mAdapter);
    }
}
