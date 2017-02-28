package com.nakoukal.radek.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TempDetailActivity extends AppCompatActivity {

    TextView test;
    ImageView ivDay,ivWeek;
    private String host,port,name,user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_detail);

        String path = this.getFilesDir().getAbsolutePath();
        Config cfg = new Config(path);
        if (cfg.load()) {
            this.host = cfg.get("host");
            this.port = cfg.get("port");
            this.name = cfg.get("name");
            this.user = cfg.get("user");
            this.pass = cfg.get("pass");
        }

        // 1. get passed intent
        Intent intent = getIntent();
        // get object from intent
        TempObject to = (TempObject) intent.getSerializableExtra("temp");

        //set data from
        test = (TextView) findViewById(R.id.tvTest);
        test.setText(to.GetDes());

        ivDay = (ImageView) findViewById(R.id.imageViewDay);
        new ImageLoadTask("http://"+host+":"+port+"/smarthome/temp/day_"+to.GetSensorid()+".png", ivDay).execute();
        ivWeek = (ImageView) findViewById(R.id.imageViewWeek);
        new ImageLoadTask("http://"+host+":"+port+"/smarthome/temp/week_"+to.GetSensorid()+".png", ivWeek).execute();
    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                String login  = user+":"+pass;
                String basicAuth = "Basic " + new String(Base64.encode(login.getBytes(), Base64.NO_WRAP));
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setRequestProperty("Authorization", basicAuth);
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

    }
}
