package com.dcet.attendance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CheckListActivity extends AppCompatActivity {

    TextView ECE,CSE,IT;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        ECE = (TextView) findViewById(R.id.ECE);
        CSE= (TextView) findViewById(R.id.CSE);

        path= getIntent().getStringExtra("Key");



        IT= (TextView) findViewById(R.id.IT);

        ECE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
