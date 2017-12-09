package com.mom.momtomom.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mom.momtomom.DTO.DonorInfoDto;
import com.mom.momtomom.R;

import java.util.ArrayList;

/**
 * Created by wee on 2017. 11. 25..
 */

public class DonorListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<DonorInfoDto> donorInfoItems;
    private int layout;


    public DonorListAdapter(Context context, int layout, ArrayList<DonorInfoDto> donorInfoItems) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.donorInfoItems = donorInfoItems;
        this.layout = layout;
    }

    public void setMessageItems(ArrayList<DonorInfoDto> donorInfoItems) {
        this.donorInfoItems = donorInfoItems;
    }

    @Override
    public int getCount() {
        return donorInfoItems.size();
    }

    @Override
    public Object getItem(int position) {
        return donorInfoItems.get(position).getDonorName();
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

        DonorInfoDto donorInfoItem = donorInfoItems.get(position);

        ImageView imageView = convertView.findViewById(R.id.list_donor_img);
        imageView.setImageResource(R.drawable.donor_item_donor_img);

        TextView donorName = convertView.findViewById(R.id.list_donor_name);
        donorName.setText(donorInfoItem.getDonorName());

        String date = donorInfoItem.getDonorDeliveryDate();
        String[] dateToken = date.split("/");
        String deliveryDate = dateToken[0] + "년" + dateToken[1] + "월" + dateToken[2] + "일 " + "출산";

        TextView donorDelivery = convertView.findViewById(R.id.list_donor_date);
        donorDelivery.setText(deliveryDate);

        if (!donorInfoItem.getCheckListDto().isQuestion2()) {
            TextView donorCertificate = convertView.findViewById(R.id.list_donor_certificate);
            donorCertificate.setText("증명서 소유 X");
        }
        TextView donorCertificate = convertView.findViewById(R.id.list_donor_certificate);
        donorCertificate.setText("증명서 소유");
        donorCertificate.setFocusable(false);

        ImageView requestImageView = convertView.findViewById(R.id.list_donor_request_img);
        requestImageView.setImageResource(R.drawable.donor_item_finger_img);

        return convertView;
    }
}
