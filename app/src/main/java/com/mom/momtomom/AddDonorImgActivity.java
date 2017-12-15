package com.mom.momtomom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mom.momtomom.DTO.DonorInfoDto;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;

/**
 * Created by wee on 2017. 12. 9..
 */

public class AddDonorImgActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private DonorInfoDto donorInfoDto;
    private String feedingRoomTitle;
    private ImageView addProfileImageView;
    private String uid;
    private Bitmap imgBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor_img);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        addProfileImageView = findViewById(R.id.add_donor_profile_image_view);

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

        addProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(false)
                        .start(AddDonorImgActivity.this, PhotoPicker.REQUEST_CODE);
                //.start를 하게 되면 이미지를 고르는 화면으로 넘어가고 고르고 DONE을 누르면 그 결과를 onActivityResult라는 함수에서 받게 됨
                //여기서 결과라는 것은 고른 이미지 데이터를 칭함.
            }
        });

        allAddDonor_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                donorInfoDto.setDonorMyInfo(myInfoEditText.getText().toString());

                mDatabase.getReference().child("users").child(uid).child("Donor").child(feedingRoomTitle).push().setValue(donorInfoDto);
                mDatabase.getReference().child("FeedingRoom").child("Donor").child(feedingRoomTitle).push().setValue(donorInfoDto);

                //TODO: firebase Storage에 이미지파일 업로드하기 (참고:https://firebase.google.com/docs/storage/android/upload-files?hl=ko)
                //firebase database에 올리는것과 비슷
                //이미지 파일의 경로를 이용해서 업로드 할 수 있고, bitmap으로도 업로드 할 수 있음.

                Intent intent = new Intent(getApplicationContext(), FeedingRoomActivity.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("feedingRoomTitle", feedingRoomTitle);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                //데이터는 고른 사진들에 대한 경로의 list임.
                //여기서는 사진을 한장만 고르므로 photos.get(0)만 사용
                //ex) photos.get(0) => /storage/3336-6331/DCIM/Camera/20171214_232716.jpg
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

                //이미지 파일의 경로를 비트맵으로 변환에서 이미지뷰에 띄움
                imgBitmap = BitmapFactory.decodeFile(photos.get(0));
                addProfileImageView.setImageBitmap(imgBitmap);
            }
        }
    }
}
