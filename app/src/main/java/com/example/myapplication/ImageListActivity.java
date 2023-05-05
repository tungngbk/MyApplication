package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import model.record;

public class ImageListActivity extends AppCompatActivity implements SelectListener {
    private ArrayList<record> mHeros ;
    private RecyclerView mRecyclerHero;
    private ImageNameAdapter mHeroAdapter ;

    private String projectName;

    private TextView projectnameview;
    private TextView projectdescription;
    private Button btncontinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        Intent intent = getIntent();
        String projectId = intent.getStringExtra("projectId");
        projectName = intent.getStringExtra("projectName");
        String projectDescription = intent.getStringExtra("projectDescription");
        mRecyclerHero = findViewById(R.id.recyclerHero);
        projectnameview = findViewById(R.id.textView5);
        projectnameview.setText(projectName);
        projectdescription = findViewById(R.id.textView6);
        projectdescription.setText(projectDescription);
        btncontinue = findViewById(R.id.button);
        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImageListActivity.this, ControlActivity.class);
                intent.putExtra("projectid",projectId);
                intent.putExtra("projectname",projectName);
                startActivity(intent);
            }
        });
        new DownloadImageNameTask().execute(projectId);
    }

    @Override
    public void onItemClicked(record image) {
        Intent intent = new Intent(ImageListActivity.this, ImageDetail.class);
        intent.putExtra("date",image.getDate());
        intent.putExtra("projectname",projectName);
        startActivity(intent);
    }

    private class DownloadImageNameTask extends AsyncTask<String, Void, List<record>> {

        protected List<record> doInBackground(String... x) {

            //List<ImageName> resultImage = new ArrayList<>();
            List<record> records = null;
            records = Connectivity.getImageOfProject(x[0]);
            return records;
        }
        protected void onPostExecute(List<record> result) {
            mHeros= (ArrayList<record>) result;
            mHeroAdapter = new ImageNameAdapter(ImageListActivity.this, mHeros, ImageListActivity.this);
            mRecyclerHero.setAdapter(mHeroAdapter);
            mRecyclerHero.setLayoutManager(new LinearLayoutManager(ImageListActivity.this));
        }
    }
}
