package com.nakoukal.radek.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by uidv7359 on 19.9.2016.
 */
public class TempAdapter extends ArrayAdapter<TempObject> {

    public TempAdapter(Context context, ArrayList<TempObject> temperatures) {
        super(context, 0, temperatures);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TempObject to = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_temp, parent, false);
        }
        // Lookup view for data population
        TextView tvDes = (TextView) convertView.findViewById(R.id.tvDes);
        TextView tvTemp = (TextView) convertView.findViewById(R.id.tvTemp);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);

        // Populate the data into the template view using the data object
        tvDes.setText(to.GetDes().toString());
        tvTemp.setText(to.GetTemp().toString());
        tvDate.setText(to.GetDate().toString());

        // Return the completed view to render on screen
        return convertView;
    }
}
