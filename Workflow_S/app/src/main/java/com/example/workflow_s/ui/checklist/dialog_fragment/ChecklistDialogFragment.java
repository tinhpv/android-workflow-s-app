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

import java.util.ArrayList;

public class ChecklistDialogFragment extends BottomSheetDialogFragment implements ChecklistDialogAdapter.EventListener {

    View view;
    private RecyclerView templateChecklistRecyclerView;
    private ChecklistDialogAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String selectedTemplate;
    DataBackContract listener;

    @Override
    public void onEvent(String template) {
        listener.onFinishSelectTemplate(template);
        dismiss();
    }

    public interface DataBackContract {
        void onFinishSelectTemplate(String template);
    }

    public static ChecklistDialogFragment newInstance() {return new ChecklistDialogFragment();}

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
        ArrayList<String> dataList = (ArrayList<String>) args.getSerializable("template_list");
        selectedTemplate = args.getString("selected_template");
        mAdapter.setSelectedTemplate(selectedTemplate);
        mAdapter.setmTemplateChecklists(dataList);
    }

    private void setupTemplateRV() {
        templateChecklistRecyclerView = view.findViewById(R.id.rv_template_category);
        templateChecklistRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        templateChecklistRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ChecklistDialogAdapter(this);
        templateChecklistRecyclerView.setAdapter(mAdapter);
    }
}
