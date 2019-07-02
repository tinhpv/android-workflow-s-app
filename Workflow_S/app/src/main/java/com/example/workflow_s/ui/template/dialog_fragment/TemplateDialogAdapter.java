package com.example.workflow_s.ui.template.dialog_fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.workflow_s.R;

import java.util.EventListener;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-02
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TemplateDialogAdapter extends RecyclerView.Adapter<TemplateDialogAdapter.TemplateDialogViewHolder> {

    List<String> mTemplateList;
    String selectedCategory;
    RecyclerView mRecyclerView;
    TemplateDialogFragment mFragment;

    EventListener listener;

    public interface EventListener {
        void onEvent(String category);
    }

    public void setTemplateList(List<String> templateList) {
        mTemplateList = templateList;
        notifyDataSetChanged();
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public TemplateDialogAdapter(EventListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public TemplateDialogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutId = R.layout.recyclerview_item_template_category;
        View view = layoutInflater.inflate(layoutId, viewGroup, false);
        final TemplateDialogViewHolder viewHolder = new TemplateDialogViewHolder(view);

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mTemplateList.indexOf(selectedCategory);
                TemplateDialogViewHolder oldViewHolder = (TemplateDialogViewHolder) mRecyclerView.findViewHolderForAdapterPosition(index);
                oldViewHolder.imgChecked.setVisibility(View.INVISIBLE);

                TemplateDialogViewHolder currentViewHolder = (TemplateDialogViewHolder) mRecyclerView.findContainingViewHolder(v);
                currentViewHolder.imgChecked.setVisibility(View.VISIBLE);
                selectedCategory = mTemplateList.get(mRecyclerView.getChildLayoutPosition(v));

                listener.onEvent(selectedCategory);
//                mFragment.dismiss();
            }
        });

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull TemplateDialogViewHolder templateDialogViewHolder, int i) {
        templateDialogViewHolder.mTemplateCategoryName.setText(mTemplateList.get(i));
        if (mTemplateList.get(i) == selectedCategory) {
            templateDialogViewHolder.imgChecked.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (null == mTemplateList) {
            return 0;
        } else {
            return mTemplateList.size();
        }
    }

    public class TemplateDialogViewHolder extends RecyclerView.ViewHolder {

        public TextView mTemplateCategoryName;
        public ImageView imgChecked;
        public View view;

        public TemplateDialogViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            mTemplateCategoryName = itemView.findViewById(R.id.tv_category_name);
            imgChecked = itemView.findViewById(R.id.img_checked);
        }
    }

}
