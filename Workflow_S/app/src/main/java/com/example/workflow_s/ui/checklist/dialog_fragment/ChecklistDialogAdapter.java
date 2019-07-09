package com.example.workflow_s.ui.checklist.dialog_fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workflow_s.R;

import java.util.EventListener;
import java.util.List;

public class ChecklistDialogAdapter extends RecyclerView.Adapter<ChecklistDialogAdapter.ChecklistDialogViewHolder>{

    List<String> mTemplateChecklists;
    String selectedTemplate;
    RecyclerView mRecyclerView;

    EventListener listener;

    public interface EventListener {
        void onEvent(String template);
    }

    public void setmTemplateChecklists(List<String> templateChecklists) {
        mTemplateChecklists = templateChecklists;
        notifyDataSetChanged();
    }

    public void setSelectedTemplate(String selectedTemplate) {
        this.selectedTemplate = selectedTemplate;
    }

    public ChecklistDialogAdapter(EventListener listener) {this.listener = listener;}

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ChecklistDialogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyclerview_item_template_category, viewGroup, false);
        final  ChecklistDialogViewHolder viewHolder = new ChecklistDialogViewHolder(view);

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mTemplateChecklists.indexOf(selectedTemplate);
                ChecklistDialogViewHolder oldHolder = (ChecklistDialogViewHolder) mRecyclerView.findViewHolderForAdapterPosition(index);
                oldHolder.imgChecked.setVisibility(View.INVISIBLE);

                ChecklistDialogViewHolder currentHolder = (ChecklistDialogViewHolder) mRecyclerView.findContainingViewHolder(v);
                currentHolder.imgChecked.setVisibility(View.VISIBLE);
                selectedTemplate = mTemplateChecklists.get(mRecyclerView.getChildLayoutPosition(v));

                listener.onEvent(selectedTemplate);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChecklistDialogViewHolder checklistDialogViewHolder, int i) {
        checklistDialogViewHolder.mTemplateChecklistName.setText(mTemplateChecklists.get(i));
        if (mTemplateChecklists.get(i) == selectedTemplate) {
            checklistDialogViewHolder.imgChecked.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (mTemplateChecklists == null) {
            return 0;
        }
        return mTemplateChecklists.size();
    }

    public class ChecklistDialogViewHolder extends RecyclerView.ViewHolder {

        public TextView mTemplateChecklistName;
        public ImageView imgChecked;
        public View view;

        public ChecklistDialogViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            mTemplateChecklistName = itemView.findViewById(R.id.tv_category_name);
            imgChecked = itemView.findViewById(R.id.img_checked);
        }
    }
}
