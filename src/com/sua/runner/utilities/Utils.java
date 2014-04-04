package com.sua.runner.utilities;

import android.content.Context;
import com.sua.runner.R;

public class Utils {

    public static int timeInSeconds(int minutes, int seconds) {
        return minutes * 60 + seconds;
    }

    public static int getSeconds(int timeInSeconds) {
        return timeInSeconds % 60;
    }

    public static int getMinutes(int timeInSeconds) {
        return timeInSeconds / 60;
    }

    public static String timeInString(int timeInSeconds, Context context) {
        int minutes = getMinutes(timeInSeconds);
        int seconds = getSeconds(timeInSeconds);

        StringBuilder builder = new StringBuilder();
        builder.append(minutes).append(" ").append(context.getString(R.string.min)).append(" ").append(seconds).append(" ").append(context.getString(R.string.sec));
        return builder.toString();
    }

    public static String timeInString(int minutes, int seconds, Context context) {
        return timeInString(timeInSeconds(minutes, seconds), context);
    }

    public static String repeatInString(int repeat, Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append(repeat).append(" ").append(context.getString(R.string.times));
        return builder.toString();
    }
}
