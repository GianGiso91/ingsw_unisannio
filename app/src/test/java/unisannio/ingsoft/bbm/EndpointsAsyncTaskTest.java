package unisannio.ingsoft.bbm;

/**
 * Created by gianluca on 23/05/2017.
 */

import android.content.Context;
import android.os.AsyncTask;

import junit.framework.TestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import unisannio.ingsoft.bbm.backend.beerApi.model.CollectionResponseString;

public class EndpointsAsyncTaskTest extends TestCase {
    private CountDownLatch signal;
    private MainActivity mainActivity;



    protected void setUp() throws Exception {
        super.setUp();

        signal = new CountDownLatch(1);
        mainActivity = new MainActivity();
    }

    public void testEndpointsAsyncTaskTest() throws Throwable {
       final AsyncTask<Context, Integer, CollectionResponseString> dummyTask = new AsyncTask<Context, Integer, CollectionResponseString>() {
           @Override
           protected CollectionResponseString doInBackground(Context... contexts) {
                //
               return null;
           }

           @Override
           protected void onPostExecute(CollectionResponseString collectionResponseString) {
               super.onPostExecute(collectionResponseString);

               signal.countDown();
           }
       };

        // Execute the async task on the UI thread! THIS IS KEY!
        mainActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                dummyTask.execute();
            }
        });

    /* The testing thread will wait here until the UI thread releases it
     * above with the countDown() or 30 seconds passes and it times out.
     */
    signal.await(30, TimeUnit.SECONDS);

    // The task is done, and now you can assert some things!
    assertTrue("Happiness", true);
}

    protected void tearDown() throws Exception {
        super.tearDown();
    }


}


