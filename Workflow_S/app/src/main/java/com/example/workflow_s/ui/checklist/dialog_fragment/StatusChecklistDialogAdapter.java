package com.example.workflow_s.ui.checklist.dialog_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workflow_s.R;

import java.util.ArrayList;
import java.util.List;

public class StatusChecklistDialogAdapter extends RecyclerView.Adapter<StatusChecklistDialogAdapter.StatusChecklistDialogViewHolder> {

    private List<String> mStatusChecklists;
    private String selectedStatus;
    private RecyclerView mRecyclerView;

    EventListener listener;

    public interface EventListener {
        void onEvent(String status);
    }

    public void setSelectedStatusChecklist() {
        mStatusChecklists = new ArrayList<>();
        mStatusChecklists.add("All");
        mStatusChecklists.add("Running");
        mStatusChecklists.add("Done");
        mStatusChecklists.add("Expired");
        notifyDataSetChanged();
    }

    public void setSelectedStatus(String selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public StatusChecklistDialogAdapter(EventListener listener) {this.listener = listener;}

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public StatusChecklistDialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyclerview_item_template_category, parent, false);
        final StatusChecklistDialogViewHolder viewHolder = new StatusChecklistDialogViewHolder(view);

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mStatusChecklists.indexOf(selectedStatus);
                StatusChecklistDialogViewHolder oldHolder = (StatusChecklistDialogViewHolder) mRecyclerView.findViewHolderForAdapterPosition(index);
                oldHolder.imgChecked.setVisibility(View.INVISIBLE);

                StatusChecklistDialogViewHolder currentHolder = (StatusChecklistDialogViewHolder) mRecyclerView.findContainingViewHolder(v);
                currentHolder.imgChecked.setVisibility(View.VISIBLE);
                selectedStatus = mStatusChecklists.get(mRecyclerView.getChildLayoutPosition(v));

                listener.onEvent(selectedStatus);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StatusChecklistDialogViewHolder holder, int position) {
        holder.mStatusChecklist.setText(mStatusChecklists.get(position));
        if (mStatusChecklists.get(position) == selectedStatus) {
            holder.imgChecked.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (mStatusChecklists == null) {
            return 0;
        }
        return mStatusChecklists.size();
    }

    public class StatusChecklistDialogViewHolder extends RecyclerView.ViewHolder {

        public TextView mStatusChecklist;
        public ImageView imgChecked;
        public View view;

        public StatusChecklistDialogViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            mStatusChecklist = view.findViewById(R.id.tv_category_name);
            imgChecked = itemView.findViewById(R.id.img_checked);
        }
    }
}
