package unisannio.ingsoft.bbm;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import java.util.ArrayList;
import java.util.List;
import unisannio.ingsoft.bbm.databinding.BeerRowItemBinding;

public class BeerListAdapter extends BaseAdapter implements Filterable {

  List<String> data;
  List<String> stringFilterList;
  ValueFilter valueFilter;
  private LayoutInflater inflater;

  /**
   * Adapter to display list of beers.
   */
  public BeerListAdapter(List cancelType) {
    super();
    data = cancelType;
    stringFilterList = cancelType;
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public String getItem(int position) {
    return data.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, final ViewGroup parent) {
    if (inflater == null) {
      inflater = (LayoutInflater) parent.getContext()
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    BeerRowItemBinding rowItemBinding =
        DataBindingUtil.inflate(inflater, R.layout.beer_row_item, parent, false);
    rowItemBinding.textViewIdBeer.setText(data.get(position));
    return rowItemBinding.textViewIdBeer;
  }

  @Override
  public Filter getFilter() {
    if (valueFilter == null) {
      valueFilter = new ValueFilter();
    }
    return valueFilter;
  }

  private class ValueFilter extends Filter {
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
      FilterResults results = new FilterResults();
      if (constraint != null && constraint.length() > 0) {
        List<String> filterList = new ArrayList<>();
        for (int i = 0; i < stringFilterList.size(); i++) {
          if (stringFilterList.get(i).toUpperCase()
              .contains(constraint.toString().toUpperCase())) {
            filterList.add(stringFilterList.get(i));
          }
        }
        results.count = filterList.size();
        results.values = filterList;
      } else {
        results.count = stringFilterList.size();
        results.values = stringFilterList;
      }
      return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
      data = (List<String>) results.values;
      notifyDataSetChanged();
    }
  }
}