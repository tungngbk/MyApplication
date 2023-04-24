package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.ImageName;
import model.Project;

public class ProjectListActivity extends AppCompatActivity implements SelectListenerProject {
    private ArrayList<Project> mProjects ;
    private RecyclerView mRecyclerProject;
    private ProjectListAdapter mProjectAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        mRecyclerProject = findViewById(R.id.recyclerProject);
        new DownloadProjectsTask().execute();
    }

    @Override
    public void onItemClicked(Project project) {
        Toast.makeText(this, project.getName(), Toast.LENGTH_SHORT).show();
    }

    private class DownloadProjectsTask extends AsyncTask<Void, Void, List<Project>> {

        protected List<Project> doInBackground(Void... voids) {

            List<Project> resultProject = null;
            try {
                resultProject = Connectivity.getProjects();
                // call function here
                System.out.println("abc");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //resultImage.add(new ImageName("dhdhdh"));
            return resultProject;
        }
        protected void onPostExecute(List<Project> result) {
            mProjects = (ArrayList<Project>) result;
            mProjectAdapter = new ProjectListAdapter(ProjectListActivity.this, mProjects, ProjectListActivity.this);
            mRecyclerProject.setAdapter(mProjectAdapter);
            mRecyclerProject.setLayoutManager(new LinearLayoutManager(ProjectListActivity.this));
        }
    }
}
