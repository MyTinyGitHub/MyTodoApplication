package com.dominikpall.todoapplication.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper utility class with static methods
 */
public class Utils {
    /**
     * Method that formats date from date to corresponding pattern
     * @param date dated to be formatted
     * @return String corresponding formatted date
     */
    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("EEE, MMM d");
        return simpleDateFormat.format(date);
    }

    /**
     * Helper to method to hide the keyboard
     * @param view parent view
     */
    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
