package unisannio.ingsoft.bbm;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import unisannio.ingsoft.bbm.backend.beerApi.BeerApi;
import unisannio.ingsoft.bbm.backend.beerApi.model.Beer;

public class InfoBeerActivity extends Activity {

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_beer);

        layout = (LinearLayout) findViewById(R.id.progressbar_view);

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
        ((InfoBeerActivity) context).layout.setVisibility(View.GONE);
        TextView v = (TextView) ((InfoBeerActivity) context).findViewById(R.id.beer_name);
        v.setText(v.getText() + result.getIdbeer());
        TextView v1 = (TextView) ((InfoBeerActivity) context).findViewById(R.id.beer_type);
        v1.setText(v1.getText() + result.getType());
        TextView v2= (TextView) ((InfoBeerActivity) context).findViewById(R.id.beer_fermentation);
         v2.setText(v2.getText() + result.getFermentation());
        TextView v3 = (TextView) ((InfoBeerActivity) context).findViewById(R.id.beer_volume);
        v3.setText(v3.getText() + Float.toString(result.getVolume()) + "%");
        TextView v4 = (TextView) ((InfoBeerActivity) context).findViewById(R.id.beer_color);
        String color = "NaN";
        if(result.getColor()!= null)
            color = result.getColor();
        v4.setText(v4.getText() + color);
        TextView v5 = (TextView) ((InfoBeerActivity) context).findViewById(R.id.beer_ibu);
        String ibu = "NaN";
        if(result.getIbu() != 0)
            ibu = Integer.toString(result.getIbu());
        v5.setText(v5.getText() + ibu);
        TextView v6 = (TextView) ((InfoBeerActivity) context).findViewById(R.id.beer_glass);
        v6.setText(v6.getText() + result.getGlass());
    }
}