package me.varlez.movies.common;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static junit.framework.Assert.assertEquals;

/**
 * Useful assertion to work with RecyclerView.
 *
 * @see <a href="https://gist.github.com/chemouna/00b10369eb1d5b00401b">Based on that Gist</a>
 */
public final class RecyclerViewAssertions {

    public static ViewAssertion hasItemsCount(final int count) {
        return new ViewAssertion() {
            @Override public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof RecyclerView)) {
                    throw e;
                }
                RecyclerView rv = (RecyclerView) view;
                assertEquals(rv.getAdapter().getItemCount(), count);
            }
        };
    }

}
