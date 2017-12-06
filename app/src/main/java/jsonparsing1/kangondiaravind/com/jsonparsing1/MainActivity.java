package jsonparsing1.kangondiaravind.com.jsonparsing1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView txtdisplay;
    private static final  String jsonurl = "https://api.myjson.com/bins/1aow3b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtdisplay = (TextView) findViewById(R.id.txtDisplay);
        Button btnclick = (Button) findViewById(R.id.btnHit);
        btnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonTask().execute();
            }
        });
    }

    class JsonTask extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
          txtdisplay.setText(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(jsonurl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream =urlConnection.getInputStream();
                BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer buffer = new StringBuffer();
                String line="";
                while ((line=bufferedReader.readLine())!=null)
                {
                    buffer.append(line);
                }
                System.out.println("Data:::" +buffer.toString());
                String finaljson = buffer.toString();
                JSONObject parentobject = new JSONObject(finaljson);
                JSONArray parentarray = parentobject.getJSONArray("movies");

                JSONObject moviesobject = parentarray.getJSONObject(0);
                String moviename = moviesobject.getString("movie");
                int movieyear = moviesobject.getInt("year");

                return moviename + "-" + movieyear;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}