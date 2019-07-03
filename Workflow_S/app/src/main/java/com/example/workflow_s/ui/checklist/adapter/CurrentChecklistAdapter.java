package com.example.workflow_s.ui.checklist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Checklist;

import java.util.List;

public class CurrentChecklistAdapter extends RecyclerView.Adapter<CurrentChecklistAdapter.CurrentChecklistViewHolder> {

    //datasource for recyclerview
    private List<Checklist> mChecklists;

    @NonNull
    @Override
    public CurrentChecklistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyclerview_item_current_checklist, viewGroup, false);
        CurrentChecklistViewHolder viewHolder = new CurrentChecklistViewHolder(view);
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

    //viewholder
    public class CurrentChecklistViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public CurrentChecklistViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_checklist_current);
        }
    }

    public CurrentChecklistAdapter() {}

    public void setChecklists(List<Checklist> checklists) {
        mChecklists = checklists;
        this.notifyDataSetChanged();
    }
}
