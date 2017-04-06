package edu.uw.inputlistdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

//        Button searchButton = (Button)findViewById(R.id.btnSearch);

//        searchButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Log.v(TAG, "You clicked me!");
//            }
//        });
        //model
        ArrayList<String> data = new ArrayList<String>();



//        for (int i=99; i>0; i--) {
//            data[99-i] = i + " bottles of beer on the wall";
//        }

        //view
        // See XML!

        //controller
        adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, R.id.txtItem, data);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    public void handleButtonSearch(View v) {
        Log.v(TAG, "Clicked from the XML");

        EditText searchText = (EditText)findViewById(R.id.txtSearch);
        // this is a string builder so needs to be changed to string
        String searchTerm = searchText.getText().toString();

        Log.v(TAG, "You searched for:" + searchTerm);

        MovieDownloadTask myTask = new MovieDownloadTask();
        myTask.execute(searchTerm);
    }

    public class MovieDownloadTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {
            Log.v(TAG, params[0]);
            String[] results = MovieDownloadTask.downloadMovieData(params[0]);

            for(String movie : results) {
                Log.v(TAG, movie);
            }
            return results;
        }

        @Override
        protected void onPostExecute(String[] movies) {
            super.onPostExecute(movies);

            if(movies != null) {
                adapter.clear();
                for(String movie : movies) {
                    adapter.add(movie);
                }
            }
        }
    }
}
