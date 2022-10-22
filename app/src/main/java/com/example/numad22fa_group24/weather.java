package com.example.numad22fa_group24;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class weather extends AppCompatActivity {

    TextView temp, max, min, feelslike;
    EditText cityName;
    URL urlLocation;
    URL urlWeather;

    Thread t1, t2;
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
        cityName = findViewById(R.id.cityName);
        Button check = findViewById(R.id.checkButton);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checkWeather();
                String city = cityName.getText().toString();
                if(city.length() == 0) city = "San Jose";
                //Toast.makeText(getBaseContext(), "You are now checking the weather at "+ city,Toast.LENGTH_SHORT).show();

                final ProgressDialog progress = new ProgressDialog(weather.this);
                progress.setTitle("Connecting");
                progress.setMessage("Getting you the weather of " + city +" ...");
                progress.show();

                Runnable progressRunnable = new Runnable() {

                    @Override
                    public void run() {
                        progress.cancel();
                    }
                };

                Handler pdCanceller = new Handler();
                pdCanceller.postDelayed(progressRunnable, 4000);
                ExampleThread thread = new ExampleThread(city);
               thread.run();
            }
        });

        t2 = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

        t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    //Your code goes here

                    String[] results = new String[1];
                    URL url;
                    String city = cityName.getText().toString();
                    if(city.length() == 0) city = "San Jose";
                    Log.i(TAG, "reach url");
                    url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+city
                            +"&appid=065649652f5babbce40369334af7e36c&units=imperial");
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
                    results[4] = main.getString("pressure");
                    results[5] = main.getString("humidity");
                    Log.i(TAG, response);
                    String[] finalResults = results;
                    Thread.sleep(4000);
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


    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }

    class ExampleThread extends Thread {
    String city;

        ExampleThread(String city) {
            this.city = city;
        }

        @Override
        public void run() {
            try  {
                //Your code goes here

                String[] results = new String[1];
                URL url;

                Log.i(TAG, "reach url");
                url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+city
                        +"&appid=065649652f5babbce40369334af7e36c&units=imperial");
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
                results[4] = main.getString("pressure");
                results[5] = main.getString("humidity");
                Log.i(TAG, response);
                String[] finalResults = results;
                Thread.sleep(4000);
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
            }

    }
