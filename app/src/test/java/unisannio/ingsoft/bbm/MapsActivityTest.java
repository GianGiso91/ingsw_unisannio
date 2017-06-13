package unisannio.ingsoft.bbm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.Implementation;

import unisannio.ingsoft.bbm.backend.beerApi.model.Beer;
import unisannio.ingsoft.bbm.backend.breweryApi.model.CollectionResponseBrewery;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;
import static org.robolectric.util.FragmentTestUtil.startFragment;

/**
 * Created by Luca on 10/06/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MapsActivityTest {

    private MapsActivity activity;

    @Before
    public void setUp() throws Exception {

        activity = Robolectric.buildActivity(MapsActivity.class)
                .create()
                .resume()
                .get();

    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(activity);
    }


}
