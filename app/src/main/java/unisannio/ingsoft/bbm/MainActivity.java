package unisannio.ingsoft.bbm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.List;

import unisannio.ingsoft.bbm.backend.beerApi.BeerApi;
import unisannio.ingsoft.bbm.backend.beerApi.model.CollectionResponseString;

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new EndpointsAsyncTask().execute(this);


        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long id) {
                TextView v = (TextView) view.findViewById(R.id.textViewIdBeer);
                Intent intent = new Intent(MainActivity.this, InfoBeerActivity.class);
                intent.putExtra("Beer", v.getText());
                startActivity(intent);
            }
        };

        ListView listView = (ListView)findViewById(R.id.listView_beer);
        listView.setOnItemClickListener(clickListener);

    }
}

class EndpointsAsyncTask extends AsyncTask<Context, Integer, CollectionResponseString> {
    private static BeerApi myApiService = null;
    private Context context;


    @Override
    protected CollectionResponseString doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            BeerApi.Builder builder = new BeerApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
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
            return myApiService.listId().execute();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(CollectionResponseString result) {
        List<String> beers = result.getItems();
        ListView listView = (ListView)((MainActivity) context).findViewById(R.id.listView_beer);
        BeerListAdapter listBeerAdapter = new BeerListAdapter((MainActivity) context, R.layout.beer_row_item, beers);
        listView.setAdapter(listBeerAdapter);
    }
}

