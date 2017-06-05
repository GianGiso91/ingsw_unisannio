package unisannio.ingsoft.bbm;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.List;


import unisannio.ingsoft.bbm.backend.breweryApi.BreweryApi;
import unisannio.ingsoft.bbm.backend.breweryApi.model.CollectionResponseBrewery;
import unisannio.ingsoft.bbm.backend.breweryApi.model.GeoPt;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        new EndAsyncTask().execute(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng bruxelles=new LatLng(50.85034,4.35171);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bruxelles,6));
    }

    protected Marker createMarker(GeoPt coordinate, String title) {

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(coordinate.getLatitude(),coordinate.getLongitude()))
                .anchor(0.5f, 0.5f)
                .title(title)
        );
    }

}
class EndAsyncTask extends AsyncTask<Context, Integer, CollectionResponseBrewery> {
    private static BreweryApi myApiService = null;
    private Context context;


    @Override
    protected CollectionResponseBrewery doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            BreweryApi.Builder builder = new BreweryApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("https://bebemap-167519.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver.

            myApiService = builder.build();
        }

        context = params[0];

        try {
            return myApiService.list().execute();
        } catch (IOException e) {
            return null;
        }
    }


    @Override
    protected void onPostExecute(CollectionResponseBrewery result) {

        List<unisannio.ingsoft.bbm.backend.breweryApi.model.Brewery> brewery = result.getItems();
        for(unisannio.ingsoft.bbm.backend.breweryApi.model.Brewery b:brewery){
            ((MapsActivity) context).createMarker(b.getGeopt(),b.getIdbrewery());
        }
    }
}

