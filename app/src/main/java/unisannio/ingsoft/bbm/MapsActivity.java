package unisannio.ingsoft.bbm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
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
    private Circle c;

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng bruxelles = new LatLng(50.85034,4.35171);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bruxelles,6));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                showDialog(latLng);
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker mark) {
                final LatLng latLng = mark.getPosition();
                mark.showInfoWindow();
                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setMessage("Do you also want to search for breweries from here?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showDialog(latLng);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alert.show();
                return true;
            }
        });


        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.dialog_info_brewery,null);
                TextView tv = (TextView) v.findViewById(R.id.beer_brewery);
                tv.setText(tv.getText()+marker.getTitle());
                TextView tv1 = (TextView) v.findViewById(R.id.brewey_info);
                tv1.setText(marker.getSnippet());
                return v;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String result=marker.getSnippet();
                int newLineIndex = result.indexOf("\n");
                final int URL_START = 12;
                String url=result.substring(URL_START,newLineIndex);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
    }


    public void drawRadius(LatLng latLng,int radius) {
        if (c != null)
            c.remove();
        c = mMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(radius)
                .strokeColor(Color.RED));
    }

    public void showDialog(final LatLng latLng){
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        builder.setTitle(R.string.title_radius_dialog).
                setItems(R.array.radius_list, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ListView lw = ((AlertDialog)dialog).getListView();
                        String radius= lw.getItemAtPosition(which).toString();
                        switch (radius) {
                            case ("10 km"):
                                drawRadius(latLng,10000);
                                break;
                            case ("20 km"):
                                drawRadius(latLng,20000);
                                break;
                            case ("30 km"):
                                drawRadius(latLng,30000);
                                break;
                            case ("40 km"):
                                drawRadius(latLng,40000);
                                break;

                            default:
                                break;
                        }
                    }

                });
        builder.create().show();
    }


    protected Marker createMarker(GeoPt coordinate, String title,String snippet) {
        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(coordinate.getLatitude(),coordinate.getLongitude()))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet));
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
            StringBuilder snippets = new StringBuilder();
            snippets.append("WebAddress: "+ b.getWebaddress()+"\n");
            snippets.append("Beers: "+ b.getBeers()+"\n");
            snippets.append("Location: "+b.getPlace()+"\n");
            String info = snippets.toString();
            ((MapsActivity) context).createMarker(b.getGeopt(),b.getIdbrewery(),info);

        }
    }
}