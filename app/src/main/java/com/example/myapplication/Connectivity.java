package com.example.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Connectivity {
    public static byte[] geturl (String url_esp32){

        OkHttpClient client = new OkHttpClient();
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
    public static String postimage (String url,String base64string) throws IOException, JSONException {

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", base64string)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        String responsebody =response.body().string();
        JSONObject jObject = new JSONObject(responsebody);

        return jObject.getString("image");
    }

}
