package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button createProjectBtn;
    private Button openProjectBtn;

    private Button openChartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createProjectBtn = (Button) findViewById(R.id.create);
        createProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateProjectActivity.class);
                startActivity(intent);
            }
        });

        openProjectBtn = (Button) findViewById(R.id.open);
        openProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProjectListActivity.class);
                startActivity(intent);
            }
        });

        openChartBtn = (Button) findViewById(R.id.chart);
        openChartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, chart_activity.class);
                startActivity(intent);
            }
        });

    }
}