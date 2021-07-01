package com.dcet.attendance;

import android.content.Intent;
import android.icu.util.MeasureUnit;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    TextView TakeAtta,CheckList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TakeAtta = (TextView) findViewById(R.id.TakeAttandance);
        CheckList= (TextView) findViewById(R.id.CheckList);


        TakeAtta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MenuActivity.this,MainActivity.class);
                intent.putExtra("Change",0);
                startActivity(intent);
            }
        });

        CheckList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MenuActivity.this,MainActivity.class);
                intent.putExtra("Change",2);
                startActivity(intent);
            }
        });
    }
}
