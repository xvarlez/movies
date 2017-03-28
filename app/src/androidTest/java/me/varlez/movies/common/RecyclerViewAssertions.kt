package me.varlez.movies.common

import android.support.test.espresso.ViewAssertion
import android.support.v7.widget.RecyclerView
import junit.framework.Assert.assertEquals

/**
 * Useful assertion to work with RecyclerView.

 * @see [Based on that Gist](https://gist.github.com/chemouna/00b10369eb1d5b00401b)
 */
object RecyclerViewAssertions {

    fun hasItemsCount(count: Int): ViewAssertion {
        return ViewAssertion { view, e ->
            if (view !is RecyclerView) {
                throw e
            }
            assertEquals(view.adapter.itemCount, count)
        }
    }

}
