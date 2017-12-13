package com.mom.momtomom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mom.momtomom.DTO.DonorInfoDto;

/**
 * Created by wee on 2017. 12. 9..
 */

public class AddDonorImgActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private DonorInfoDto donorInfoDto;
    private String feedingRoomTitle;

    private String uid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor_img);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();


        //get Intent
        Intent intent = getIntent();

        final double latitude = intent.getExtras().getDouble("latitude");
        final double longitude = intent.getExtras().getDouble("longitude");

        donorInfoDto = (DonorInfoDto) intent.getSerializableExtra("donorInfoDto");
        feedingRoomTitle = intent.getStringExtra("feedingRoomTitle");
        uid = mAuth.getCurrentUser().getUid();

        Log.d("dto", String.valueOf(donorInfoDto));

        //get Id
        final EditText myInfoEditText = findViewById(R.id.add_donor_img_layout_editText_myInfo);
        Button allAddDonor_Button = findViewById(R.id.add_donor_imgLayout_final_addButton);


        System.out.println(donorInfoDto);
        System.out.println(feedingRoomTitle);

        allAddDonor_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                donorInfoDto.setDonorMyInfo(myInfoEditText.getText().toString());

                mDatabase.getReference().child("users").child(uid).child("Donor").child(feedingRoomTitle).push().setValue(donorInfoDto);
                mDatabase.getReference().child("FeedingRoom").child("Donor").child(feedingRoomTitle).push().setValue(donorInfoDto);

                Intent intent = new Intent(getApplicationContext(), FeedingRoomActivity.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("feedingRoomTitle", feedingRoomTitle);
                startActivity(intent);
                finish();
            }
        });


    }
}
