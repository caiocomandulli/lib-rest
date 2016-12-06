package com.comandulli.lib;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Formatter for time strings.
 */
public class TimeStringFormatter {

    /**
     * The constant MY_SQL_DATETIME_FORMAT.
     */
    public static final String MY_SQL_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * The constant HUMAN_FRIENDLY_DATETIME_FORMAT.
     */
    public static final String HUMAN_FRIENDLY_DATETIME_FORMAT = " 'of' LLLL, yyyy";
    /**
     * The constant HUMAN_FRIENDLY_DATETIME_FORMAT_PT.
     */
    public static final String HUMAN_FRIENDLY_DATETIME_FORMAT_PT = "dd 'de' LLLL, yyyy";
    /**
     * The constant HUMAN_FRIENDLY_DATETIME_FORMAT_IT.
     */
    public static final String HUMAN_FRIENDLY_DATETIME_FORMAT_IT = "dd 'di' LLLL, yyyy";
    /**
     * The constant SIMPLE_DATETIME_FORMAT.
     */
    public static final String SIMPLE_DATETIME_FORMAT = "yyyy-MM-dd";
    /**
     * The constant UNIVERSAL_TIME_ZONE.
     */
    public static final String UNIVERSAL_TIME_ZONE = "UTC";

    /**
     * Format length in seconds in hour format. hh:mm
     *
     * @param length seconds
     * @return formatted hour
     */
    public static String formatTimeLength(String length) {
        int parsed = Integer.parseInt(length);
        return formatTimeLength(parsed);
    }

    /**
     * Format length in seconds in hour format. hh:mm
     *
     * @param length seconds
     * @return formatted hour
     */
    public static String formatTimeLength(int length) {
        String value;

        int seconds = length % 60;
        int minutes = length / 60;

        int hours = minutes / 60;
        if (hours > 0) {
            minutes %= 60;

            value = hours + ":" + String.format(Locale.getDefault(), "%02d", minutes) + ":" + String.format(Locale.getDefault(), "%02d", seconds);
        } else {
            value = String.format(Locale.getDefault(), "%02d", minutes) + ":" + String.format(Locale.getDefault(), "%02d", seconds);
        }

        return value;
    }

    /**
     * Format date in mysql format. yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param date the date
     * @return the formatted date
     */
    public static String formatDateMySQL(Date date) {
        if (date != null) {
            return new SimpleDateFormat(MY_SQL_DATETIME_FORMAT, Locale.getDefault()).format(date);
        } else {
            return null;
        }
    }

    /**
     * Format date in a human friendly format.
     *
     * @param date the date
     * @return the formatted date
     */
    @SuppressWarnings("deprecation")
    public static String formatDateHumanFriendly(Date date) {
        if (date != null) {
            Locale defaultLocale = Locale.getDefault();
            String language = defaultLocale.getLanguage().toUpperCase(defaultLocale);
            String formatted;
            switch (language) {
                case "PT":
                    formatted = new SimpleDateFormat(HUMAN_FRIENDLY_DATETIME_FORMAT_PT, Locale.getDefault()).format(date);
                    break;
                case "IT":
                    formatted = new SimpleDateFormat(HUMAN_FRIENDLY_DATETIME_FORMAT_IT, Locale.getDefault()).format(date);
                    break;

                default:
                    String dayOfMonth = getDayOfMonthSuffix(date.getDate());
                    formatted = dayOfMonth + new SimpleDateFormat(HUMAN_FRIENDLY_DATETIME_FORMAT, Locale.getDefault()).format(date);
                    break;
            }
            return formatted;
        } else {
            return null;
        }
    }

    /**
     * Format date in a simple format. yyyy-MM-dd
     *
     * @param date the date
     * @return formatted date
     */
    public static String formatDateSimple(Date date) {
        if (date != null) {
            return new SimpleDateFormat(SIMPLE_DATETIME_FORMAT, Locale.getDefault()).format(date);
        } else {
            return null;
        }
    }

    /**
     * Gets day of month suffix.
     *
     * @param n the n day of the month
     * @return the day of month suffix
     */
    public static String getDayOfMonthSuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return n + "th";
        }
        switch (n % 10) {
            case 1:
                return n + "st";
            case 2:
                return n + "nd";
            case 3:
                return n + "rd";
            default:
                return n + "th";
        }
    }

}
