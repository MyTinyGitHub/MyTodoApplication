package com.dominikpall.todoapplication.util;

import androidx.room.TypeConverter;

import com.dominikpall.todoapplication.model.RepeatCycle;

import java.util.Date;

/**
 * Class that handler necessary conversion
 */
public class Converter {

    /**
     * Convert method to convert from timestamp to date
     * @param value value to be converted
     * @return Date correspoding date
     */
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    /**
     * Method to convert date to timestamp
     * @param date date to be converted
     * @return Long resulting timestamp
     */
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    /**
     * Method to convert from Repeat cycle to name
     * @param repeatCycle repeat cycle to be converted
     * @return String corresponding name of the repeat cycle
     */
    @TypeConverter
    public static String fromRepeatCycle(RepeatCycle repeatCycle) {
        return repeatCycle == null ? null : repeatCycle.name();
    }

    /**
     * Method to convert from string to repeat cycle
     * @param repeatCycle repeat cycle in String format to be converted
     * @return RepeatCycle correspoding repeat cycle
     */
    @TypeConverter
    public static RepeatCycle fromStringToRepeatCycle(String repeatCycle) {
        return repeatCycle == null ? null : RepeatCycle.valueOf(repeatCycle);
    }
}
