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
    public void checkActivityNotNull() {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveCorrectAppName() {
        String hello = activity.getResources().getString(R.string.app_name);
        assertEquals(hello,"BBM");
    }

    @Test
    public void testNormalFlow() {
        AsyncTask<Context, Integer, CollectionResponseString> asyncTask = new EndpointsAsyncTask();
        assertEquals(asyncTask.getStatus(),AsyncTask.Status.PENDING);
        asyncTask.execute(activity);
        assertEquals(asyncTask.getStatus(),AsyncTask.Status.RUNNING);
        Robolectric.getBackgroundThreadScheduler().unPause();
        assertEquals(asyncTask.getStatus(),AsyncTask.Status.FINISHED);
    }

    @Test
    public void shouldActivityStartedOnMenuItem() {
        // The intent we expect to be launched when a user clicks on the button "Breweries Map"
        Intent expectedIntent = new Intent(activity, MapsActivity.class);
        MenuItem menuItem = new RoboMenuItem(R.id.action_brewerymap);
        activity.onOptionsItemSelected(menuItem);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }
}
