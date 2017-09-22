package com.ocean_soft.aebeid.modern_pentathlon_points_calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeFormatException;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SingleEntry extends AppCompatActivity {

    RadioGroup sportGroup;
    RadioGroup swimmingGroup;
    RadioGroup runningGroup;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_entry);

        sportGroup = (RadioGroup) findViewById(R.id.sportGroup);
        swimmingGroup = (RadioGroup) findViewById(R.id.swimmingGroup);
        runningGroup = (RadioGroup) findViewById(R.id.runningGroup);
        editText = (EditText) findViewById(R.id.editText2);

    }

    public void onRadioButtonClick(View view){
        switch (view.getId()){
            case R.id.swimmingRadio:
                for (int i = 0; i < swimmingGroup.getChildCount(); i++) {
                    swimmingGroup.getChildAt(i).setEnabled(true);
                }
                for (int i = 0; i < runningGroup.getChildCount(); i++) {
                    runningGroup.getChildAt(i).setEnabled(false);
                }
                break;
            case R.id.runningRadio:
                for (int i = 0; i < swimmingGroup.getChildCount(); i++) {
                    swimmingGroup.getChildAt(i).setEnabled(false);
                }
                for (int i = 0; i < runningGroup.getChildCount(); i++) {
                    runningGroup.getChildAt(i).setEnabled(true);
                }
                break;
            default:
                return;
        }
    }

    public void result(View view){
        if (editText.getText().toString().trim().equalsIgnoreCase("")) {
            editText.setError("This field can not be blank");
        }
        else {
            String[] time = editText.getText().toString().split(":");
            int min = Integer.valueOf(time[0]);
            float sec;
            if(time.length == 1){
                sec = 0;
            }else {
                sec = Float.valueOf("0." + time[1]);
            }
            if(sec > 0.59){
                editText.setError("Seconds can't be more than 59");
            }
        }

    }

    @Override
    public void onBackPressed() {
        //return nothing
        return;
    }
}

