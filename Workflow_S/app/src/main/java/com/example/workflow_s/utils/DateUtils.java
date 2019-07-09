package com.example.workflow_s.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-07
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class DateUtils {

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseTime(String time) {
        try {
            return new SimpleDateFormat("HH:mm").parse(time);
        } catch (ParseException e) {
            return null;
        }
    }
}
