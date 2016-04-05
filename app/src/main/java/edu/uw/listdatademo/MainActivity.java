package edu.uw.listdatademo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        final EditText searchField = (EditText)findViewById(R.id.txtSearch);
        final Button searchButton = (Button)findViewById(R.id.btnSearch);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = searchField.getText().toString();
                Log.v(TAG, "Searching for: " + searchTerm);

                MovieDownloadTask task = new MovieDownloadTask();
                task.execute(searchTerm);
            }
        });


        //model!
//        String[] data = new String[99];
//        for(int i=99; i>0; i--){
//            data[99-i] = i+ " bottles of beer on the wall";
//        }
        ArrayList<String> data = new ArrayList<String>(); //empty data; ArrayList so modifiable


        //controller
        adapter = new ArrayAdapter<String>(
                this, R.layout.list_item, R.id.txtItem, data);

        //supports ListView or GridView
        AdapterView listView = (AdapterView)findViewById(R.id.listView);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String movie = (String) parent.getItemAtPosition(position);
                Log.d(TAG, "You clicked on " + movie);
            }
        });
    }

    public class MovieDownloadTask extends AsyncTask<String, Void, String[]> {

        protected String[] doInBackground(String... params){

            String movieTitle = params[0];

            //could have pasted code directly in here if needed
            String[] movies = MovieDownloader.downloadMovieData(movieTitle);

            return movies;
        }

        protected void onPostExecute(String[] movies){
            if(movies != null) {
                adapter.clear();
                for (String movie : movies) {
                    adapter.add(movie);
                }
            }
        }
    }
}
