package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import model.Project;
import model.record;
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
    final Calendar mCalendar = Calendar.getInstance();
    String projectName="";
    String projectId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);


        OkHttpClient client = new OkHttpClient();
        String url = "your url";

        inputName = (EditText) findViewById(R.id.inputName);
        inputDescription = (EditText) findViewById(R.id.inputDescription);
        inputDate = (EditText) findViewById(R.id.inputDate);

        selectDate();
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("".equals(inputName.getText().toString())||"".equals(inputDescription.getText().toString())){
                    Toast.makeText(CreateProjectActivity.this,"Please fill the fields", Toast.LENGTH_SHORT).show();
                }else{
                    projectName=inputName.getText().toString();
                    new DownloadProjectsTask().execute();


                }
            }
        });
    }

    private void selectDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, day);

                inputDate.setText(updateDate());
            }
        };

        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CreateProjectActivity.this, date, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private String updateDate() {
        String format = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(mCalendar.getTime());
    }

    private class insertnewproject extends AsyncTask<String, Void, Boolean> {

        protected Boolean doInBackground(String... x) {

            try {
                return Connectivity.insertNewProject(x[0],x[1],x[2]);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return false;
        }
        protected void onPostExecute(Boolean result) {
            if(result==true){
                Toast.makeText(CreateProjectActivity.this,"Create new project successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateProjectActivity.this, ControlActivity.class);
                intent.putExtra("projectname",projectName);
                intent.putExtra("projectid",projectId);
                startActivity(intent);
            }else{
                Toast.makeText(CreateProjectActivity.this,"Create new project failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class DownloadProjectsTask extends AsyncTask<Void, Void, List<Project>> {

        protected List<Project> doInBackground(Void... voids) {

            List<Project> resultProject = new ArrayList<>();
            try {
                resultProject = Connectivity.getProjects();
                // call function here

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            return resultProject;
        }
        protected void onPostExecute(List<Project> result) {
            int max=0;
            for (Project p:
                 result) {
                try{
                if(Integer.parseInt(p.getId())>max){
                        max=Integer.parseInt(p.getId());
                }
                }catch (Exception e){

                }
            }
            System.out.println("abc");
            projectId= String.valueOf(max+1);
            new insertnewproject().execute(projectId,projectName,inputDescription.getText().toString());
        }
    }
}