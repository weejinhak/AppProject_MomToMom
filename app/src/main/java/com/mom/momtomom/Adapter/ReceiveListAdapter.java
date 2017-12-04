package com.mom.momtomom.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mom.momtomom.DTO.BeneficiaryInfoDto;
import com.mom.momtomom.DTO.DonorInfoDto;
import com.mom.momtomom.R;

import java.util.ArrayList;

/**
 * Created by wee on 2017. 11. 25..
 */

public class ReceiveListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<BeneficiaryInfoDto> beneficiaryInfoDtos;
    private int layout;


    public ReceiveListAdapter(Context context, int layout, ArrayList<BeneficiaryInfoDto> beneficiaryInfoDtos) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.beneficiaryInfoDtos = beneficiaryInfoDtos;
        this.layout = layout;
    }

    public void setMessageItems(ArrayList<BeneficiaryInfoDto> beneficiaryInfoDtos) {
        this.beneficiaryInfoDtos = beneficiaryInfoDtos;
    }

    @Override
    public int getCount() {
        return beneficiaryInfoDtos.size();
    }

    @Override
    public Object getItem(int position) {
        return beneficiaryInfoDtos.get(position).getBeneficiaryName();
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

        BeneficiaryInfoDto beneficiaryInfoDto = beneficiaryInfoDtos.get(position);

        ImageView imageView = convertView.findViewById(R.id.my_page_list_donor_img);
        imageView.setImageResource(R.drawable.donor_item_donor_img);

        TextView donorName = convertView.findViewById(R.id.my_page_list_beneficiary_name);
        donorName.setText(beneficiaryInfoDto.getBeneficiaryName());

        TextView donorDelivery = convertView.findViewById(R.id.my_page_list_beneficiary_phoneNumber);
        donorDelivery.setText(beneficiaryInfoDto.getBeneficiaryPhoneNumber());

        return convertView;
    }
}
