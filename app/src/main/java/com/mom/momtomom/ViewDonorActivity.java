package com.mom.momtomom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mom.momtomom.DTO.AgreeInfoDto;
import com.mom.momtomom.DTO.BeneficiaryInfoDto;
import com.mom.momtomom.DTO.DonorInfoDto;

/**
 * Created by wee on 2017. 12. 11..
 */

public class ViewDonorActivity extends AppCompatActivity implements ValueEventListener {

    private FirebaseDatabase mDatabase;
    private BeneficiaryInfoDto beneficiaryInfoDto;
    private String feedingRoomTitle;
    private String donorUid;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_donor);

        beneficiaryInfoDto = new BeneficiaryInfoDto();
        //firebase
        mDatabase = FirebaseDatabase.getInstance();


        Intent intent = getIntent();
        DonorInfoDto donorInfoDto = (DonorInfoDto) intent.getSerializableExtra("donorInfoDto");
        donorUid = intent.getStringExtra("donorUid");
        latitude = intent.getExtras().getDouble("latitude");
        longitude = intent.getExtras().getDouble("longitude");
        feedingRoomTitle = intent.getStringExtra("feedingRoomTitle");

        TextView selfInfo_textView = findViewById(R.id.view_Donor_layout_donorSelfInfo_textView);
        TextView deliveryDate_textView = findViewById(R.id.view_Donor_layout_deliveryDate_textView);
        TextView birthReport_textView = findViewById(R.id.view_Donor_layout_birthReport_textView);
        TextView bloodReport_textView = findViewById(R.id.view_Donor_layout_bloodReport_textView);
        TextView donorName_textView= findViewById(R.id.view_beneficiary_layout_textView_DonorName);

        Button addDonor_Button = findViewById(R.id.view_Donor_layout_DonorAdd_Button);

        selfInfo_textView.setText(donorInfoDto.getDonorMyInfo());
        deliveryDate_textView.setText(donorInfoDto.getDonorDeliveryDate());
        donorName_textView.setText(donorInfoDto.getDonorName());
        AgreeInfoDto agreeInfoDto = donorInfoDto.getAgreeInfoDto();

        if (agreeInfoDto.isAgree1()) {
            birthReport_textView.setText("소유");
        }
        if (agreeInfoDto.isAgree2()) {
            bloodReport_textView.setText("소유");
        }

        addDonor_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(donorUid);
            }
        });


    }

    private void showDialog(final String donorUid) {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        final EditText editText = new EditText(this);

        alt_bld.setView(editText);
        editText.setHint(R.string.dialog_hint_text);

        alt_bld.setMessage("기부자에게 수혜 요청을 하시겠습니까?").setCancelable(
                false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (getUid().equals(donorUid)) {
                            Toast.makeText(getApplicationContext(), "본인에게는 요청 불가합니다.", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        } else {

                            beneficiaryInfoDto.setBeneficiaryMessage(editText.getText().toString());
                            mDatabase.getReference().child("users").child(donorUid).child("receive").push().setValue(beneficiaryInfoDto);
                            mDatabase.getReference().child("users").child(getUid()).child("request").push().setValue(feedingRoomTitle);

                            Toast.makeText(getApplicationContext(), "요청 완료", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                            startActivity(intent);
                            finish();
                            dialog.cancel();
                        }
                    }
                }).setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();
        // Title for AlertDialog
        alert.setTitle("요청보내기");
        // Icon for AlertDialog
        alert.setIcon(R.drawable.login_layout_logo_img);
        alert.show();
    }

    @NonNull
    private String getUid() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return currentUser.getUid();
    }


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        dataSnapshot.getValue();

        //로그인한사람정보
        String userName = (String) dataSnapshot.child("users").child(getUid()).child("name").getValue();
        String userPhoneNum = (String) dataSnapshot.child("users").child(getUid()).child("phoneNumber").getValue();
        beneficiaryInfoDto.setBeneficiaryName(userName);
        beneficiaryInfoDto.setBeneficiaryPhoneNumber(userPhoneNum);

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), FeedingRoomActivity.class);
        intent.putExtra("feedingRoomTitle", feedingRoomTitle);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);

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

}
