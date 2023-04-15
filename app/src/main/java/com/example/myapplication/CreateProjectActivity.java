package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateProjectActivity extends AppCompatActivity {

    private EditText inputName;
    private EditText inputDescription;
    private EditText inputDate;
    private Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        OkHttpClient client = new OkHttpClient();
        String url = "http://192.168.0.103:8000/test";

        inputName = (EditText) findViewById(R.id.inputName);
        inputDescription = (EditText) findViewById(R.id.inputDescription);
        inputDate = (EditText) findViewById(R.id.inputDate);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestBody formBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("image", "zyx")
                        .build();

                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final String myResponse = response.body().string();
                            System.out.println(myResponse);
                        }
                    }
                });
            }
        });
    }
}