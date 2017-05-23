package unisannio.ingsoft.bbm;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import unisannio.ingsoft.bbm.backend.beerApi.BeerApi;
import unisannio.ingsoft.bbm.backend.beerApi.model.Beer;

public class InfoBeerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_beer);
        String beerid = getIntent().getStringExtra("Beer");
        new InfoBeerAsyncTask().execute(new Pair(this,beerid));
    }

}

class InfoBeerAsyncTask extends AsyncTask<Pair<Context, String>, Integer, Beer> {
    private static BeerApi myApiService = null;
    private Context context;

    @Override
    protected Beer doInBackground(Pair<Context, String>... params) {
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

        context = params[0].first;
        String idbeer = params[0].second;

        try {
            return myApiService.get(idbeer).execute();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Beer result) {
        TextView v = (TextView) ((InfoBeerActivity) context).findViewById(R.id.beer_name);
        v.setText(v.getText() + result.getIdbeer());
        TextView v1 = (TextView) ((InfoBeerActivity) context).findViewById(R.id.beer_type);
        v1.setText(v1.getText() + result.getType());
        TextView v2 = (TextView) ((InfoBeerActivity) context).findViewById(R.id.beer_brewery);
        v2.setText(v2.getText() + result.getBrewery());
        TextView v3 = (TextView) ((InfoBeerActivity) context).findViewById(R.id.beer_webaddress);
        v3.setText(v3.getText() + result.getWebaddress());
        TextView v4 = (TextView) ((InfoBeerActivity) context).findViewById(R.id.beer_fermentation);
        v4.setText(v4.getText() + result.getFermentation());
        TextView v5 = (TextView) ((InfoBeerActivity) context).findViewById(R.id.beer_volume);
        v5.setText(v5.getText() + Float.toString(result.getVolume()) + "%");
        TextView v6 = (TextView) ((InfoBeerActivity) context).findViewById(R.id.beer_color);
        String color = (result.getColor() == null) ? "NaN" : result.getColor();
        v6.setText(v6.getText() + color);
        TextView v7 = (TextView) ((InfoBeerActivity) context).findViewById(R.id.beer_ibu);
        String ibu = "Nan";
        if(result.getIbu() != 0)
            ibu = Integer.toString(result.getIbu());
        v7.setText(v7.getText() + ibu);
        TextView v8 = (TextView) ((InfoBeerActivity) context).findViewById(R.id.beer_glass);
        v8.setText(v8.getText() + result.getGlass());
    }
}