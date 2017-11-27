package com.mom.momtomom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by wee on 2017. 11. 3..
 */

public class EmailJoinActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText join_editTextEmail;
    private EditText join_editTextPassword;
    private EditText join_editTextPasswordOk;
    private String email;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        //firebase
        mAuth = FirebaseAuth.getInstance();

        join_editTextEmail=findViewById(R.id.emailJoin_layout_text_name);
        join_editTextPassword=findViewById(R.id.emailJoin_layout_editText_password);
        join_editTextPasswordOk =findViewById(R.id.emailJoin_layout_editText_passWordOk);

        Button joinOkButton=findViewById(R.id.emailJoin_layout_Button_infoOk_button);

        joinOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Validation Check
                if (join_editTextEmail.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "이메일을 입력하세요!", Toast.LENGTH_SHORT).show();
                    join_editTextEmail.requestFocus();
                    return;
                }
                if (join_editTextPassword.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    join_editTextPassword.requestFocus();
                    return;
                }
                if (!join_editTextPasswordOk.getText().toString().equals(join_editTextPasswordOk.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다!!", Toast.LENGTH_SHORT).show();
                    join_editTextPasswordOk.setText("");
                    join_editTextPasswordOk.setText("");
                    join_editTextPasswordOk.requestFocus();
                    return;
                }

                createUser(join_editTextEmail.getText().toString(),join_editTextPassword.getText().toString());
            }
        });

    }

    private void createUser(final String email, final String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(EmailJoinActivity.this,"회원가입성공",Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(getApplicationContext(),UserInfoActivity.class);
                            intent.putExtra("userEmail",join_editTextEmail.getText().toString());
                            startActivity(intent);
                            finish();
                        }

                        // ...
                    }
                });
    }

}
