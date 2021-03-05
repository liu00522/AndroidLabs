package com.cst2335.liu00522;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);


        ForecastQuery query = new ForecastQuery();
        query.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");  //Type 1
    }


    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        private double uv;
        private String min;
        private String max;
        private String currentTemp;
        private URL url = null;
        private String iconName;
        private Bitmap image;
        TextView currentTemptv = findViewById(R.id.currentTemp);
        TextView minTemptv = findViewById(R.id.minTemp);
        TextView maxTemptv = findViewById(R.id.maxTemp);
        TextView uvRatingtv = findViewById(R.id.uvRating);
        ImageView imageView = findViewById(R.id.currentWeather);
        ProgressBar pgsBar = findViewById(R.id.progressBar);


        @Override
        protected String doInBackground(String... args) {

            try {
                url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();


                //  pull parser
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");

                //
                String parameter = null;

                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT


                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equals("temperature")) {
                            currentTemp = xpp.getAttributeValue(null, "value");
                            publishProgress(25);

                            min = xpp.getAttributeValue(null, "min");
                            publishProgress(50);

                            max = xpp.getAttributeValue(null, "max");
                            publishProgress(75);
                        } else if (xpp.getName().equals("weather")) {
                            iconName = xpp.getAttributeValue(null, "icon");

                        }

                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable
                }

                url = new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
                urlConnection = (HttpURLConnection) url.openConnection();
                response = urlConnection.getInputStream();


                //  read JSON
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string

                JSONObject jsonObject = new JSONObject(result);

                uv = jsonObject.getDouble("value");


                //  image
                url = new URL("https://openweathermap.org/img/w/" + iconName + ".png");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200) {
                    image = BitmapFactory.decodeStream(urlConnection.getInputStream());
                }

                ////////////////////////////////
                if (fileExistance(iconName + ".png")) {
                    Log.i("WeatherForecast", "Downloading" + iconName + ".png");
                    FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } else {
                    Log.i("WeatherForecast", "Already exists, retrieving " + iconName + ".png");
                    FileInputStream fis = null;
                    try {
                        fis = openFileInput(getBaseContext().getFileStreamPath(iconName + ".png").toString());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    image = BitmapFactory.decodeStream(fis);
                }

                ////////////////////////////////


            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Done";
        }

        ////////////////////////////////
        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }
        ////////////////////////////////

        //Type 2
        public void onProgressUpdate(Integer... args) {
            //  call this in response to publishProgress();
            pgsBar.setProgress(args[0]);
        }

        //Type3 from DoinBackground is the return from doInBackground "Done"
        public void onPostExecute(String fromDoInBackground) {  //  not an array "Done"
//            Log.i("HTTP", fromDoInBackground);
            currentTemptv.setText(currentTemp + " °C");
            minTemptv.setText(min + "  °C");
            maxTemptv.setText(max + "  °C");
            uvRatingtv.setText(Double.toString(uv));
            imageView.setImageBitmap(image);
            pgsBar.setVisibility(View.INVISIBLE);

        }
    }
}

