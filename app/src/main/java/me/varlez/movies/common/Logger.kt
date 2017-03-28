package me.varlez.movies.common

import android.util.Log

/**
 * Utility class for simple logging features.
 */
object Logger {

    private val LOG_TAG = "Movies"

    fun d(message: String, vararg args: Any) {
        Log.d(LOG_TAG, String.format(message, *args))
    }

    fun e(message: String, vararg args: Any) {
        Log.e(LOG_TAG, String.format(message, *args))
    }
}
