package com.example.workflow_s.ui.template.dialog_fragment;

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

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-02
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TemplateDialogFragment extends BottomSheetDialogFragment implements TemplateDialogAdapter.EventListener {

    @Override
    public void onEvent(String category) {
        listener.onFinishSelectCategory(category);
        dismiss();
    }

    public interface DataBackContract {
        void onFinishSelectCategory(String category);
    }

    View view;
    private RecyclerView templateCategoryRecyclerView;
    private TemplateDialogAdapter mAdapter;
    private RecyclerView.LayoutManager templateCategoryLayoutManager;
    private String selectedCategory;
    DataBackContract listener;

    public static TemplateDialogFragment newInstance() {
        return new TemplateDialogFragment();
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
        view = View.inflate(getContext(), R.layout.fragment_template_dialog, null);
        dialog.setContentView(view);
        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        setupTemplateRV();
        loadData();
    }


    private void loadData() {
        Bundle args = getArguments();
        ArrayList<String> dataList = (ArrayList<String>) args.getSerializable("category_list");
        selectedCategory = args.getString("selected_category");
        mAdapter.setSelectedCategory(selectedCategory);
        mAdapter.setTemplateList(dataList);
    }

    private void setupTemplateRV() {
        templateCategoryRecyclerView = view.findViewById(R.id.rv_template_category);
        templateCategoryRecyclerView.setHasFixedSize(true);
        templateCategoryLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        templateCategoryRecyclerView.setLayoutManager(templateCategoryLayoutManager);
        mAdapter = new TemplateDialogAdapter(this);
        templateCategoryRecyclerView.setAdapter(mAdapter);
    }
}
