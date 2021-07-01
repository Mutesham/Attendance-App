package com.dcet.attendance;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by shafiq on 31/03/17.
 */

public class MyFirebaseInstanceIDService  extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    @Override
    public void onTokenRefresh() {
//Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        //Toast.makeText(getApplicationContext(),"T:  "+refreshedToken,Toast.LENGTH_SHORT).show();
    }
    private void sendRegistrationToServer(String token) {
//You can implement this method to store the token on your server
    }
}

