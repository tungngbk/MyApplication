package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConfigWifiActivity extends AppCompatActivity {
    private EditText inputSsid;
    private EditText inputPassword;
    private Button btnConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_wifi);

        inputSsid = (EditText) findViewById(R.id.inputSsid);
        inputPassword = (EditText) findViewById(R.id.inputPassword);

        btnConfig = (Button) findViewById(R.id.btnConfig);
        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}