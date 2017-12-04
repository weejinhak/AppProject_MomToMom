package com.mom.momtomom.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mom.momtomom.DTO.DonorInfoDto;
import com.mom.momtomom.R;

import java.util.ArrayList;

/**
 * Created by wee on 2017. 11. 25..
 */

public class RequestListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<String> feedingRommItems;
    private int layout;


    public RequestListAdapter(Context context, int layout, ArrayList<String> feedingRommItems) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.feedingRommItems = feedingRommItems;
        this.layout = layout;
    }

    public void setMessageItems(ArrayList<String> feedingRommItems) {
        this.feedingRommItems = feedingRommItems;
    }

    @Override
    public int getCount() {
        return feedingRommItems.size();
    }

    @Override
    public Object getItem(int position) {
        return feedingRommItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        String feedingRoom = feedingRommItems.get(position);

        TextView feedingRoomName = convertView.findViewById(R.id.my_page_list_request_feedingRoom);
        feedingRoomName.setText(feedingRoom);

        return convertView;
    }
}
