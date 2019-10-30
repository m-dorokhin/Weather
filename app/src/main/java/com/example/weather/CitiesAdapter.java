package com.example.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.weather.data.local.CitiesDao;
import com.example.weather.data.local.City;

import java.util.ArrayList;
import java.util.List;

public class CitiesAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 10;

    private final Context context;
    private final CitiesDao citiesDao;
    private List<City> results;

    public CitiesAdapter(Context context, CitiesDao citiesDao) {
        this.context = context;
        this.citiesDao = citiesDao;
        results = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public City getItem(int index) {
        return results.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        City city = getItem(position);

        ((TextView) convertView.findViewById(android.R.id.text1))
                .setText(String.format("%s (%s)", city.name, city.country));

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<City> cities = findCities(constraint.toString());
                    // Assign the data to the FilterResults
                    filterResults.values = cities;
                    filterResults.count = cities.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    CitiesAdapter.this.results = (List<City>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};

        return filter;
    }

    /**
     * Returns a search result for the given book title.
     */
    private List<City> findCities(String bookTitle) {
        return citiesDao.searchCities("%" + bookTitle + "%", bookTitle);
    }
}
