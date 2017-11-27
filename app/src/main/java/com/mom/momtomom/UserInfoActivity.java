package com.mom.momtomom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mom.momtomom.DTO.UserInfoDto;

/**
 * Created by wee on 2017. 11. 6..
 */

public class UserInfoActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private UserInfoDto userInfoDto;
    private FirebaseDatabase mDatabase;
    private String uid;
    private String email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);


        //FireBase auth&database
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        Button userInfoOk_button = findViewById(R.id.userinfo_layout_Button_infoOk_button);
        final EditText editNickName = findViewById(R.id.userinfo_layout_text_nickName);
        final EditText editName = findViewById(R.id.userinfo_layout_text_name);
        final EditText editAge = findViewById(R.id.userinfo_layout_text_age);
        final EditText editPhoneNumber= findViewById(R.id.userinfo_layout_text_phoneNumber);

        userInfoDto = new UserInfoDto();
        uid = auth.getCurrentUser().getUid();
        email = auth.getCurrentUser().getEmail();

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
    }
}
