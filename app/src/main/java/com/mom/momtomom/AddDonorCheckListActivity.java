package com.mom.momtomom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mom.momtomom.DTO.AgreeInfoDto;
import com.mom.momtomom.DTO.CheckListDto;
import com.mom.momtomom.DTO.DonorInfoDto;

/**
 * Created by wee on 2017. 11. 21..
 */

public class AddDonorCheckListActivity extends AppCompatActivity {

    private int currentIndex = 0;
    private TextView checkListTextView;
    private Button addDonorButton;
    private CheckBox yesCheckBox;
    private CheckBox noCheckBox;

    private DonorInfoDto donorInfoDto;
    private CheckListDto checkListDto;
    private AgreeInfoDto agreeInfoDto;
    private String feedingRoomTitle;


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
        agreeInfoDto = new AgreeInfoDto();


        donorInfoDto = (DonorInfoDto) intent.getSerializableExtra("donorInfoDto");
        agreeInfoDto = (AgreeInfoDto) intent.getSerializableExtra("donorAgreeInfo");
        Log.d("dto", String.valueOf(donorInfoDto));
        feedingRoomTitle = intent.getStringExtra("feedingRoomTitle");
        final double latitude = intent.getExtras().getDouble("latitude");
        final double longitude = intent.getExtras().getDouble("longitude");


        checkListTextView = findViewById(R.id.checkList_layout_textView);
        noCheckBox = findViewById(R.id.checkList_layout_noCheckBox);
        yesCheckBox = findViewById(R.id.checkList_layout_yesCheckBox);
        Button preButton = findViewById(R.id.checkList_layout_beforeButton);
        Button nextButton = findViewById(R.id.checkList_layout_nextButton);
        addDonorButton = findViewById(R.id.checklist_layout_checkListOk_Button);
        addDonorButton.setVisibility(View.INVISIBLE);

        yesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    noCheckBox.setChecked(false);
                    feedPossibleSet();
                    isCheckBoxCheck(checkListDto, true);
                }
            }
        });
        noCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    yesCheckBox.setChecked(false);
                    feedPossibleSet();
                    isCheckBoxCheck(checkListDto, false);
                }
            }
        });


        //다음버튼
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yesCheckBox.isChecked() || noCheckBox.isChecked()) {
                    currentIndex = (currentIndex + 1) % checkListText.length;
                    updateCheckList();
                    if (yesCheckBox.isChecked()) {
                        noCheckBox.setChecked(false);
                        createCheckOkButton(addDonorButton);
                    } else if (noCheckBox.isChecked()) {
                        yesCheckBox.setChecked(false);
                        createCheckOkButton(addDonorButton);
                    }
                    yesCheckBox.setChecked(false);
                    noCheckBox.setChecked(false);
                } else
                    Toast.makeText(getApplicationContext(), "체크를 부탁드려요", Toast.LENGTH_SHORT).show();
            }
        });

        //이전버튼
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

        //수유적합여부 판단 및 다음 페이지로 넘어가기 버튼
        addDonorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donorInfoDto.setCheckListDto(checkListDto);
                donorInfoDto.setAgreeInfoDto(agreeInfoDto);
                if (isFeedPossibleCheck()) {
                    Toast.makeText(getApplicationContext(), "현재 기증에 적합한 상태입니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), AddDonorImgActivity.class);
                    intent.putExtra("donorInfoDto", donorInfoDto);
                    intent.putExtra("feedingRoomTitle", feedingRoomTitle);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "현재 기증에 적합하지 않은 상태입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //질문내용바꾸기
    private void updateCheckList() {
        int questionResourceId = checkListText[currentIndex];
        checkListTextView.setText(questionResourceId);
    }

    //적합여부판단버튼생성
    private void createCheckOkButton(Button button) {
        if (currentIndex == 8) {
            button.setVisibility(View.VISIBLE);
        }
    }

    //Dto에 질문 넣기
    private void isCheckBoxCheck(CheckListDto checkListDto, boolean check) {
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

    //적합여부 가능 배열에 넣기
    private void feedPossibleSet() {
        checkList_Yes_or_No[currentIndex] = yesCheckBox.isChecked();
    }

    //배열의 적합 여부가 맞을시 return true
    private boolean isFeedPossibleCheck() {
        return checkList_Yes_or_No[0] && checkList_Yes_or_No[1] && !checkList_Yes_or_No[2] && !checkList_Yes_or_No[3] &&
                !checkList_Yes_or_No[5] && !checkList_Yes_or_No[6] && checkList_Yes_or_No[7] && checkList_Yes_or_No[8];
    }

}
