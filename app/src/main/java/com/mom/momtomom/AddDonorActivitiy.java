package com.mom.momtomom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wee on 2017. 11. 20..
 */

public class AddDonorActivitiy extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor);


        Intent intent = getIntent();
        String feedingRoom = intent.getExtras().getString("feedingRoom");
    }
}
