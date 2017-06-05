package unisannio.ingsoft.bbm;

import android.content.Context;
import android.os.AsyncTask;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import unisannio.ingsoft.bbm.backend.beerApi.model.Beer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by gianluca on 04/06/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class InfoBeerActivityTest {

        private InfoBeerActivity activity;

        @Before
        public void setup() {
            activity = Robolectric.buildActivity(InfoBeerActivity.class).create().get();
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
            assertThat(asyncTask.getStatus()).isEqualTo(AsyncTask.Status.PENDING);
            asyncTask.execute((Runnable) activity);
            assertThat(asyncTask.getStatus()).isEqualTo(AsyncTask.Status.RUNNING);
            Robolectric.getBackgroundThreadScheduler().unPause();
            assertThat(asyncTask.getStatus()).isEqualTo(AsyncTask.Status.FINISHED);
        }


}



