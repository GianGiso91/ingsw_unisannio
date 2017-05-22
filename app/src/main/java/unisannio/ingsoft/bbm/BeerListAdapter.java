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

public class BeerListAdapter extends ArrayAdapter<String>{

    public BeerListAdapter (Context ctx, int resourceId, List<String> objects) {
        super(ctx, resourceId, objects);
    }


    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}


