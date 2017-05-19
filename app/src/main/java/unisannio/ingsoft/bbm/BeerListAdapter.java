package unisannio.ingsoft.bbm;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import unisannio.ingsoft.bbm.backend.beerApi.model.Beer;

/**
 * Created by Luca on 18/05/2017.
 */

public class BeerListAdapter extends ArrayAdapter{

    private int resource;
    private LayoutInflater inflater;
    private Context context;
    private List<Beer>beers;

    public BeerListAdapter ( Context ctx, int resourceId, List<Beer> objects) {

        super( ctx, resourceId, objects );
        this.context = context;
        this.resource = resource;
        this.beers = objects;
    }


    @Override
    public View getView ( int position, View convertView, ViewGroup parent ) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resource, parent, false);
        Beer beer=beers.get(position);
        String beerName=beer.getIdbeer();
        TextView textView = (TextView) rowView.findViewById(R.id.beerName);
        textView.setText(beerName);


        return convertView;
    }
}


