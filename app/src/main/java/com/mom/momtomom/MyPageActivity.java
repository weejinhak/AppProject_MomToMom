package com.mom.momtomom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mom.momtomom.Adapter.BackPressCloseHandler;
import com.mom.momtomom.Adapter.DonorListAdapter;
import com.mom.momtomom.Adapter.ReceiveListAdapter;
import com.mom.momtomom.Adapter.RequestListAdapter;
import com.mom.momtomom.DTO.BeneficiaryInfoDto;
import com.mom.momtomom.DTO.DonorInfoDto;

import java.util.ArrayList;

/**
 * Created by wee on 2017. 11. 28..
 */

public class MyPageActivity extends AppCompatActivity implements ValueEventListener {

    private BackPressCloseHandler backPressCloseHandler;

    private ArrayList<String> feedingRoomArrayList;
    private ArrayList<BeneficiaryInfoDto> beneficiaryInfoDtos;
    private ReceiveListAdapter receiveListAdapter;
    private RequestListAdapter requestListAdapter;

    private ListView receiveListView;
    private ListView requestListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        backPressCloseHandler = new BackPressCloseHandler(this);
        feedingRoomArrayList= new ArrayList<>();
        beneficiaryInfoDtos= new ArrayList<>();

        receiveListView = findViewById(R.id.myPage_layout_receive_ListView);
        requestListView = findViewById(R.id.myPage_layout_request_ListView);


    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        dataSnapshot.getValue();

        //자기가 요청한 수유실 정보
        for (DataSnapshot fileSnapshot : dataSnapshot.child("users").child(getUid()).child("request").getChildren()) {
            String feedingRoomName = fileSnapshot.getValue(String.class);
            feedingRoomArrayList.add(feedingRoomName);
            Log.d("feedingRoomName", String.valueOf(feedingRoomName));
        }
        //요청받은 수혜자들 정보
        for (DataSnapshot fileSnapshot : dataSnapshot.child("users").child(getUid()).child("receive").getChildren()) {
            BeneficiaryInfoDto beneficiaryInfoDto = fileSnapshot.getValue(BeneficiaryInfoDto.class);
            beneficiaryInfoDtos.add(beneficiaryInfoDto);
            Log.d("feedingRoombeneficialry", String.valueOf(beneficiaryInfoDto));
        }
        //List Adapter
        receiveListAdapter= new ReceiveListAdapter(this,R.layout.activity_my_page_item_receive,beneficiaryInfoDtos);
        requestListAdapter= new RequestListAdapter(this,R.layout.activity_my_page_item_request,feedingRoomArrayList);
        receiveListView.setAdapter(receiveListAdapter);
        requestListView.setAdapter(requestListAdapter);
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
        backPressCloseHandler.onBackPressed();
    }
}
