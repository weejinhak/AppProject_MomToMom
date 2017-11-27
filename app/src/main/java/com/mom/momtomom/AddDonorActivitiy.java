package com.mom.momtomom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.mom.momtomom.DTO.DonorInfoDto;

/**
 * Created by wee on 2017. 11. 20..
 */

public class AddDonorActivitiy extends AppCompatActivity{

    private DonorInfoDto donorInfoDto;
    private FirebaseAuth mAuth;
    private String uid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor);

        Intent intent = getIntent();
        mAuth=FirebaseAuth.getInstance();
        uid=mAuth.getCurrentUser().getUid();

        final String feedingRoom = intent.getExtras().getString("feedingRoomTitle");
        final EditText donorName= findViewById(R.id.addDonor_layout_editText_name);
        final EditText donorAdress= findViewById(R.id.addDonor_layout_editText_residence);
        final EditText donorDeliveryDate= findViewById(R.id.addDonor_layout_editText_calving_date);
        final EditText donorEmail=findViewById(R.id.addDonor_layout_editText_email);
        final EditText donorPhoneNumber=findViewById(R.id.addDonor_layout_editText_phoneNumber);
        final Button addDonorNextButton=findViewById(R.id.addDonor_layout_button_nextButton);

        donorInfoDto = new DonorInfoDto();

        addDonorNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donorInfoDto.setDonorName(donorName.getText().toString());
                donorInfoDto.setDonorAdress(donorAdress.getText().toString());
                donorInfoDto.setDonorDeliveryDate(donorDeliveryDate.getText().toString());
                donorInfoDto.setDonorEmail(donorEmail.getText().toString());
                donorInfoDto.setDonorPhoneNumber(Integer.parseInt(donorPhoneNumber.getText().toString()));
                donorInfoDto.setDonorUid(uid);

                Intent intent= new Intent(getApplicationContext(),DonorCheckListActivity.class);
                intent.putExtra("donorInfoDto",donorInfoDto);
                intent.putExtra("feedingRoomTitle",feedingRoom);
                startActivity(intent);
                finish();

            }
        });


    }
}
