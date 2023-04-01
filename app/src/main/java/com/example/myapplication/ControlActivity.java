package com.example.myapplication;

// Little project from wiwidnadw
// program use java language
//u can reach me on https://github.com/wiwidnadw
//https://www.youtube.com/channel/UClxwaaJ-or0SJtlWMi3pHpA
// line : wiwidnadw
//gmail : nurahmaddw@gmail.com


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
//import org.apache.commons.io.IOUtils;



import static com.example.myapplication.activity_1.ip_address;


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
                request_to_url("take_photo");
                break;
            case R.id.upBtn:
                request_to_url(UP);
                break;
            case R.id.downBtn:
                request_to_url(DOWN);
                break;
            case R.id.leftBtn:
                request_to_url(LEFT);
                break;
            case R.id.rightBtn:
                request_to_url(RIGHT);
                break;
            case R.id.stopBtn:
                request_to_url(STOP);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void request_to_url (String command) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            new request_data().execute("http://" + ip_address + "/" + command);
        }else {
            Toast.makeText(ControlActivity.this, "Not connected  ", Toast.LENGTH_LONG).show();

        }
    }

    private class request_data extends AsyncTask<String, Void, byte[]> {

        @Override
        protected byte[] doInBackground(String... url) {
            return Connectivity.geturl(url[0]);
        }


        @Override
        protected void onPostExecute(byte[] result_data) {
            if(result_data != null) {
                Log.d("chieu dai mang", String.valueOf(result_data.length));
                Bitmap bmp = BitmapFactory.decodeByteArray(result_data, 0, result_data.length);
                imageView.setImageBitmap(bmp);
            }else{
                Toast.makeText(ControlActivity.this, "Null data", Toast.LENGTH_LONG).show();
            }
        }
    }


}


