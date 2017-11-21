package com.mom.momtomom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mom.momtomom.DTO.ChcekList;

import java.util.ArrayList;

/**
 * Created by wee on 2017. 11. 21..
 */

public class DonorCheckListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_1);

        TextView checkListTextView=findViewById(R.id.checkList_layout_textView);
        CheckBox noCheckBox=findViewById(R.id.checkList_layout_noCheckBox);
        CheckBox yesCheckBox=findViewById(R.id.checkList_layout_yesCheckBox);
        Button beforeButton=findViewById(R.id.checkList_layout_beforeButton);
        Button nextButton=findViewById(R.id.checkList_layout_nextButton);

    }
}
