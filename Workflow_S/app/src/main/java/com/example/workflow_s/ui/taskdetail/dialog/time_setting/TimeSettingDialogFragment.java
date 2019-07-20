package com.example.workflow_s.ui.taskdetail.dialog.time_setting;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.workflow_s.R;
import com.example.workflow_s.model.ChecklistMember;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.TaskMember;
import com.example.workflow_s.utils.DateUtils;
import com.example.workflow_s.utils.SharedPreferenceUtils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-07
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TimeSettingDialogFragment extends DialogFragment implements View.OnClickListener, TimeSettingContract.TimeSettingView {

    View view;
    private TextView dateTextView, timeTextView;
    private Button saveButton,cancelButton, okButton;
    private ImageButton setDateButton, setTimeButton;

    private List<TaskMember> mTaskMemberList;
    private String previousDueTime, dateSelected, timeSelected;
    private int taskId;

    TimeSettingContract.TimeSettingPresenter mPresenter;

    public static TimeSettingDialogFragment newInstance(int taskId, List<TaskMember> taskMemberList) {
        TimeSettingDialogFragment frag = new TimeSettingDialogFragment();
        Bundle args = new Bundle();
        args.putInt("taskId", taskId);
        args.putSerializable("taskMembers", (Serializable) taskMemberList);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_set_duetime, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getData();
    }


    private void getData() {
        taskId = getArguments().getInt("taskId");
        mTaskMemberList = (List<TaskMember>) getArguments().getSerializable("taskMembers");
        mPresenter = new TimeSettingPresenterImpl(this, new TimeSettingInteractor());
        mPresenter.getTaskInfo(taskId);
    }


    @Override
    public void finishedGetTask(Task task) {
        if (null != task) {
            previousDueTime = task.getDueTime();
            dateSelected = previousDueTime.split("T")[0];
            timeSelected = previousDueTime.split("T")[1];
            bindUI();
        }
    }

    private void bindUI() {
        dateTextView = view.findViewById(R.id.tv_date);
        dateTextView.setText(dateSelected);
        timeTextView = view.findViewById(R.id.tv_time);
        timeTextView.setText(timeSelected);


        saveButton = view.findViewById(R.id.bt_save_time);
        cancelButton = view.findViewById(R.id.bt_cancel_save);
        okButton = view.findViewById(R.id.bt_ok);
        setDateButton = view.findViewById(R.id.bt_setdate);
        setTimeButton = view.findViewById(R.id.bt_settime);

        if (checkIfUserIsTaskMember()) {
            saveButton.setVisibility(View.VISIBLE);
            saveButton.setOnClickListener(this);
            cancelButton.setVisibility(View.VISIBLE);
            cancelButton.setOnClickListener(this);
            okButton.setVisibility(View.GONE);
            setTimeButton.setVisibility(View.VISIBLE);
            setDateButton.setVisibility(View.VISIBLE);
            setDateButton.setOnClickListener(this);
            setTimeButton.setOnClickListener(this);
        } else {
            saveButton.setVisibility(View.GONE);
            cancelButton.setVisibility(View.GONE);
            okButton.setVisibility(View.VISIBLE);
            okButton.setOnClickListener(this);
            setTimeButton.setVisibility(View.GONE);
            setDateButton.setVisibility(View.GONE);
        }

    }

    private boolean checkIfUserIsTaskMember() {
        String userId = SharedPreferenceUtils.retrieveData(getActivity(), getActivity().getString(R.string.pref_userId));
        for (TaskMember member : mTaskMemberList) {
            if (userId.equals(member.getUserId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_save_time:
                handleSaveTime();
                break;
            case R.id.bt_cancel_save:
                dismiss();
                break;
            case R.id.bt_ok:
                dismiss();
                break;
            case R.id.bt_setdate:
                handleSetDate();
                break;
            case R.id.bt_settime:
                handleSetTime();
        }
    }


    boolean isValueDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        dateFormat.format(date);

        Date currentDate = DateUtils.parseDate(dateFormat.format(date).split(" ")[0]);
        Date newDateSelected = DateUtils.parseDate(dateSelected);

        if (currentDate.after(newDateSelected)) {
            return false;
        } else if (currentDate.equals(newDateSelected)) {

            Date currentTime = DateUtils.parseTime(dateFormat.format(date).split(" ")[1]);
            Date newTimeSelected = DateUtils.parseTime(timeSelected);
            if (currentTime.after(newTimeSelected)) {
                return false;
            }
        }

        return true;
    }

    private void handleSaveTime() {
        if (isValueDateTime()) {
            String datetime = dateSelected + " " + timeSelected;
            mPresenter.setDueTime(taskId, datetime);
        } else {
            Animation shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake_animation);
            dateTextView.startAnimation(shakeAnimation);
            dateTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.color_alert));
            timeTextView.startAnimation(shakeAnimation);
            timeTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.color_alert));
        }
    }

    private void handleSetTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeTextView.setText( selectedHour + ":" + selectedMinute);
                timeSelected = String.valueOf(selectedHour) + ":" + String.valueOf(selectedMinute) + ":00";
                dateTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryText));
                timeTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryText));
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.show();
    }

    private void handleSetDate() {

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String dateFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

                dateTextView.setText(sdf.format(myCalendar.getTime()));
                dateSelected = sdf.format(myCalendar.getTime());
                dateTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryText));
                timeTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryText));
            }
        }; // end create date instance

        new DatePickerDialog(
                getContext(),
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void finishSetDueTime() {
        Toast.makeText(getContext(), "Set Time successfully", Toast.LENGTH_SHORT).show();
        dismiss();
    }
}
