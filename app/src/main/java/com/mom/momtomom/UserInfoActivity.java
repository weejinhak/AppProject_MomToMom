package com.mom.momtomom;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mom.momtomom.DTO.UserInfoDto;

import java.io.IOException;

/**
 * Created by wee on 2017. 11. 6..
 */

public class UserInfoActivity extends AppCompatActivity {

    private final static int GALLERY_CODE = 0;
    private final static int READ_EXTERNAL_STORAGE_PERMISSION_RESULT = 0;

    private FirebaseAuth auth;
    private UserInfoDto userInfoDto;
    private FirebaseDatabase mDatabase;
    private String uid;
    private String email;
    private Button btnAddImg;
    private ImageView ivProfileImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        btnAddImg = findViewById(R.id.btn_add_img);
        ivProfileImg = findViewById(R.id.iv_profile_img);


        //FireBase auth&database
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        Button userInfoOk_button = findViewById(R.id.userinfo_layout_Button_infoOk_button);
        final EditText editNickName = findViewById(R.id.userinfo_layout_text_nickName);
        final EditText editName = findViewById(R.id.userinfo_layout_text_name);
        final EditText editAge = findViewById(R.id.userinfo_layout_text_age);
        final EditText editPhoneNumber= findViewById(R.id.userinfo_layout_text_phoneNumber);
        final TextView userEmailTextView=findViewById(R.id.userInfo_layout_textView_userEmail);


        userInfoDto = new UserInfoDto();
        uid = auth.getCurrentUser().getUid();
        email = auth.getCurrentUser().getEmail();

        userEmailTextView.setText(email);


        userInfoOk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Validation Check
                if (editNickName.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력하세요!", Toast.LENGTH_SHORT).show();
                    editNickName.requestFocus();
                    return;
                }
                if (editName.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요!", Toast.LENGTH_SHORT).show();
                    editName.requestFocus();
                    return;
                }
                if (editAge.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "나이 입력하세요!", Toast.LENGTH_SHORT).show();
                    editAge.requestFocus();
                    return;
                }
                if(editPhoneNumber.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(),"전화번호 입력하세요!",Toast.LENGTH_SHORT).show();
                    editPhoneNumber.requestFocus();
                    return;
                }

                //setDto
                userInfoDto.setAge(editAge.getText().toString());
                userInfoDto.setEmail(email);
                userInfoDto.setName(editName.getText().toString());
                userInfoDto.setNickName(editNickName.getText().toString());
                userInfoDto.setPhoneNumber(editPhoneNumber.getText().toString());

                //uid에 맞는 정보 디비 입력
                mDatabase.getReference().child("users").child(uid).setValue(userInfoDto);

                Toast.makeText(getApplicationContext(), "정보입력완료", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
                startActivity(intent);
                finish();
            }
        });

        checkReadExternalStoragePermission();
    }

    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case GALLERY_CODE:
                    sendPicture(data); //갤러리에서 가져오기
                    break;
                default:
                    break;
            }

        }
    }

    private void sendPicture(Intent data) {

        Uri imgUri = data.getData();
        String imagePath = getRealPathFromURI(imgUri); // path 경로

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
        ivProfileImg.setImageBitmap(bitmap);//이미지 뷰에 비트맵 넣기
    }

    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery( contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case READ_EXTERNAL_STORAGE_PERMISSION_RESULT:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    btnAddImg.setOnClickListener(
                            new Button.OnClickListener() {
                                public void onClick(View v) {
                                    selectPhoto();
                                }
                            }
                    );
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    private void checkReadExternalStoragePermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                btnAddImg.setOnClickListener(
                        new Button.OnClickListener() {
                            public void onClick(View v) {
                                selectPhoto();
                            }
                        }
                );
            }else{
                if(shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                    Toast.makeText(this, "App needs to view thumbnails", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION_RESULT);
            }
        }else{
            btnAddImg.setOnClickListener(
                    new Button.OnClickListener() {
                        public void onClick(View v) {
                            selectPhoto();
                        }
                    }
            );
        }
    }


}
