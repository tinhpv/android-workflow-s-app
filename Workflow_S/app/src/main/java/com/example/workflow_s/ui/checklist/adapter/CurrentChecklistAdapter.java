package com.example.workflow_s.ui.checklist.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.ui.task.task_checklist.ChecklistTaskFragment;
import com.example.workflow_s.utils.CommonUtils;
import com.example.workflow_s.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class CurrentChecklistAdapter extends RecyclerView.Adapter<CurrentChecklistAdapter.CurrentChecklistViewHolder> {

    EventListener listener;

    public interface EventListener {
        void onEvent(int deletedChecklistId);
        void onChange(int checklistId, String name);
    }

    public CurrentChecklistAdapter(EventListener listener, Context context) {
        this.listener = listener;
        this.mContext = context;
    }


    //datasource for recyclerview
    private List<Checklist> mChecklists;
    private List<Checklist> mChecklistsFull;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private Dialog changeDialog;
    private EditText edtChecklistName;
    private TextView idchecklist;
    private int pos;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView =recyclerView;
    }

    @NonNull
    @Override
    public CurrentChecklistViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        final Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.recyclerview_item_checklist_progress, viewGroup, false);
        final CurrentChecklistViewHolder viewHolder = new CurrentChecklistViewHolder(view);


        //dialog
        changeDialog = new Dialog(context);
        changeDialog.setContentView(R.layout.dialog_edit_checklist_name);
        changeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        idchecklist = changeDialog.findViewById(R.id.id_checklist);
        edtChecklistName = changeDialog.findViewById(R.id.edt_name);
        Button saveName = changeDialog.findViewById(R.id.bt_save);
        saveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onChange(Integer.parseInt(idchecklist.getText().toString()), edtChecklistName.getText().toString().trim());
                changeDialog.cancel();
            }
        });

        Button cancel = changeDialog.findViewById(R.id.bt_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDialog.cancel();
            }
        });

        //checklist item click
        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = viewHolder.getAdapterPosition();
                Bundle args = new Bundle();
                args.putString("checklistId", String.valueOf(mChecklists.get(index).getId()));
                args.putInt("location", 2);
                args.putSerializable("listMember", mChecklists.get(index).getChecklistMembers());
                CommonUtils.replaceFragments(v.getContext(), ChecklistTaskFragment.class, args, true);
            }
        });
        //checklist item long click
        viewHolder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changeDialog.show();
                int index = viewHolder.getAdapterPosition();
                pos = index;
                edtChecklistName.setText(mChecklists.get(index).getName());
                idchecklist.setText(mChecklists.get(index).getId() + "");
                return true;
            }
        });
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull CurrentChecklistViewHolder currentChecklistViewHolder, int i) {
        currentChecklistViewHolder.mChecklistName.setText(mChecklists.get(i).getName());
        currentChecklistViewHolder.mTemplateName.setText(mChecklists.get(i).getTemplateName());

        String timeCreated = mChecklists.get(i).getTimeCreated().split("T")[0];
        Date dateCreate = DateUtils.parseDate(timeCreated);
        String monthString = (String) DateFormat.format("MMM", dateCreate);
        String day = (String) DateFormat.format("dd", dateCreate);
        currentChecklistViewHolder.mDayCreated.setText(day);
        currentChecklistViewHolder.mMonthCreated.setText(monthString);

        if (mChecklists.get(i).getTemplateStatus().equals("Done")) {
            currentChecklistViewHolder.mDueTime.setText("completed");
            currentChecklistViewHolder.mDueTime.setBackground(mContext.getResources().getDrawable(R.drawable.container_radius_green));
            currentChecklistViewHolder.mDueTime.setTextColor(Color.parseColor("#FFFFFF"));
            currentChecklistViewHolder.progressBar.setProgressColor(mContext.getResources().getColor(R.color.accomplishedColor));

        } else if (getDueTimeOfChecklist(i).equals("expired")) {
            currentChecklistViewHolder.mDueTime.setText(getDueTimeOfChecklist(i));
            currentChecklistViewHolder.mDueTime.setBackground(mContext.getResources().getDrawable(R.drawable.container_radius_red));
            currentChecklistViewHolder.mDueTime.setTextColor(Color.parseColor("#FFFFFF"));
            currentChecklistViewHolder.progressBar.setProgressColor(mContext.getResources().getColor(R.color.rederror));

        } else {
            currentChecklistViewHolder.mDueTime.setText(getDueTimeOfChecklist(i));
            currentChecklistViewHolder.mDueTime.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryText));
        }

        int doneTask = mChecklists.get(i).getDoneTask();
        int totalTask = mChecklists.get(i).getTotalTask();

        if (totalTask == 0) {
            currentChecklistViewHolder.progressBar.setProgress(0, 100);
        } else {
            int progress = (int) ((doneTask / (totalTask * 1.0)) * 100);
            currentChecklistViewHolder.progressBar.setProgress(progress, 100);
        }
    }

    private String getDueTimeOfChecklist(int index) {
        String time = "not set";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (mChecklists.get(index).getDueTime() != null) {
            String dateSelected = mChecklists.get(index).getDueTime().split("T")[0];
            String timeSelected = mChecklists.get(index).getDueTime().split("T")[1];
            Date currentTime = Calendar.getInstance().getTime();
            String dueTime = dateSelected + " " + timeSelected;
            try {
                Date overdue = sdf.parse(dueTime);
                long totalTime = overdue.getTime() - currentTime.getTime();
                time = String.format("%dh",
                        TimeUnit.MILLISECONDS.toHours(totalTime));
                if (Integer.parseInt(time.split("h")[0]) == 0) {
                    time = String.format("%dm",
                            TimeUnit.MILLISECONDS.toMinutes(totalTime));
                    if (Integer.parseInt(time.split("m")[0]) <= 0) {
                        time = "expired";
                    }
                }  else if (Integer.parseInt(time.split("h")[0]) <= 0){
                    time = "expired";
                } else {
                    Date upcomingDate = DateUtils.parseDate(dateSelected);
                    time = upcomingDate.toString().split(" ")[1] + " " + upcomingDate.toString().split(" ")[2];
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return time;
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

    public void deleteItem(int position) {
        Checklist checklist = mChecklists.get(position);
        listener.onEvent(checklist.getId());
    }



    //viewholder
    public class CurrentChecklistViewHolder extends RecyclerView.ViewHolder {

        private TextView mChecklistName,mTemplateName, mDueTime, mDayCreated, mMonthCreated;
        private ConstraintLayout item;
        private CircularProgressIndicator progressBar;

        public CurrentChecklistViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item_checklist_progress);
            mChecklistName = itemView.findViewById(R.id.tv_checklist_name);
            mTemplateName = itemView.findViewById(R.id.tv_template_name);
            progressBar = itemView.findViewById(R.id.pb_checklist_home);
            progressBar.setMaxProgress(100);
            mDueTime = itemView.findViewById(R.id.tv_due_time_checklist);
            mDayCreated = item.findViewById(R.id.tv_day_created);
            mMonthCreated = item.findViewById(R.id.tv_month_created);
        }
    }

    public void setChecklists(List<Checklist> checklists) {
        mChecklists = checklists;
        mChecklistsFull = new ArrayList<>(checklists);
        this.notifyDataSetChanged();
    }
}
