package me.varlez.movies.movie.detail

import android.content.Intent
import android.net.Uri
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.intent.matcher.IntentMatchers.hasData
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.runner.AndroidJUnit4
import me.varlez.movies.MockMoviesApp
import me.varlez.movies.R
import me.varlez.movies.common.di.TestAppComponent
import me.varlez.movies.common.rest.MovieService
import me.varlez.movies.movie.detail.view.MovieDetailActivity
import me.varlez.movies.movie.detail.view.MovieDetailFragment
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Behavior test of our [MovieDetailActivity].
 */
@RunWith(AndroidJUnit4::class)
class MovieDetailsActivityInstrumentationTest {

    @Inject
    internal lateinit var movieService: MovieService

    @get:Rule
    var activityRule = IntentsTestRule(MovieDetailActivity::class.java,
            true,
            false)

    @Before
    fun setUp() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as MockMoviesApp
        val component = app.appComponent as TestAppComponent
        component.inject(this)
    }

    @Test
    fun should_open_imdb_in_browser_when_clicking_on_info() {
        val launchIntent = Intent()
        launchIntent.putExtra(MovieDetailFragment.ARG_MOVIE_ID, "1")   // Batman !

        activityRule.launchActivity(launchIntent)

        onView(withId(R.id.fab))
                .perform(
                        click()
                )

        Intents.intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(Uri.parse("http://www.imdb.com/title/1"))
        ))
    }

}
