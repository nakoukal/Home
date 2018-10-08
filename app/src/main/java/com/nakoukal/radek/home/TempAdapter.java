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

    private static final int TYPE_DIVIDER = 0;
    private static final int TYPE_ROW = 1;
    private static int type = 0;

    public TempAdapter(Context context, ArrayList<TempObject> temperatures) {
        super(context, 0, temperatures);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(position == 0)
            type = 0;
        else
            type=1;

        if (convertView == null) {
            switch (type) {
                case TYPE_ROW:
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_temp, parent, false);
                    break;
                case TYPE_DIVIDER:
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_temp_header, parent, false);
                    break;
            }
        }

        TextView tvDes,tvTemp,tvReq,tvStat,tvDate;

        switch (type) {
            case TYPE_ROW:
                // Get the data item for this position
                TempObject to = getItem(position);
                // Lookup view for data population
                tvDes = (TextView) convertView.findViewById(R.id.tvDes);
                tvTemp = (TextView) convertView.findViewById(R.id.tvTemp);
                tvReq = (TextView) convertView.findViewById(R.id.tvReq);
                tvStat = (TextView) convertView.findViewById(R.id.tvStat);
                tvDate = (TextView) convertView.findViewById(R.id.tvDate);

                // Populate the data into the template view using the data object
                tvDes.setText(to.GetDes().toString());
                tvTemp.setText(to.GetTemp().toString());
                tvReq.setText(to.GetReq().toString());
                tvStat.setText(to.GetStat().toString());
                tvDate.setText(to.GetDate().toString());
                break;
            case TYPE_DIVIDER:

                tvDes = (TextView) convertView.findViewById(R.id.thDes);
                tvTemp = (TextView) convertView.findViewById(R.id.thTemp);
                tvReq = (TextView) convertView.findViewById(R.id.thReq);
                tvStat = (TextView) convertView.findViewById(R.id.thStat);
                tvDate = (TextView) convertView.findViewById(R.id.thDate);

                tvDes.setText("DES");
                tvTemp.setText("TEMP");
                tvReq.setText("REQ");
                tvStat.setText("STAT");
                tvDate.setText("DATE");
                break;
        }



        // Return the completed view to render on screen
        return convertView;
    }
}
