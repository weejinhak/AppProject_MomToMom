package com.mom.momtomom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mom.momtomom.Adapter.BackPressCloseHandler;
import com.mom.momtomom.Adapter.DonorListAdapter;
import com.mom.momtomom.DTO.AgreeInfoDto;
import com.mom.momtomom.DTO.BeneficiaryInfoDto;
import com.mom.momtomom.DTO.DonorInfoDto;

import java.util.ArrayList;

/**
 * Created by wee on 2017. 11. 20..
 */

public class FeedingRoomActivity extends AppCompatActivity implements ValueEventListener {

    private String feedingRoomTitle;
    private BackPressCloseHandler backPressCloseHandler;
    private ArrayList<DonorInfoDto> donorInfoLists;
    private BeneficiaryInfoDto beneficiaryInfoDto;
    private DonorListAdapter donorListAdapter;
    private ListView donorListView;
    private String donorUid;
    private DonorInfoDto donorInfoDto;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {



        //create Object
        backPressCloseHandler = new BackPressCloseHandler(this);
        donorInfoLists = new ArrayList<>();
        beneficiaryInfoDto = new BeneficiaryInfoDto();

        //get Intent
        Intent intent = getIntent();
        latitude = intent.getExtras().getDouble("latitude");
        longitude = intent.getExtras().getDouble("longitude");
        feedingRoomTitle = intent.getExtras().getString("feedingRoomTitle");

        //get ID
        final TextView feedRoomTitle = findViewById(R.id.feedingRoom_layout_textView_feedingRoomName);
        final TextView feedRoomAddress=findViewById(R.id.feedingRoom_layout_textView_feedingRoomAddress);

        Button add_Donor_Button = findViewById(R.id.feedingRoom_layout_Button_donorAdd_Button);
        Button roadSearch_Button = findViewById(R.id.feedingRoom_layout_Button_roadSearch_Button);

        donorListView = findViewById(R.id.donor_layout_donor_listView);
        feedRoomTitle.setText(feedingRoomTitle);
        feedRoomAddress.setText(feedingRoomTitle);

        System.out.println(donorInfoLists.size());

        donorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DonorUid", donorInfoLists.get(position).getDonorUid());
                donorUid = donorInfoLists.get(position).getDonorUid();
                donorInfoDto = donorInfoLists.get(position);

                Intent intent = new Intent(getApplicationContext(), ViewDonorActivity.class);
                intent.putExtra("donorUid", donorUid);
                intent.putExtra("donorInfoDto", donorInfoDto);
                intent.putExtra("feedingRoomTitle", feedingRoomTitle);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                startActivity(intent);

            }
        });

        add_Donor_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddDonorActivity.class);
                intent.putExtra("feedingRoomTitle", feedingRoomTitle);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                startActivity(intent);
                finish();
            }
        });
        roadSearch_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        dataSnapshot.getValue();

        //로그인한사람정보
        String userName = (String) dataSnapshot.child("users").child(getUid()).child("name").getValue();
        String userPhoneNum = (String) dataSnapshot.child("users").child(getUid()).child("phoneNumber").getValue();
        beneficiaryInfoDto.setBeneficiaryName(userName);
        beneficiaryInfoDto.setBeneficiaryPhoneNumber(userPhoneNum);

        //수유실에 맞는 기부자들 정보
        for (DataSnapshot fileSnapshot : dataSnapshot.child("FeedingRoom").child("Donor").child(feedingRoomTitle).getChildren()) {
            DonorInfoDto donorInfoDto = fileSnapshot.getValue(DonorInfoDto.class);
            donorInfoLists.add(donorInfoDto);
        }

        //List Adapter
        donorListAdapter = new DonorListAdapter(this, R.layout.activity_feeding_room_item, donorInfoLists);
        donorListView.setAdapter(donorListAdapter);
    }


    @Override
    public void onCancelled(DatabaseError databaseError) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference().addValueEventListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseDatabase.getInstance().getReference().removeEventListener(this);
    }

    @NonNull
    private String getUid() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return currentUser.getUid();
    }

    @Override
    public void onBackPressed() {
        finish();
        backPressCloseHandler.onBackPressed();
    }
}