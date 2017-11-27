package com.mom.momtomom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mom.momtomom.DTO.CheckListDto;
import com.mom.momtomom.DTO.DonorInfoDto;

/**
 * Created by wee on 2017. 11. 21..
 */

public class DonorCheckListActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private int currentIndex = 0;
    private TextView checkListTextView;
    private Button addDonorButton;
    private DonorInfoDto donorInfoDto;
    private CheckListDto checkListDto;
    private String uid;


    private int[] checkListText = {
            R.string.checkList_Text1, R.string.checkList_Text2, R.string.checkList_Text3,
            R.string.checkList_Text4, R.string.checkList_Text5, R.string.checkList_Text6,
            R.string.checkList_Text7, R.string.checkList_Text8, R.string.checkList_Text9
    };

    private boolean[] checkList_Yes_or_No = {
            false, false, false, false, false, false, false, false, false};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        Intent intent = getIntent();
        donorInfoDto = new DonorInfoDto();
        checkListDto = new CheckListDto();

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance();


        donorInfoDto = (DonorInfoDto) intent.getSerializableExtra("donorInfoDto");
        final String feedingRoomTitle = intent.getStringExtra("feedingRoomTitle");

        Log.d("DonorCheckList_Log1", String.valueOf(donorInfoDto));
        Log.d("DonorCheckList_Log2", feedingRoomTitle);

        checkListTextView = findViewById(R.id.checkList_layout_textView);
        final CheckBox noCheckBox = findViewById(R.id.checkList_layout_noCheckBox);
        final CheckBox yesCheckBox = findViewById(R.id.checkList_layout_yesCheckBox);
        Button preButton = findViewById(R.id.checkList_layout_beforeButton);
        Button nextButton = findViewById(R.id.checkList_layout_nextButton);
        addDonorButton = findViewById(R.id.checklist_layout_checkListOk_Button);
        addDonorButton.setVisibility(View.INVISIBLE);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(currentIndex);
                if (yesCheckBox.isChecked() || noCheckBox.isChecked()) {
                    currentIndex = (currentIndex + 1) % checkListText.length;
                    updateCheckList();
                    createCheckOkButton(addDonorButton);
                    if (yesCheckBox.isChecked()) {
                        isCheckBoxCheck(checkListDto, true);
                        noCheckBox.setChecked(false);
                    } else if (noCheckBox.isChecked()) {
                        isCheckBoxCheck(checkListDto, false);
                        yesCheckBox.setChecked(false);
                    }
                    yesCheckBox.setChecked(false);
                    noCheckBox.setChecked(false);
                } else
                    Toast.makeText(getApplicationContext(), "체크를 부탁드려요", Toast.LENGTH_SHORT).show();

            }
        });
        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yesCheckBox.isChecked() || noCheckBox.isChecked()) {
                    currentIndex = (currentIndex - 1) % checkListText.length;
                    if (currentIndex < 0)
                        currentIndex = checkListText.length + currentIndex;
                    updateCheckList();
                } else
                    Toast.makeText(getApplicationContext(), "체크를 부탁드려요", Toast.LENGTH_SHORT).show();

            }
        });

        //질문바꾸기
        updateCheckList();

        //db넣기 버튼
        addDonorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donorInfoDto.setCheckListDto(checkListDto);
                mDatabase.getReference().child("users").child(uid).child("Donor").child(feedingRoomTitle).push().setValue(donorInfoDto);
                mDatabase.getReference().child("FeedingRoom").child("Donor").child(feedingRoomTitle).push().setValue(donorInfoDto);
                Intent intent = new Intent(getApplicationContext(),FeedingRoomActivity.class);
                intent.putExtra("feedingRoomTitle",feedingRoomTitle);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateCheckList() {
        int questionResourceId = checkListText[currentIndex];
        checkListTextView.setText(questionResourceId);
    }

    private void createCheckOkButton(Button button) {
        if (currentIndex == 8) {
            button.setVisibility(View.VISIBLE);
        }
    }

    private void isCheckBoxCheck(CheckListDto checkListDto, boolean check) {
        Log.d("isCheckBoxCheck", String.valueOf(checkListDto));
        Log.d("isCheckBoxCheck", String.valueOf(check));
        if (currentIndex == 0) {
            checkListDto.setQuestion1(check);
        } else if (currentIndex == 1) {
            checkListDto.setQuestion2(check);
        } else if (currentIndex == 2) {
            checkListDto.setQuestion3(check);
        } else if (currentIndex == 3) {
            checkListDto.setQuestion4(check);
        } else if (currentIndex == 4) {
            checkListDto.setQuestion5(check);
        } else if (currentIndex == 5) {
            checkListDto.setQuestion6(check);
        } else if (currentIndex == 6) {
            checkListDto.setQuestion7(check);
        } else if (currentIndex == 7) {
            checkListDto.setQuestion8(check);
        } else if (currentIndex == 8) {
            checkListDto.setQuestion9(check);
        }
    }

}
