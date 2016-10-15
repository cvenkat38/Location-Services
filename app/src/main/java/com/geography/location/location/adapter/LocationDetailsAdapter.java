package com.geography.location.location.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geography.location.location.R;
import com.geography.location.location.model.LocationDetails;

import java.util.List;

/**
 * Created by Admin on 15-10-2016.
 * Adapter  for recyclerview to show time,latitude,longitude
 */



public class LocationDetailsAdapter extends RecyclerView.Adapter<LocationDetailsAdapter.MyViewHolder> {

    private Context context;

    private String TAG=LocationDetailsAdapter.class.getSimpleName();
    private List<LocationDetails> locationDetailsList;

    public LocationDetailsAdapter(List<LocationDetails> locationDetailsList,Context context) {
        this.locationDetailsList = locationDetailsList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_details_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d(TAG,"in bind view holder ");
        LocationDetails locationDetails=locationDetailsList.get(position);
        holder.latitude.setText(context.getResources().getString(R.string.latitude)+" -"+locationDetails.getLatitude());
        holder.longitude.setText(context.getResources().getString(R.string.longitude)+" - "+locationDetails.getLongitude());
        holder.time.setText(context.getResources().getString(R.string.longitude)+" - "+locationDetails.getTime());

    }

    @Override
    public int getItemCount() {
        return locationDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView time, latitude, longitude;

        public MyViewHolder(View view) {
            super(view);
            time = (TextView) view.findViewById(R.id.time);
            latitude = (TextView) view.findViewById(R.id.latitude);
            longitude = (TextView) view.findViewById(R.id.longitude);
        }
    }
}
