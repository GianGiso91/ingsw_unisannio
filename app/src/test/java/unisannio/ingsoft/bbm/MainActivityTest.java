package unisannio.ingsoft.bbm;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.MenuItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;

import unisannio.ingsoft.bbm.backend.beerApi.model.CollectionResponseString;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Antonio on 20/05/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {
    private MainActivity activity;

    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()

        activity = Robolectric.setupActivity(MainActivity.class);
        Robolectric.getBackgroundThreadScheduler().pause();
        Robolectric.getForegroundThreadScheduler().pause();

    }


    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveCorrectAppName() throws Exception {
        String hello = activity.getResources().getString(R.string.app_name);
        assertEquals(hello,"BBM");
    }

    @Test
    public void testNormalFlow() throws Exception {
        AsyncTask<Context, Integer, CollectionResponseString> asyncTask = new EndpointsAsyncTask();
        assertThat(asyncTask.getStatus()).isEqualTo(AsyncTask.Status.PENDING);
        asyncTask.execute(activity);
        assertThat(asyncTask.getStatus()).isEqualTo(AsyncTask.Status.RUNNING);
        Robolectric.getBackgroundThreadScheduler().unPause();
        assertThat(asyncTask.getStatus()).isEqualTo(AsyncTask.Status.FINISHED);
    }

    @Test
    public void shouldActivityStartedOnView() {
        activity.findViewById(R.id.text_View_Id_Beer).performClick();
        // The intent we expect to be launched when a user clicks on the button
        Intent expectedIntent = new Intent(activity, InfoBeerActivity.class);
        // An Android "Activity" doesn't expose a way to find out about activities it launches
        // Robolectric's "ShadowActivity" keeps track of all launched activities and exposes this information
        // through the "getNextStartedActivity" method.
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        // Determine if two intents are the same for the purposes of intent resolution (filtering).
        // That is, if their action, data, type, class, and categories are the same. This does
        // not compare any extra data included in the intents
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void shouldActivityStartedOnMenuItem() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        // The intent we expect to be launched when a user clicks on the button "Breweries Map"
        Intent expectedIntent = new Intent(activity, MapsActivity.class);
        MenuItem menuItem = new RoboMenuItem(R.id.action_brewerymap);
        activity.onOptionsItemSelected(menuItem);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

}
