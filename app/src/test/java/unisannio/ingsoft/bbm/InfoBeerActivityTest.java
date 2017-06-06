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

/**
 * Created by gianluca on 04/06/2017.
 */

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
        public void checkActivityNotNull() throws Exception {
            assertNotNull(activity);
        }

        @Test
        public void shouldHaveCorrectName() throws Exception {
            String hello = activity.getResources().getString(R.string.title_activity_info_beer);
            assertEquals(hello,"Info on the selected beer");
        }

        @Test
        public void testNormalFlow() throws Exception {
            AsyncTask<android.util.Pair<Context, String>, Integer, Beer> asyncTask = new InfoBeerAsyncTask();
            assertEquals(asyncTask.getStatus(),AsyncTask.Status.PENDING);
            asyncTask.execute(new Pair(activity,"ciao"));
            assertEquals(asyncTask.getStatus(),AsyncTask.Status.RUNNING);
            Robolectric.getBackgroundThreadScheduler().unPause();
            assertEquals(asyncTask.getStatus(),AsyncTask.Status.FINISHED);
        }


}



