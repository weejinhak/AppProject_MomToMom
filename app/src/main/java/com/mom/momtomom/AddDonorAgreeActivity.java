package com.mom.momtomom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.mom.momtomom.DTO.AgreeInfoDto;
import com.mom.momtomom.DTO.DonorInfoDto;

/**
 * Created by wee on 2017. 12. 9..
 */

public class AddDonorAgreeActivity extends AppCompatActivity {

    private DonorInfoDto donorInfoDto;
    private AgreeInfoDto agreeInfoDto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor_agree);

        Intent intent = getIntent();
        donorInfoDto = new DonorInfoDto();
        agreeInfoDto = new AgreeInfoDto();

        final double latitude = intent.getExtras().getDouble("latitude");
        final double longitude = intent.getExtras().getDouble("longitude");
        donorInfoDto = (DonorInfoDto) intent.getSerializableExtra("donorInfoDto");
        final String feedingRoomTitle = intent.getStringExtra("feedingRoomTitle");

        //get Id
        TextView nameTextView = findViewById(R.id.addDonor_layout_textView_name);
        TextView addressTextView = findViewById(R.id.addDonor_layout_textView_adress);
        TextView deliveryTextView = findViewById(R.id.addDonor_layout_textView_deliveryDay);
        TextView emailTextView = findViewById(R.id.addDonor_layout_textView_email);
        TextView phoneNumberTextView = findViewById(R.id.addDonor_layout_textView_phoneNumber);
        Button nextButton = findViewById(R.id.add_donor_agree_layout_nextButton);

        final CheckBox agreeCheckBox1 = findViewById(R.id.agreeCheckBox1);
        final CheckBox agreeCheckBox2 = findViewById(R.id.agreeCheckBox2);
        final CheckBox agreeCheckBox3 = findViewById(R.id.agreeCheckBox3);

        nameTextView.setText(donorInfoDto.getDonorName());
        addressTextView.setText(donorInfoDto.getDonorAddress());
        deliveryTextView.setText(donorInfoDto.getDonorDeliveryDate());
        emailTextView.setText(donorInfoDto.getDonorEmail());
        phoneNumberTextView.setText(String.valueOf(donorInfoDto.getDonorPhoneNumber()));


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (agreeCheckBox1.isChecked()) agreeInfoDto.setAgree1(true);
                else agreeInfoDto.setAgree1(false);
                if (agreeCheckBox2.isChecked()) agreeInfoDto.setAgree2(true);
                else agreeInfoDto.setAgree2(false);
                if (agreeCheckBox3.isChecked()) agreeInfoDto.setAgree3(true);
                else agreeInfoDto.setAgree3(false);

                //Validation Check
                if (agreeInfoDto.isAgree1() && agreeInfoDto.isAgree2() && agreeInfoDto.isAgree3()) {
                    System.out.println(agreeInfoDto);
                    Toast.makeText(getApplicationContext(), "동의 완료", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), AddDonorCheckListActivity.class);
                    intent.putExtra("donorInfoDto", donorInfoDto);
                    intent.putExtra("feedingRoomTitle", feedingRoomTitle);
                    intent.putExtra("donorAgreeInfo", agreeInfoDto);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                    startActivity(intent);

                } else {
                    System.out.println(agreeInfoDto);
                    Toast.makeText(getApplicationContext(), "동의를 하지 않으면 기증이 불가합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
