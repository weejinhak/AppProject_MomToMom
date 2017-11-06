package com.mom.momtomom;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

/**
 * Created by wee on 2017. 11. 6..
 */

public class UserInfoActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    public UserInfoDto userInfoDto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);


        //FireBase auth&database
        auth = FirebaseAuth.getInstance();

        Button userInfoOk_button= findViewById(R.id.userinfo_layout_Button_infoOk_button);
        final EditText editNickName=findViewById(R.id.userinfo_layout_text_nickName);
        final EditText editName=findViewById(R.id.userinfo_layout_text_name);
        final EditText editAge=findViewById(R.id.userinfo_layout_text_age);

         userInfoDto = new UserInfoDto();

        userInfoOk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfoDto.setAge(editAge.getText().toString());
                userInfoDto.setEmail(auth.getCurrentUser().getEmail().toString());
                userInfoDto.setName(editName.getText().toString());
                userInfoDto.setNickName(editNickName.getText().toString());
                Toast.makeText(getApplicationContext(),"정보입력완료",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getApplicationContext(),SelectActivity.class);
                intent.putExtra("userInfo", userInfoDto);
                startActivity(intent);
                finish();
            }
        });
    }
}
