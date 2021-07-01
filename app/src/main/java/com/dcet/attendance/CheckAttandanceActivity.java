package com.dcet.attendance;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;


public class CheckAttandanceActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{

    DatabaseReference reference;
    FirebaseAuth auth;
    String path;
    ArrayList<String> RollNumberList = new ArrayList<>();
    ListView listView;
    String[] Roll2;
    ProgressDialog dialog;
    //int year1,month1,day1;
    int year1,month1,day1;
    String DateS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_attandance);
        listView= (ListView) findViewById(R.id.ECEList);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Getting data from server ");

        //dialog.show();
        setTitle("Att");

        reference= FirebaseDatabase.getInstance().getReference();
        auth= FirebaseAuth.getInstance();
        path= getIntent().getStringExtra("Key");
       // path="ECE_Semester 1_Subject1_Section A";
        LoadData();

        LoadAttDate();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String path2=parent.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(),path2,Toast.LENGTH_SHORT).show();

                Intent intent= new Intent(CheckAttandanceActivity.this,FinalAttandanceActivity.class);
                intent.putExtra("path",path);
                intent.putExtra("path2",DateS);
                startActivity(intent);
            }
        });
    }

    private void LoadData()
    {
        reference.child("Attendance").child("Sheet").child(""+path).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items= dataSnapshot.getChildren().iterator();

                long length=dataSnapshot.getChildrenCount();
                String[] Roll= new String[(int)length];
                int i=0;



                while (items.hasNext())

                {
                    DataSnapshot item = items.next();

                    String RollTransfer=item.getKey().toString();

                    Roll[i]=RollTransfer;

                    i++;





                }
                length=length-2;


                if(length>0)
                {
                    Roll2= new String[(int)length];

                    for(int s1=0;s1<length;s1++)
                    {
                        Roll2[s1]=Roll[s1];
                    }
                    ArrayAdapter<String> adapter= new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,Roll2);

                    listView.setAdapter(adapter);
                   // dialog.dismiss();


                }
                else
                {
                    //dialog.dismiss();
                }





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(dayOfMonth<10)
        {

            if(month<9)
            {
                year1=year;
                month1=month+1;
                day1=dayOfMonth;
                DateS="0"+day1+"-"+"0"+month1+"-"+year1;
            }
            else
            {
                year1=year;
                month1=month+1;
                day1=dayOfMonth;
                DateS="0"+day1+"-"+month1+"-"+year1;
            }


        }
        else
        {
            if(month<9)
            {
                year1=year;
                month1=month+1;
                day1=dayOfMonth;
                DateS=day1+"-"+"0"+month1+"-"+year1;
            }
            else
            {
                year1=year;
                month1=month+1;
                day1=dayOfMonth;
                DateS=day1+"-"+month1+"-"+year1;
            }
        }



        Intent intent= new Intent(CheckAttandanceActivity.this,FinalAttandanceActivity.class);
        intent.putExtra("path",path);
        intent.putExtra("path2",DateS);
        startActivity(intent);
       // Toast.makeText(getApplicationContext(),""+DateS,Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    private void LoadAttDate()
    {
        Calendar C=Calendar.getInstance();
        year1 = C.get(Calendar.YEAR);
        month1=C.get(Calendar.MONTH);
        day1=C.get(Calendar.DATE);

        DatePickerDialog dialog= new DatePickerDialog(CheckAttandanceActivity.this,CheckAttandanceActivity.this,year1,month1,day1);

        dialog.show();

    }
}
