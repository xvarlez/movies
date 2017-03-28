package me.varlez.movies.movie.list;

import android.app.Instrumentation;
import android.content.Intent;
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
import me.varlez.movies.movie.detail.view.MovieDetailFragment;
import me.varlez.movies.movie.list.view.MovieListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Behavior test of our {@link me.varlez.movies.movie.list.view.MovieListActivity}.
 */
@RunWith(AndroidJUnit4.class)
public class MovieListActivityBehaviorTest {
    @Inject
    MovieService movieService;

    @Rule
    public IntentsTestRule<MovieListActivity> activityRule = new IntentsTestRule<>(MovieListActivity.class,
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
    public void should_see_two_movies() {
        activityRule.launchActivity(new Intent());

        onView(withId(R.id.movie_list))
                .check(
                        RecyclerViewAssertions.hasItemsCount(2)
                );
    }

    @Test
    public void should_display_title_and_year() {
        activityRule.launchActivity(new Intent());

        onView(withText("Batman"))
                .check(
                        matches(
                                hasSibling(
                                        withText("2005")
                                )
                        )
                );
    }

    @Test
    public void should_open_details_when_clicking_on_movie() {
        activityRule.launchActivity(new Intent());

        onView(withId(R.id.movie_list))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition(0, click())
                );

        Intents.intended(allOf(
                toPackage("me.varlez.movies"),
                hasExtra(MovieDetailFragment.ARG_MOVIE_ID, "1")
        ));
    }

}
