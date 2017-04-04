package me.varlez.movies.movie.list

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra
import android.support.test.espresso.intent.matcher.IntentMatchers.toPackage
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import me.varlez.movies.R
import me.varlez.movies.common.RecyclerViewAssertions
import me.varlez.movies.movie.detail.view.MovieDetailFragment
import me.varlez.movies.movie.list.view.MovieListActivity
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Behavior test of our [me.varlez.movies.movie.list.view.MovieListActivity].
 */
@RunWith(AndroidJUnit4::class)
class MovieListActivityInstrumentationTest {

    @get:Rule
    var activityRule = IntentsTestRule(MovieListActivity::class.java,
            true,
            false)

    @Test
    fun should_see_two_movies() {
        activityRule.launchActivity(Intent())

        onView(withId(R.id.movie_list))
                .check(
                        RecyclerViewAssertions.hasItemsCount(2)
                )
    }

    @Test
    fun should_display_title_and_year() {
        activityRule.launchActivity(Intent())

        onView(withText("Batman"))
                .check(
                        matches(
                                hasSibling(
                                        withText("2005")
                                )
                        )
                )
    }

    @Test
    fun should_open_details_when_clicking_on_movie() {
        activityRule.launchActivity(Intent())

        onView(withId(R.id.movie_list))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
                )

        Intents.intended(allOf(
                toPackage("me.varlez.movies"),
                hasExtra(MovieDetailFragment.ARG_MOVIE_ID, "1")
        ))
    }

}
