package com.whut.ein3614.appwidgettest;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private MyAppWidgetProvider appWidgetProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appWidgetProvider = new MyAppWidgetProvider();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyAppWidgetProvider.CLICK_ACTION);
        registerReceiver(appWidgetProvider,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(appWidgetProvider);
    }
}
