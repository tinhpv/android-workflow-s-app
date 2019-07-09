package com.example.workflow_s.ui.checklist.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.ui.task.task_checklist.ChecklistTaskFragment;
import com.example.workflow_s.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class CurrentChecklistAdapter extends RecyclerView.Adapter<CurrentChecklistAdapter.CurrentChecklistViewHolder> {

    //datasource for recyclerview
    private List<Checklist> mChecklists;
    private List<Checklist> mChecklistsFull;
    private RecyclerView mRecyclerView;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView =recyclerView;
    }

    @NonNull
    @Override
    public CurrentChecklistViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyclerview_item_current_checklist, viewGroup, false);
        CurrentChecklistViewHolder viewHolder = new CurrentChecklistViewHolder(view);

        //checklist item click
        viewHolder.mChecklistItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mRecyclerView.getChildLayoutPosition(v);
                Bundle args = new Bundle();
                args.putString("checklistId", String.valueOf(mChecklists.get(index).getId()));
                CommonUtils.replaceFragments(v.getContext(), ChecklistTaskFragment.class, args);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentChecklistViewHolder currentChecklistViewHolder, int i) {
        currentChecklistViewHolder.mTextView.setText(mChecklists.get(i).getName());
    }

    @Override
    public int getItemCount() {
        if (mChecklists == null) {
            return 0;
        }
        return mChecklists.size();
    }

    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Checklist> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(mChecklistsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Checklist item : mChecklistsFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filterList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mChecklists.clear();
            mChecklists.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };



    //viewholder
    public class CurrentChecklistViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mChecklistItem;
        private TextView mTextView;
        private View view;

        public CurrentChecklistViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            mChecklistItem = itemView.findViewById(R.id.checklist_item);
            mTextView = itemView.findViewById(R.id.tv_checklist_current);
        }
    }

    public CurrentChecklistAdapter() {}

    public void setChecklists(List<Checklist> checklists) {
        mChecklists = checklists;
        mChecklistsFull = new ArrayList<>(checklists);
        this.notifyDataSetChanged();
    }
}
