package com.dcet.attendance;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class FinalAttandanceActivity extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseAuth auth;
    String path,path2;
    ArrayList<String> RollNumberList=new ArrayList<>();
    ArrayList<String> StatusList=new ArrayList<>();

    ListView listView;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_attandance);

        listView= (ListView) findViewById(R.id.ECEList);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Getting data from server ");

        dialog.show();

        reference= FirebaseDatabase.getInstance().getReference();
        auth= FirebaseAuth.getInstance();
        path= getIntent().getStringExtra("path");
        path2= getIntent().getStringExtra("path2");
        LoadData();
    }

    private void LoadData()
    {
        reference.child("Attendance").child("Sheet").child(""+path).child(""+path2).child("Attandance").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items= dataSnapshot.getChildren().iterator();

                long length=dataSnapshot.getChildrenCount();
               // String[] Roll= new String[(int)length];
               // int i=0;
                //Toast.makeText(getApplicationContext(),length+"",Toast.LENGTH_SHORT).show();

                int len=(int) length;

                if(len==0)
                {
                    Toast.makeText(getApplicationContext(),"Attandance not taken on this date",Toast.LENGTH_SHORT).show();
                }



                while (items.hasNext()) {
                    DataSnapshot item = items.next();

                    String RollTransfer=item.getKey().toString();
                    String Att=item.getValue().toString();
                    RollNumberList.add(RollTransfer);
                    StatusList.add(Att);

                   // Toast.makeText(getApplicationContext(),Att,Toast.LENGTH_SHORT).show();

                }


                MyAdapter adapter= new MyAdapter(getApplicationContext(),RollNumberList,StatusList);
                listView.setAdapter(adapter);

                dialog.dismiss();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
