package com.example.noovercharge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp;
    int p=90,temp=90;
    private TextView tv;

    public void st(View view){
        p=temp;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp= MediaPlayer.create(this,R.raw.charge);
//        mp.setVolume(1000,1000);
        tv=findViewById(R.id.tv);
        SeekBar seek_bar = findViewById(R.id.seekBar);
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int i=0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
             i=progress;
             temp=i;
             tv.setText(""+i+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        BroadcastReceiver broadcastReceiverBattery = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                int deviceStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
                int integerBatteryLevel = intent.getIntExtra("level",0);
                if (integerBatteryLevel>=p && deviceStatus == BatteryManager.BATTERY_STATUS_CHARGING){
                    mp.start();
//                    deviceStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
//                    integerBatteryLevel = intent.getIntExtra("level",0);
                    Toast.makeText(context,"Charging complete. Please remove the power supply now",Toast.LENGTH_LONG).show();
//                    SystemClock.sleep(30000);
                }
            }
        };
        registerReceiver(broadcastReceiverBattery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }


    @Override
    protected void onStop() {
        super.onStop();
        mp.stop();
    }






}