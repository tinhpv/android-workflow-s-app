package com.example.workflow_s.ui.task.dialog.time_setting;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
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

import java.util.Date;

import com.example.workflow_s.R;
import com.example.workflow_s.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-07
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TimeSettingDialogFragment extends DialogFragment implements View.OnClickListener, TimeSettingContract.TimeSettingView {

    View view;
    private TextView dateTextView, timeTextView;
    private Button saveButton,cancelButton;
    private ImageButton setDateButton, setTimeButton;
    private String previousDueTime, dateSelected, timeSelected;
    private int taskId;

    TimeSettingContract.TimeSettingPresenter mPresenter;

    public static TimeSettingDialogFragment newInstance(int taskId, String dueTime) {
        TimeSettingDialogFragment frag = new TimeSettingDialogFragment();
        Bundle args = new Bundle();
        args.putInt("taskId", taskId);
        args.putString("dueTime", dueTime);
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
        bindUI();
    }

    private void getData() {
        previousDueTime = getArguments().getString("dueTime");
        dateSelected = previousDueTime.split("T")[0];
        timeSelected = previousDueTime.split("T")[1];
        taskId = getArguments().getInt("taskId");

        mPresenter = new TimeSettingPresenterImpl(this, new TimeSettingInteractor());
    }

    private void bindUI() {
        dateTextView = view.findViewById(R.id.tv_date);
        dateTextView.setText(previousDueTime.split("T")[0]);
        timeTextView = view.findViewById(R.id.tv_time);
        timeTextView.setText(previousDueTime.split("T")[1]);
        saveButton = view.findViewById(R.id.bt_save_time);
        saveButton.setOnClickListener(this);
        cancelButton = view.findViewById(R.id.bt_cancel_save);
        cancelButton.setOnClickListener(this);
        setDateButton = view.findViewById(R.id.bt_setdate);
        setDateButton.setOnClickListener(this);
        setTimeButton = view.findViewById(R.id.bt_settime);
        setTimeButton.setOnClickListener(this);
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
            case R.id.bt_setdate:
                handleSetDate();
                break;
            case R.id.bt_settime:
                handleSetTime();
        }
    }


    boolean isValueDateTime() {
        Date oldDate = DateUtils.parseDate(previousDueTime.split("T")[0]);
        Date newDateSelected = DateUtils.parseDate(dateSelected);
        
        if (oldDate.after(newDateSelected)) {
            return false;
        } else if (oldDate.equals(newDateSelected)) {
            Date oldTime = DateUtils.parseTime(previousDueTime.split("T")[1]);
            Date newTimeSelected = DateUtils.parseTime(timeSelected);
            if (oldTime.after(newTimeSelected)) {
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
