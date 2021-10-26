package com.atr.lowadventure.activity;

import android.app.Activity;

import android.os.*;
import android.content.Intent;


import com.atr.Util;
import com.atr.lowadventure.singlemode.SingleGameActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), SingleGameActivity.class);
        startActivity(intent);


    }

    private void initializeLogic() {
        Util.logAdd("MainActivity:initializeLogic()", this);
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            startActivity( new Intent(this, SingleGameActivity.class));
        } else {
            startActivity(new Intent(this, SignPageActivity.class));
        }
        finish();
    }

}
