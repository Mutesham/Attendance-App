package com.dcet.attendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
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
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button Submit;
    Spinner Branch, Semester, Subject, Section;

    String[] Details={"","","",""};

    DatabaseReference reference;
    FirebaseAuth auth;
    String TeacherName;
    String date;

    int Change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Submit= (Button) findViewById(R.id.Submit);
        Branch = (Spinner) findViewById(R.id.Branch);
        Semester = (Spinner) findViewById(R.id.Semester);
        Subject = (Spinner) findViewById(R.id.Subject);
        Section = (Spinner) findViewById(R.id.Section);

        reference= FirebaseDatabase.getInstance().getReference();
        auth= FirebaseAuth.getInstance();
        Change= getIntent().getIntExtra("Change",0);
        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        Details[3]="A";

         //Toast.makeText(getApplicationContext(),"C "+Change,Toast.LENGTH_SHORT).show();


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseUser FUser= auth.getCurrentUser();




                //reference.child("Attendance").child("Users").child(""+FUser.getUid()).child("Branch").setValue(""+Details[0]);
              //  reference.child("Attendance").child("Users").child(""+FUser.getUid()).child("Semester").setValue(""+Details[1]);
              //  reference.child("Attendance").child("Users").child(""+FUser.getUid()).child("Subject").setValue(""+Details[2]);
              //  reference.child("Attendance").child("Users").child(""+FUser.getUid()).child("Section").setValue(""+Details[3]);


                Toast.makeText(getApplicationContext(),""+Details[0]+" "+Details[1]+" "+Details[2]+" "+Details[3]+" ",Toast.LENGTH_SHORT).show();
                String Path=Details[0]+"_"+Details[1]+"_"+Details[2]+"_"+Details[3];

                if(Change==5)
                {
                    Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                    intent.putExtra("Key", Path);
                    startActivity(intent);
                    finish();
                }
                if(Change==2)
                {
                    Intent intent = new Intent(MainActivity.this, CheckAttandanceActivity.class);
                    intent.putExtra("Key", Path);
                    startActivity(intent);
                    finish();
                }
                if(Change==0)
                {

                  // reference.child("Attendance").child("Sheet").child(""+Path).child(""+date).child("Teacher").setValue(String.class);
                    Intent intent = new Intent(MainActivity.this, AttendanceActivity.class);
                    intent.putExtra("Key", Path);
                    startActivity(intent);
                    finish();
                }
               // reference.child("Mallre").setValue("Hyderabad");
            }
        });

        Branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String Txt= adapterView.getItemAtPosition(i).toString();

               // Toast.makeText(getApplicationContext(),""+Txt,Toast.LENGTH_SHORT).show();
                Details[0]=Txt;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String Txt= adapterView.getItemAtPosition(i).toString();

                // Toast.makeText(getApplicationContext(),""+Txt,Toast.LENGTH_SHORT).show();
                Details[1]=Txt;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String Txt= adapterView.getItemAtPosition(i).toString();

                // Toast.makeText(getApplicationContext(),""+Txt,Toast.LENGTH_SHORT).show();
               Details[2]=Txt;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String Txt= adapterView.getItemAtPosition(i).toString();

                // Toast.makeText(getApplicationContext(),""+Txt,Toast.LENGTH_SHORT).show();
                Details[3]=Txt;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

                TeacherName=dataSnapshot.child("Attendance").child("Users").child(""+User.getUid()).child("FirstName").getValue(String.class);
               // setTitle("Attandance taken By  "+Name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
