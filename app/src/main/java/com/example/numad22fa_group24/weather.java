package com.example.numad22fa_group24;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class weather extends AppCompatActivity {

    TextView temp, max, min, feelslike;
    URL urlLocation;
    URL urlWeather;

    Thread t;
    private static final String TAG = "WebServiceActivity";
    private String UrlString = "https://api.openweathermap.org/data/2.5/weather?q=San Jose&appid=065649652f5babbce40369334af7e36c&units=imperial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_weather);
        Log.i(TAG, "success");

        temp = findViewById(R.id.temp);
        max = findViewById(R.id.max);
        min = findViewById(R.id.min);
        feelslike = findViewById(R.id.feelslike);
        Button check = findViewById(R.id.checkButton);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checkWeather();
               // ProgressDialog.show(weather.this, "Loading", "Wait while loading...");
//                ProgressDialog progress = new ProgressDialog(weather.this);
//                progress.setTitle("Loading");
//                progress.setMessage("Wait while loading...");
//                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
//                progress.show();
                t.run();

            }
        });

        t = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    //Your code goes here

                    String[] results = new String[1];
                    URL url;
                    Log.i(TAG, "reach url");
                    url = new URL(UrlString);
                    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                    httpConn.setRequestMethod("GET");
                    httpConn.setDoInput(true);
                    httpConn.setConnectTimeout(5000);
                    httpConn.connect();

                    results = new String[6];
                    InputStream inputStream = httpConn.getInputStream();
                    String response = convertStreamToString(inputStream);
                    inputStream.close();
                    httpConn.disconnect();

                    JSONObject responseJSON = new JSONObject(response);
                    results[0] = responseJSON.getString("name");
                    JSONArray weather = responseJSON.getJSONArray("weather");
                    results[1] = weather.getJSONObject(0).getString("description");
                    JSONObject main = responseJSON.getJSONObject("main");
                    results[2] = main.getString("temp");
                    results[3] = main.getString("feels_like");
                    results[4] = main.getString("temp_max");
                    results[5] = main.getString("temp_min");
                    Log.i(TAG, response);
                    String[] finalResults = results;
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            temp.setText(finalResults[2]);
                            max.setText(finalResults[4]);
                            min.setText(finalResults[5]);
                            feelslike.setText(finalResults[3]);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

//    private void checkWeather() {
//        String[] results = new String[1];
//        String city = cityName.getText().toString();
//        URL url;
//        try {
//            url = new URL(UrlString);
//            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//            httpConn.setRequestMethod("GET");
//            httpConn.setDoInput(true);
//            httpConn.setConnectTimeout(5000);
//            httpConn.connect();
//
//            results = new String[6];
//            InputStream inputStream = httpConn.getInputStream();
//            String response = convertStreamToString(inputStream);
//            inputStream.close();
//            httpConn.disconnect();
//
//            JSONObject responseJSON = new JSONObject(response);
//            results[0] = responseJSON.getString("name");
//            JSONArray weather = responseJSON.getJSONArray("weather");
//            results[1] = weather.getJSONObject(0).getString("description");
//            JSONObject main = responseJSON.getJSONObject("main");
//            results[2] = main.getString("temp");
//            results[3] = main.getString("feels_like");
//            results[4] = main.getString("temp_max");
//            results[5] = main.getString("temp_min");
//            Log.i(TAG, results[0]);
//
//        }
//        catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
//
//    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }

    class ExampleThread extends Thread {


        ExampleThread() {
        }

        @Override
        public void run() {
            String[] results = new String[1];
            URL url;
            Log.i(TAG, "reach url");

            try {
                Log.i(TAG, "reach url");
                url = new URL(UrlString);
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                httpConn.setRequestMethod("GET");
                httpConn.setDoInput(true);
                httpConn.setConnectTimeout(5000);
                httpConn.connect();

                results = new String[6];
                InputStream inputStream = httpConn.getInputStream();
                String response = convertStreamToString(inputStream);
//                inputStream.close();
//                httpConn.disconnect();
//
//                JSONObject responseJSON = new JSONObject(response);
//                results[0] = responseJSON.getString("name");
//                JSONArray weather = responseJSON.getJSONArray("weather");
//                results[1] = weather.getJSONObject(0).getString("description");
//                JSONObject main = responseJSON.getJSONObject("main");
//                results[2] = main.getString("temp");
//                results[3] = main.getString("feels_like");
//                results[4] = main.getString("temp_max");
//                results[5] = main.getString("temp_min");
                Log.i(TAG, results[0]);

            }
            catch (IOException  e) {
                e.printStackTrace();
            }

                }
            }

    }
