package com.grability.apps.Categoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.grability.apps.R;

import java.util.List;

/**
 * Created by ana on 08/11/2016.
 */

public class CategoriaListAdapter extends ArrayAdapter<Categoria> {

    public CategoriaListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CategoriaListAdapter(Context context, int resource, List<Categoria> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // // Get the data item for this position
        Categoria categoria = getItem(position);

        if (convertView == null) {
            // Check if an existing view is being reused, otherwise inflate the view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.categoria_list_item, null);
        }

        if (categoria != null) {
            TextView textView1 = (TextView)convertView.findViewById(R.id.categoria);
            textView1.setText(categoria.getLabel());
            convertView.setTag(categoria.getId());
        }
        // Return the completed view to render on screen
        return convertView;
    }
}