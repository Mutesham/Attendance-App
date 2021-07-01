package com.dcet.attendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> list= new ArrayList<>();
    ArrayList<String> list2= new ArrayList<>();
    DatabaseReference reference;
    int PresentCount=0,AbsentCount=0;
    Button Finish;
    String Path,Date;
    FirebaseAuth auth;
    int count=0;
    String RollAdd,NameAdd,StatusAdd;

    ArrayList<String> RollNumberList= new ArrayList<>();
    ArrayList<String> NameList= new ArrayList<>();
    ArrayList<String> StatusList= new ArrayList<>();
    TextView Present, Absent,Total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView= (ListView) findViewById(R.id.list1);
        Present = (TextView) findViewById(R.id.Present);
        Absent = (TextView) findViewById(R.id.Absent);
        Total = (TextView) findViewById(R.id.Total);
        Finish= (Button) findViewById(R.id.Finish);

        reference= FirebaseDatabase.getInstance().getReference();
        auth= FirebaseAuth.getInstance();
        PresentCount=getIntent().getIntExtra("PresentCount",0);
        AbsentCount=getIntent().getIntExtra("AbsentCount",0);
        Path= getIntent().getStringExtra("Key2");
        Date= getIntent().getStringExtra("Date");

        Present.setText(""+PresentCount);
        Absent.setText(""+AbsentCount);



        LoadData();
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ListActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }


    private void LoadData()
    {
        reference.child("Attendance").child("Sheet").child(""+Path).child("StudentForm").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items= dataSnapshot.getChildren().iterator();

                while (items.hasNext()) {
                    DataSnapshot item = items.next();

                    String RollTransfer=item.getKey().toString();
                    String NameTransfer=item.child("Name").getValue().toString();
                    String StatusTransfer=item.child("DateWise").child(""+Date).getValue().toString();

                    RollNumberList.add(RollTransfer);
                    NameList.add(NameTransfer);
                    StatusList.add(StatusTransfer);


                }

                count=NameList.size();
                Toast.makeText(getApplicationContext(),"gfg  "+count,Toast.LENGTH_SHORT).show();
                Total.setText(""+count);

                MyAdapter adapter= new MyAdapter(getApplicationContext(),RollNumberList,StatusList);

                listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser User= auth.getCurrentUser();

                String Name=dataSnapshot.child("Attendance").child("Users").child(""+User.getUid()).child("FirstName").getValue(String.class);
                setTitle("Attandance taken By  "+Name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
