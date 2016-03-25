package me.varlez.movies.common;

import android.util.Log;

/**
 * Utility class for simple logging features.
 */
public final class Logger {

    private static final String LOG_TAG = "Movies";

    private Logger() {
    }

    public static void d(String message, Object... args) {
        Log.d(LOG_TAG, String.format(message, args));
    }

    public static void e(String message, Object... args) {
        Log.e(LOG_TAG, String.format(message, args));
    }
}
