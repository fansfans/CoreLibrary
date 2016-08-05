package com.core.samplelib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.core.network.NetworkHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkHelper.init(getApplicationContext(),false);
    }
}
