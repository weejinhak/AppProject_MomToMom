package com.mom.momtomom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wee on 2017. 11. 20..
 */

public class FeedingRoomActivity extends FragmentActivity {

    private String feedingRoomTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeding_room);


        Intent intent = getIntent();
        double latitude = intent.getExtras().getDouble("latitude");
        double longitude = intent.getExtras().getDouble("longitude");

        feedingRoomTitle = intent.getExtras().getString("feedingRoomTitle");
        System.out.println("intent Data \n" + latitude + "\n" + longitude + "\n" + feedingRoomTitle);

        TextView feedRoomTitle = findViewById(R.id.feedingRoom_textView_title);
        feedRoomTitle.setText(feedingRoomTitle);

        Button add_Donor_Beneficiary_Button = findViewById(R.id.add_Donor_Beneficiary_button);

        add_Donor_Beneficiary_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
    }


    void show() {
        final List<String> ListItems = new ArrayList<>();
        ListItems.add("기부자");
        ListItems.add("수혜자");

        final CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("등록 유형 선택");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                String selectedText = items[pos].toString();
                Toast.makeText(getApplicationContext(), selectedText, Toast.LENGTH_SHORT).show();
                if (selectedText.equals("기부자")) {
                    Intent intent = new Intent(getApplicationContext(), addDonorBeneficialryActivitiy.class);
                    intent.putExtra("기부자",selectedText);
                    startActivity(intent);
                } else if (selectedText.equals("수혜자")) {
                    Intent intent = new Intent(getApplicationContext(), addDonorBeneficialryActivitiy.class);
                    intent.putExtra("수혜자",selectedText);
                    startActivity(intent);
                }
            }
        });
        builder.show();

    }
}
