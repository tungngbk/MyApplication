package com.example.myapplication;

// Little project from wiwidnadw
// program use java language
//u can reach me on https://github.com/wiwidnadw
//https://www.youtube.com/channel/UClxwaaJ-or0SJtlWMi3pHpA
// line : wiwidnadw
//gmail : nurahmaddw@gmail.com


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
//import org.apache.commons.io.IOUtils;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import model.ResultPredict;
import model.record;


public class ControlActivity extends AppCompatActivity implements View.OnClickListener {
    private Button takePhoto;
    private Button mUpBtn;
    private Button mDownBtn;
    private Button mLeftBtn;
    private Button mRightBtn;
    private Button mStopBtn;

    private TextView projectNameView;

    protected static String UP = "up";
    protected static String DOWN = "down";
    protected static String LEFT = "left";
    protected static String RIGHT = "right";
    protected static String STOP = "stop";

    ImageView imageView;
    WebView webView;
    TextView textView;

    // Insert your Video URL
    String VideoURL = "http://192.168.32.184:81/stream";
    String ip="";
    private SharedPreferences sharedPreferences;
    String projectId="";

    Handler handler = new Handler();
    //private StringBuilder image=new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_activity);
        Intent intent = getIntent();
        String projectName = intent.getStringExtra("projectname");
        projectId = intent.getStringExtra("projectid");
        if(projectName==null){
            System.out.println("ten bị null");
        }else {
            System.out.println(projectName);
        }
        sharedPreferences = getSharedPreferences("mySharedPreferences", MODE_PRIVATE);
        ip=sharedPreferences.getString("ip","");
        if(ip.equals("")){
            Intent intent2 = new Intent(ControlActivity.this, ConfigWifiActivity.class);
            startActivity(intent2);
        }
        textView = (TextView) findViewById(R.id.textView7);
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        String newUA= "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        webView.getSettings().setUserAgentString(newUA);
        webView.loadUrl("http://"+ip+":81/stream");

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

        projectNameView = (TextView) findViewById(R.id.textView2);
        projectNameView.setText(projectName);



        //handler.postDelayed(status_data,0);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.takePhoto:
                textView.setText("");
                new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute("http://"+ip+"/capture");
                break;
            case R.id.upBtn:

                new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute("http://"+ip+"/left");
                break;
            case R.id.downBtn:
                new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute("http://"+ip+"/right");
                break;
            case R.id.leftBtn:
                new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute("http://"+ip+"/back");
                break;
            case R.id.rightBtn:
                new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute("http://"+ip+"/go");
                break;
            case R.id.stopBtn:
                new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute("http://"+ip+"/stop");
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
//            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                System.out.println("this");
                //bmImage.setImageBitmap(mIcon11);
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 4;
//                options.inPurgeable = true;
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                mIcon11.compress(Bitmap.CompressFormat.JPEG, 40, baos);
//                byte[] byteImage_photo = baos.toByteArray();
//
//                String encodedImage = Base64.encodeToString(byteImage_photo, Base64.DEFAULT);
//                String newencodedImage = encodedImage.replace("\n","");
//                record resultImage = Connectivity.postimage(
//                        "https://fault-anomaly-detection-api-k6hgw7qjeq-ue.a.run.app/predict", newencodedImage, projectId);
//                //String resultImage = Connectivity.postimage("http://192.168.1.12:8000/test", newencodedImage);
//                if("1".equals(resultImage.getOriginal_image())){
//                    byte[] bytes = Base64.decode(resultImage.getSegment_image(), Base64.DEFAULT);
//                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                }else if("0".equals(resultImage.getOriginal_image())){
//                    bitmap = mIcon11;
//                }

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;

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
            if(result!=null){
                bmImage.setImageBitmap(result);
            }
            new getSegmentImage((ImageView) findViewById(R.id.imageView)).execute(result);
        }
    }

    private class getSegmentImage extends AsyncTask<Bitmap, Void, ResultPredict> {
        ImageView bmImage;

        public getSegmentImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected ResultPredict doInBackground(Bitmap... bitmaps) {
//            String urldisplay = urls[0];
//            Bitmap mIcon11 = null;
            Bitmap bitmap = null;
            record record = null;
            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
                //bmImage.setImageBitmap(mIcon11);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                options.inPurgeable = true;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmaps[0].compress(Bitmap.CompressFormat.JPEG, 40, baos);
                byte[] byteImage_photo = baos.toByteArray();

                String encodedImage = Base64.encodeToString(byteImage_photo, Base64.DEFAULT);
                String newencodedImage = encodedImage.replace("\n","");
                record resultImage = Connectivity.postimage(
                        "https://fault-anomaly-detection-api-k6hgw7qjeq-ue.a.run.app/predict", newencodedImage, projectId);
                //String resultImage = Connectivity.postimage("http://192.168.1.12:8000/test", newencodedImage);
                record=new record("","",resultImage.getPrediction(),resultImage.getOriginal_image(),resultImage.getType(),"");
                if("1".equals(resultImage.getOriginal_image())&&"Crack".equals(resultImage.getType())){
                    byte[] bytes = Base64.decode(resultImage.getSegment_image(), Base64.DEFAULT);
                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    System.out.println("segment");
                }else{
                    System.out.println("normal image");
                    bitmap = bitmaps[0];
                }

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return new ResultPredict(bitmap,record);
        }

        protected void onPostExecute(ResultPredict result) {
            if(result!=null){
                bmImage.setImageBitmap(result.getBitmap());
                if(result.getRecord()!=null){
                    if("1".equals(result.getRecord().getOriginal_image())){
                        textView.setText("Prediction Result: "+result.getRecord().getType() + "  Fault rate: " + result.getRecord().getPrediction()*100+"%");
                    }else{
                        textView.setText("Prediction Result: Normal");
                    }
                    if("Crack".equals(result.getRecord().getType())||"Normal".equals(result.getRecord().getType())){

                    }else{

                    }

                }
            }
        }
    }


    private class controlTask extends AsyncTask<String, Void, Void> {

        protected Void doInBackground(String... x) {
            Connectivity.control(x[0]);
            return null;
        }
    }




}
