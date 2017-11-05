package com.mom.momtomom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by wee on 2017. 11. 5..
 */

public class SelectActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selelct);

        //FireBase auth&database
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        //Dto
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.email=auth.getCurrentUser().getEmail();
        //button
        Button logout_button = findViewById(R.id.select_layout_logout_temporary_button_);
        ImageButton findFeedingRoomButton = findViewById(R.id.select_layout_findFeedingRoom_imgButton);

        //firebase_get
        String uid=auth.getCurrentUser().getUid();
        //firebase_set
        database.getReference().child("users").child(uid).setValue(userInfoDto);

        //clickEvent
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                finish();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        findFeedingRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("눌림");
                Intent intent= new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
