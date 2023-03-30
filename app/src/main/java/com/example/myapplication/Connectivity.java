package com.example.myapplication;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Connectivity {
    public static byte[] geturl (String url_esp32){

        OkHttpClient client = new OkHttpClient();
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.connectTimeout(5, TimeUnit.MINUTES) // connect timeout
//                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
//                .readTimeout(5, TimeUnit.MINUTES); // read timeout
//
//        client = builder.build();
        Request request = new Request.Builder()
                .url(url_esp32)
                .build();

        try  {
            Response response = client.newCall(request).execute();
            return response.body().bytes();

        } catch (IOException error) {
            return null;
        }


    }

}
