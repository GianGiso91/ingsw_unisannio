package unisannio.ingsoft.bbm;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import unisannio.ingsoft.bbm.backend.beerApi.model.Beer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;



@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class InfoBeerActivityTest {

        private InfoBeerActivity activity;

        @Before
        public void setup() {
            Intent intent = new Intent(RuntimeEnvironment.application, InfoBeerActivity.class);
            intent.putExtra("Beer", "Chimay Red");
            activity = Robolectric.buildActivity(InfoBeerActivity.class).withIntent(intent).create().get();
            Robolectric.getBackgroundThreadScheduler().pause();
            Robolectric.getForegroundThreadScheduler().pause();

        }

        @Test
        public void checkActivityNotNull() {
            assertNotNull(activity);
        }

        @Test
        public void testNormalFlow() {
            AsyncTask<android.util.Pair<Context, String>, Integer, Beer> asyncTask = new InfoBeerAsyncTask();
            assertEquals(asyncTask.getStatus(),AsyncTask.Status.PENDING);
            asyncTask.execute(new Pair(activity,"ciao"));
            assertEquals(asyncTask.getStatus(),AsyncTask.Status.RUNNING);
            Robolectric.getBackgroundThreadScheduler().unPause();
            assertEquals(asyncTask.getStatus(),AsyncTask.Status.FINISHED);
        }
}



