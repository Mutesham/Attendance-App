package com.dcet.attendance;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddStudentActivity extends AppCompatActivity {

    EditText FirstName,Email,Phone,Roll;

    DatabaseReference reference;
    FirebaseAuth auth;

    String date;
    TextView SignUp;
    String Path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        FirstName = (EditText) findViewById(R.id.FirstName);

        Email = (EditText) findViewById(R.id.Email);
        Phone = (EditText) findViewById(R.id.Phone);
        SignUp= (TextView) findViewById(R.id.Register);
        Roll = (EditText) findViewById(R.id.Roll);

        reference= FirebaseDatabase.getInstance().getReference();

        auth=FirebaseAuth.getInstance();

        Path= getIntent().getStringExtra("Key");

        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Name=FirstName.getText().toString();


                String Email1=Email.getText().toString();
                String Roll1=Roll.getText().toString();
                String Mob=Phone.getText().toString();

                reference.child("Attendance").child("Sheet").child(""+Path).child("Enable").setValue("1");

                reference.child("Attendance").child("Sheet").child(""+Path).child("StudentForm").child("" + Roll1).child("Email").setValue(""+Email1);

                reference.child("Attendance").child("Sheet").child(""+Path).child("StudentForm").child("" + Roll1).child("Number").setValue(""+Mob);

                reference.child("Attendance").child("Sheet").child(""+Path).child("StudentForm").child("" + Roll1).child("Name").setValue(""+Name);

                Toast.makeText(getApplicationContext(),"Successfully added",Toast.LENGTH_SHORT).show();
            }
        });


    }

}
