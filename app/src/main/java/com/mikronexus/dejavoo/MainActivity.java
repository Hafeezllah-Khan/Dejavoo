package com.mikronexus.dejavoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    Runnable updateClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.switchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("app1://open"));
                startActivity(intent);
            }
        });

        TextView packageNameTextView = findViewById(R.id.pakage_name);
        packageNameTextView.setText(getPackageName());

        handler = new Handler(Looper.getMainLooper());
        updateClock = new Runnable() {
            @Override
            public void run() {
                updateClockDisplay();
                handler.postDelayed(this, 1000);
            }
        };
    }

    private void updateClockDisplay() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a", Locale.getDefault());
        String currentTime = dateFormat.format(calendar.getTime());
        TextView timeTxt = findViewById(R.id.time_date);
        timeTxt.setText(currentTime);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(handler != null && updateClock != null) {
            handler.post(updateClock);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(handler != null && updateClock != null){
            handler.removeCallbacks(updateClock);
        }
    }

}