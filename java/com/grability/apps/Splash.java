package com.grability.apps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ana on 10/11/2016.
 */

public class Splash extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent itemintent = new Intent(Splash.this, MainActivity.class);
                    itemintent.setFlags(itemintent.getFlags() | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(itemintent);
                }
            }
        };
        timerThread.start();
    }
}
