package com.example.webprog26.datatask.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.webprog26.datatask.R;
import com.example.webprog26.datatask.models.Island;

import java.util.ArrayList;

/**
 * Created by webprog26 on 16.11.2016.
 */

public class IslandsAdapter extends ArrayAdapter<Island>{

    private Activity mActivity;
    public IslandsAdapter(Activity activity, ArrayList<Island> islands) {
        super(activity, 0, islands);
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Island island = getItem(position);

        if(convertView == null){
            convertView = mActivity.getLayoutInflater().inflate(R.layout.islands_spinner_item, parent, false);
            String islandName = island.getIslandName();
            if(islandName != null){
                ((TextView) convertView.findViewById(R.id.tvIslandName)).setText(island.getIslandName());
            }

        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }


}
