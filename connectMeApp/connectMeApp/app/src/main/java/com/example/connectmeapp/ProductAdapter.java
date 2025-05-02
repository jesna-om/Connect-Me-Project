package com.example.connectmeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.ViewGroup.LayoutParams;

public class ProductAdapter extends ArrayAdapter<String> implements Filterable {

    private Context context;
    private List<String> originalData;
    private List<String> filteredData;
    private Filter filter;

    public ProductAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.context = context;
        this.originalData = new ArrayList<>(items);
        this.filteredData = new ArrayList<>(items);
        getFilter();  // Initialize the filter
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Override
    public String getItem(int position) {
        return filteredData.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        textView.setText(filteredData.get(position));

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new ProductFilter(this, originalData);
        }
        return filter;
    }

    private static class ProductFilter extends Filter {

        private ProductAdapter adapter;
        private List<String> originalData;

        ProductFilter(ProductAdapter adapter, List<String> originalData) {
            this.adapter = adapter;
            this.originalData = originalData;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = originalData;
                results.count = originalData.size();
            } else {
                List<String> filteredList = new ArrayList<>();
                String filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim();

                for (String data : originalData) {
                    if (data.toLowerCase(Locale.getDefault()).contains(filterPattern)) {
                        filteredList.add(data);
                    }
                }

                results.values = filteredList;
                results.count = filteredList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filteredData = (List<String>) results.values;
            adapter.notifyDataSetChanged();
        }
    }
}
