package unisannio.ingsoft.bbm;

import android.content.Context;
import android.os.AsyncTask;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import unisannio.ingsoft.bbm.backend.beerApi.model.CollectionResponseString;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
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
}
