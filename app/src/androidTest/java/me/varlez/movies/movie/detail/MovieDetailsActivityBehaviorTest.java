package me.varlez.movies.movie.detail;

import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import me.varlez.movies.MockMoviesApp;
import me.varlez.movies.R;
import me.varlez.movies.common.RecyclerViewAssertions;
import me.varlez.movies.common.di.TestComponent;
import me.varlez.movies.common.rest.MovieService;
import me.varlez.movies.movie.list.view.MovieListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Behavior test of our {@link me.varlez.movies.movie.detail.MovieDetailActivity}.
 */
@RunWith(AndroidJUnit4.class)
public class MovieDetailsActivityBehaviorTest {

    @Inject
    MovieService movieService;

    @Rule
    public IntentsTestRule<MovieDetailActivity> activityRule = new IntentsTestRule<>(MovieDetailActivity.class,
            true,
            false);

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MockMoviesApp app
                = (MockMoviesApp) instrumentation.getTargetContext().getApplicationContext();
        TestComponent component = (TestComponent) app.getMoviesComponent();
        component.inject(this);
    }

    @Test
    public void should_open_imdb_in_browser_when_clicking_on_info() {
        Intent launchIntent = new Intent();
        launchIntent.putExtra(MovieDetailFragment.ARG_MOVIE_ID, "1");   // Batman !

        activityRule.launchActivity(launchIntent);

        onView(withId(R.id.fab))
                .perform(
                        click()
                );

        Intents.intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(Uri.parse("http://www.imdb.com/title/1"))
        ));
    }

}
