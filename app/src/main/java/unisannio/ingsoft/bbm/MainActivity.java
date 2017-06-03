package unisannio.ingsoft.bbm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.List;

import unisannio.ingsoft.bbm.backend.beerApi.BeerApi;
import unisannio.ingsoft.bbm.backend.beerApi.model.CollectionResponseString;
import unisannio.ingsoft.bbm.databinding.ActivityMainBinding;

public class MainActivity extends Activity{
    ActivityMainBinding activityMainBinding;

    public LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        activityMainBinding.search.setActivated(true);
        activityMainBinding.search.setQueryHint("Type your keyword here");
        activityMainBinding.search.onActionViewExpanded();
        activityMainBinding.search.setIconified(false);
        activityMainBinding.search.clearFocus();

        layout = (LinearLayout) findViewById(R.id.progressbar_view);
        new EndpointsAsyncTask().execute(this);





        new EndpointsAsyncTask().execute(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_brewerymap:
                //Put the intent to the map activity here
                Context context = getApplicationContext();
                CharSequence text = "Should open MapActivity";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_act_menu, menu);
        return true;
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
      


        LinearLayout pbv = (LinearLayout) ((MainActivity) context).findViewById(R.id.progressbar_view);
        pbv.setVisibility(View.GONE);
      
        List<String> beers = result.getItems();
        ListView listView = (ListView) ((MainActivity) context).findViewById(R.id.list_View_beer);
        final BeerListAdapter listBeerAdapter = new BeerListAdapter(beers);
        listView.setAdapter(listBeerAdapter);
        SearchView sv = (SearchView) ((MainActivity) context).findViewById(R.id.search);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listBeerAdapter.getFilter().filter(newText);
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                TextView v = (TextView) view.findViewById(R.id.text_View_Id_Beer);
                Intent intent = new Intent(((MainActivity) context), InfoBeerActivity.class);
                intent.putExtra("Beer", v.getText());
                
                ((MainActivity) context).startActivity(intent);
            }
        });
    }
}
