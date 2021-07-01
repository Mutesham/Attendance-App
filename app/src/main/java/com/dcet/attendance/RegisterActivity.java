package com.dcet.attendance;

import android.app.ProgressDialog;

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

public class RegisterActivity extends AppCompatActivity {

    EditText FirstName,LastName,Pasword,ConPasword,Email,Phone;
    DatabaseReference reference;
    FirebaseAuth auth;
    ProgressDialog dialog;
    TextView SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirstName = (EditText) findViewById(R.id.FirstName);
        LastName = (EditText) findViewById(R.id.LastName);
        Pasword = (EditText) findViewById(R.id.Password);
        ConPasword = (EditText) findViewById(R.id.Password2);
        Email = (EditText) findViewById(R.id.Email);
        Phone = (EditText) findViewById(R.id.Phone);
        SignUp= (TextView) findViewById(R.id.Register);
        reference= FirebaseDatabase.getInstance().getReference();
        setTitle("Register");
        auth=FirebaseAuth.getInstance();
        dialog=new ProgressDialog(this);
        SignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                dialog.setMessage("Getting data from server ");

                dialog.show();
                CreateUser();
            }
        });


    }

    private void CreateUser()
    {

        String User= Email.getText().toString();
        String Pass= Pasword.getText().toString();
        final String First= FirstName.getText().toString();
        final String Last= LastName.getText().toString();
        final String Phn= Phone.getText().toString();





        auth.createUserWithEmailAndPassword(User,Pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Not success",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            FirebaseUser FUser= auth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                            reference.child("Attendance").child("Users").child(""+FUser.getUid()).child("FirstName").setValue(""+First);
                            reference.child("Attendance").child("Users").child(""+FUser.getUid()).child("LastName").setValue(""+Last);
                            reference.child("Attendance").child("Users").child(""+FUser.getUid()).child("Mobile").setValue(""+Phn);
                            dialog.dismiss();

                            finish();
                        }

                        // ...
                    }
                });

    }
}
