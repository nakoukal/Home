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
public class EventAdapter extends ArrayAdapter<EventObject> {

    public EventAdapter(Context context, ArrayList<EventObject> temperatures) {
        super(context, 0, temperatures);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        EventObject eo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_event, parent, false);
        }
        // Lookup view for data population
        TextView tvIP = (TextView) convertView.findViewById(R.id.tvIp);
        TextView tvDev = (TextView) convertView.findViewById(R.id.tvDevice);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        TextView tvBit = (TextView) convertView.findViewById(R.id.tvBit);
        TextView tvVal = (TextView) convertView.findViewById(R.id.tvVal);

        // Populate the data into the template view using the data object
        tvDate.setText(eo.GetDate().toString());
        tvIP.setText(eo.GetIP().toString());
        tvDev.setText(eo.GetDev().toString());
        tvBit.setText(eo.GetBit().toString());
        tvVal.setText(eo.GetValue().toString());

        // Return the completed view to render on screen
        return convertView;
    }
}
