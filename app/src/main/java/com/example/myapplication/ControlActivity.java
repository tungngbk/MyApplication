package com.example.myapplication;

// Little project from wiwidnadw
// program use java language
//u can reach me on https://github.com/wiwidnadw
//https://www.youtube.com/channel/UClxwaaJ-or0SJtlWMi3pHpA
// line : wiwidnadw
//gmail : nurahmaddw@gmail.com


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
//import org.apache.commons.io.IOUtils;



import static com.example.myapplication.activity_1.ip_address;


import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import model.ImageName;


public class ControlActivity extends AppCompatActivity implements View.OnClickListener {
    private Button takePhoto;
    private Button mUpBtn;
    private Button mDownBtn;
    private Button mLeftBtn;
    private Button mRightBtn;
    private Button mStopBtn;

    protected static String UP = "up";
    protected static String DOWN = "down";
    protected static String LEFT = "left";
    protected static String RIGHT = "right";
    protected static String STOP = "stop";

    ImageView imageView;
    Handler handler = new Handler();
    //private StringBuilder image=new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_activity);

        takePhoto = (Button) findViewById(R.id.takePhoto);
        takePhoto.setOnClickListener(this);

        mUpBtn = (Button) findViewById(R.id.upBtn);
        mUpBtn.setOnClickListener(this);

        mDownBtn = (Button) findViewById(R.id.downBtn);
        mDownBtn.setOnClickListener(this);

        mLeftBtn = (Button) findViewById(R.id.leftBtn);
        mLeftBtn.setOnClickListener(this);

        mRightBtn = (Button) findViewById(R.id.rightBtn);
        mRightBtn.setOnClickListener(this);

        mStopBtn = (Button) findViewById(R.id.stopBtn);
        mStopBtn.setOnClickListener(this);

        imageView = (ImageView) findViewById(R.id.imageView);



        //handler.postDelayed(status_data,0);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.takePhoto:
                new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute("http://192.168.1.184/capture");
                break;
            case R.id.upBtn:
                break;
            case R.id.downBtn:
                break;
            case R.id.leftBtn:
                break;
            case R.id.rightBtn:
                break;
            case R.id.stopBtn:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                options.inPurgeable = true;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                mIcon11.compress(Bitmap.CompressFormat.JPEG, 40, baos);
                byte[] byteImage_photo = baos.toByteArray();

                String encodedImage = Base64.encodeToString(byteImage_photo, Base64.DEFAULT);
                String newencodedImage = encodedImage.replace("\n","");
                String resultImage = Connectivity.postimage(
                        "https://fault-anomaly-detection-api-k6hgw7qjeq-ue.a.run.app/predict", newencodedImage);
                //String resultImage = Connectivity.postimage("http://192.168.1.12:8000/test", newencodedImage);
                byte[] bytes = Base64.decode(resultImage, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;

            //String resultImage;

                //resultImage = Connectivity.postimage("http://192.168.1.12:8000/test", "\"image\": \"alsdjk\"");
                //resultImage=Connectivity.getImageNames();
                //resultImage=Connectivity.geturl("https://fault-anomaly-detection-api-k6hgw7qjeq-ue.a.run.app/haha");
            //System.out.println(resultImage);
//            for (ImageName image:
//                 resultImage) {
   //           System.out.println(image.getName());
//            }
            //return null;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }





}


