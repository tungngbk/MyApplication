package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import model.record;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

public class ConfigWifiActivity extends AppCompatActivity {
    private EditText inputSsid;
    private EditText inputPassword;
    private Button btnConfig;
    private final String BASE_URL = "http://192.168.4.1:8080";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_wifi);

        sharedPreferences = getSharedPreferences("mySharedPreferences", MODE_PRIVATE);

        inputSsid = (EditText) findViewById(R.id.inputSsid);
        inputPassword = (EditText) findViewById(R.id.inputPassword);

        btnConfig = (Button) findViewById(R.id.btnConfig);
        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .connectTimeout(2021
                                , TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS).build();

                Request request = new Request.Builder()
                        .url("http://192.168.4.1:8080/wifi/"+inputSsid.getText().toString()+"/"+inputPassword.getText().toString())
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    public void onResponse(Call call, Response response)
                            throws IOException {
                        String responseBody = response.body().string();
                        System.out.println(responseBody);
                        if(responseBody!=null){
                            if("fail".equals(responseBody)){
                            }else{
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("ip", ""+responseBody);
                                editor.apply();
                                Intent intent = new Intent(ConfigWifiActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    }

                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }


}