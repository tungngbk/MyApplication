package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.ImageName;

public class ImageListActivity extends AppCompatActivity {
    private ArrayList<ImageName> mHeros ;
    private RecyclerView mRecyclerHero;
    private ImageNameAdapter mHeroAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        mRecyclerHero = findViewById(R.id.recyclerHero);
        new DownloadImageNameTask().execute();
    }
    private class DownloadImageNameTask extends AsyncTask<Void, Void, List<ImageName>> {

        protected List<ImageName> doInBackground(Void... voids) {

            //List<ImageName> resultImage = new ArrayList<>();
            List<ImageName> resultImage = null;
            try {
                resultImage=Connectivity.getImageNames();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //resultImage.add(new ImageName("dhdhdh"));
            return resultImage;
        }
        protected void onPostExecute(List<ImageName> result) {
            mHeros= (ArrayList<ImageName>) result;
            mHeroAdapter = new ImageNameAdapter(ImageListActivity.this,mHeros);
            mRecyclerHero.setAdapter(mHeroAdapter);
            mRecyclerHero.setLayoutManager(new LinearLayoutManager(ImageListActivity.this));
        }
    }
}
