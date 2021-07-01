package com.dcet.attendance;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {
    EditText Username, Password;
    TextView Submit,Register,Admin;
    FirebaseAuth auth;
    ProgressDialog dialog;

    int LogI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Username= (EditText) findViewById(R.id.UserName);
        Password= (EditText) findViewById(R.id.Password);

        Submit = (TextView) findViewById(R.id.Submit);
        Register= (TextView) findViewById(R.id.Register);

        Admin= (TextView) findViewById(R.id.Admin);
        LogI=getIntent().getIntExtra("LogIn",0);

        auth= FirebaseAuth.getInstance();
        dialog=new ProgressDialog(this);

        if(LogI==1)
        {
            Admin.setVisibility(View.INVISIBLE);
            setTitle("Admin Login");
            Register.setVisibility(View.INVISIBLE);
            Username.setText("admin@gmail.com");
            Password.setText("CMR321");

        }
        else
        {
            Admin.setVisibility(View.VISIBLE);
            setTitle("Login");
        }
        //Toast.makeText(getApplicationContext(),"LOh "+LogI,Toast.LENGTH_SHORT).show();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(LogI==0)
                {
                    SignIn();
                }
                else
                {
                    SignIn2();
                }





            }
        });

        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent= new Intent(LoginActivity.this,MainActivity.class);
               // intent.putExtra("Change",5);
               // startActivity(intent);

                Intent intent= new Intent(LoginActivity.this,LoginActivity.class);
                 intent.putExtra("LogIn",1);
                 startActivity(intent);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });
    }

    private void SignIn()
    {

        dialog.setMessage("Getting data from server ");

          dialog.show();
        String User= Username.getText().toString();
        String Pass= Password.getText().toString();
        auth.signInWithEmailAndPassword(User,Pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Not success", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(LoginActivity.this,MenuActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }

            }
        });

    }

    private void SignIn2()
    {

        String User= Username.getText().toString();
        String Pass= Password.getText().toString();


        if(User.equals("admin@gmail.com") && Pass.equals("CMR321"))
        {
            Intent intent= new Intent(LoginActivity.this,MainActivity.class);
             intent.putExtra("Change",5);
             startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Wrong UserID and Password",Toast.LENGTH_SHORT).show();
        }

    }
}
