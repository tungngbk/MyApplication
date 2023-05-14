package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                Request request = new Request.Builder()
                        .url("https://api.publicapis.org/entries")
                        .build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    public void onResponse(Call call, Response response)
                            throws IOException {
                        System.out.println(response.body().string());
                        String a = "1"; //
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("ip", a);
                        editor.apply();
                        /* Lay gia tri
                        * String a = sharedPreferences.getString()
                        * */
                    }

                    public void onFailure(Call call, IOException e) {
                        System.out.println("Fail");
                    }
                });
            }
        });
    }
}