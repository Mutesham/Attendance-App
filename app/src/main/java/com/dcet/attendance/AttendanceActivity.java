package com.dcet.attendance;

import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class AttendanceActivity extends AppCompatActivity {

    Button Present,Absent,Complete;
    TextView AttNum,NameGet;

    ProgressDialog pd;

    int Number=0;
    DatabaseReference reference;

    ArrayList<String> list= new ArrayList<>();
    ArrayList<String> list2= new ArrayList<>();

    int count=0;
    int PresentCount=0,AbsentCount=0;
    String path;
    ArrayList<String> RollNumberList= new ArrayList<>();
    ArrayList<String> NameList= new ArrayList<>();


    String date,TeacherName;
    FirebaseAuth auth;

    int C=0;
    String Test;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Present= (Button) findViewById(R.id.Present);

        Absent= (Button) findViewById(R.id.Absent);
        Complete= (Button) findViewById(R.id.Complete);
        Complete.setEnabled(false);

        AttNum= (TextView) findViewById(R.id.AttNum);
        NameGet= (TextView) findViewById(R.id.NameGet);
        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        reference= FirebaseDatabase.getInstance().getReference();
        auth= FirebaseAuth.getInstance();
        path= getIntent().getStringExtra("Key");

        pd = new ProgressDialog(AttendanceActivity.this);
        pd.setMessage("Getting from server....");
        pd.setCancelable(false);
        pd.show();

        CheckData();
        Present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Number<count) {

                    String MyName = "Student" + Number;

                    reference.child("Attendance").child("Sheet").child(""+path).child("StudentForm").child("" + RollNumberList.get(Number)).child("DateWise").child(""+date).setValue("Present");

                    reference.child("Attendance").child("Sheet").child(""+path).child(""+date).child("Attandance").child("" + RollNumberList.get(Number)).setValue("Present");
                    Number++;
                    C++;
                    if(Number<count) {
                        NameGet.setText("" + NameList.get(Number));
                        AttNum.setText("" + RollNumberList.get(Number));
                    }
                    else
                    {
                        AttNum.setText("Finished" );
                        Toast.makeText(getApplicationContext(),"Attandance for all students taken, Press Completed Button",Toast.LENGTH_SHORT).show();
                        Complete.setEnabled(true);

                    }

                   // AttNum.setText("" + getRoll);
                    PresentCount++;
                }
                else
                {
                    AttNum.setText("Finished" );
                    Toast.makeText(getApplicationContext(),"Attandance for all students taken, Press Completed Button",Toast.LENGTH_SHORT).show();

                }

               // RetriveData();
            }
        });

        Absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Number<count) {

                    String MyName = "Student" + Number;

                    reference.child("Attendance").child("Sheet").child(""+path).child("StudentForm").child("" + RollNumberList.get(Number)).child("DateWise").child(""+date).setValue("Absent");

                    reference.child("Attendance").child("Sheet").child(""+path).child(""+date).child("Attandance").child("" + RollNumberList.get(Number)).setValue("Absent");
                    Number++;
                    C++;
                    if(Number<count) {
                        NameGet.setText("" + NameList.get(Number));
                        AttNum.setText("" + RollNumberList.get(Number));
                    }
                    else
                    {
                        AttNum.setText("Finished" );
                        Toast.makeText(getApplicationContext(),"Attandance for all students taken, Press Completed Button",Toast.LENGTH_SHORT).show();
                        Complete.setEnabled(true);

                    }

                    // AttNum.setText("" + getRoll);
                    AbsentCount++;
                }

                else
                {
                    AttNum.setText("Finished" );
                    Toast.makeText(getApplicationContext(),"Attandance for all students taken, Press Completed Button",Toast.LENGTH_SHORT).show();

                }
            }
        });

        Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AttendanceActivity.this,ListActivity.class);
                intent.putExtra("PresentCount",PresentCount);
                intent.putExtra("AbsentCount",AbsentCount);
                intent.putExtra("Key2",path);
                intent.putExtra("Date",date);
                startActivity(intent);
                finish();
            }
        });
    }

    private void LoadData()
    {
        reference.child("Attendance").child("Sheet").child(""+path).child("StudentForm").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items= dataSnapshot.getChildren().iterator();

                while (items.hasNext()) {
                    DataSnapshot item = items.next();

                    String RollTransfer=item.getKey().toString();
                    String NameTransfer=item.child("Name").getValue().toString();

                    RollNumberList.add(RollTransfer);
                    NameList.add(NameTransfer);


                }

                count=NameList.size();
                Toast.makeText(getApplicationContext(),"gfg  "+count,Toast.LENGTH_SHORT).show();

                NameGet.setText(""+NameList.get(0));
                AttNum.setText(""+RollNumberList.get(0));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void LoadTeacher()
    {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                FirebaseUser user= auth.getCurrentUser();

                TeacherName=dataSnapshot.child("Attendance").child("Users").child(""+user.getUid()).child("FirstName").getValue(String.class);

                reference.child("Attendance").child("Sheet").child(""+path).child(""+date).child("Teacher").setValue(""+TeacherName);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void CheckData()
    {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Test= dataSnapshot.child("Attendance").child("Sheet").child(""+path).child("Enable").getValue(String.class);

              //  Test= dataSnapshot.child("Attendance").child("Sheet").child(""+path).child(""+date).child("Teacher").getValue(String.class);
                Toast.makeText(getApplicationContext(),"ef   "+Test,Toast.LENGTH_SHORT).show();
                Test=Test+"";

                String kk=Test;

                if(kk.equals("null"))
                {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(),"No data Found",Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(AttendanceActivity.this);

                    builder.setMessage("Students record not found for this class");
                    builder.setTitle("Students not added");
// Add the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            Intent intent = new Intent(AttendanceActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            // User clicked OK button
                        }
                    });

// Set other dialog properties

// Create the AlertDialog

                    AlertDialog dialog1 = builder.create();
                    dialog1.show();


                }
                else
                {
                    pd.dismiss();


                     LoadTeacher();
                    LoadData();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




}
