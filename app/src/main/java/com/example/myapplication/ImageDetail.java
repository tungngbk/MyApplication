package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.record;

public class ImageDetail extends AppCompatActivity {
    private TextView projectnametext;
    private TextView capturedatetext;
    private TextView predicttext;
    private ImageView originalimage;
    private ImageView segmengtimage;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        projectnametext = findViewById(R.id.textView3);
        capturedatetext = findViewById(R.id.textView4);
        predicttext = findViewById(R.id.textView7);
        originalimage = findViewById(R.id.imageView3);
        segmengtimage = findViewById(R.id.imageView4);
        Intent intent = getIntent();
        String projectName = intent.getStringExtra("projectname");
        projectnametext.setText(projectName);
        String date = intent.getStringExtra("date");
        try {
            Date date1 = dateFormat.parse(date);
            capturedatetext.setText(dateFormat2.format(date1));
        } catch (ParseException e) {
            capturedatetext.setText(date);
            throw new RuntimeException(e);
        }

        new DownloadImagedetailTask().execute(date);
    }

    private class DownloadImagedetailTask extends AsyncTask<String, Void, record> {

        protected record doInBackground(String... x) {


            record record =Connectivity.getImagedetail(x[0]);
            return record;
        }
        protected void onPostExecute(record result) {
            predicttext.setText("Prediction Result: "+result.getType() + "  Percentage: " + result.getPrediction()*100+"%");
            byte[] bytes = Base64.decode(result.getOriginal_image(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            originalimage.setImageBitmap(bitmap);
            bytes = Base64.decode(result.getSegment_image(), Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            segmengtimage.setImageBitmap(bitmap);
        }
    }
}