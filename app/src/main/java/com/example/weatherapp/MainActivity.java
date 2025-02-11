package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    TextView temp;
    TextView wind;
    TextView city;
    TextView discription;
    ImageView img ;
    String userEmail;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("USER_EMAIL");

        setButtonClickListener(R.id.buttonNewYork, "New York");
        setButtonClickListener(R.id.buttonLondon, "London");
        setButtonClickListener(R.id.buttonTokyo, "Tokyo");
        setButtonClickListener(R.id.buttonColombo, "Colombo");
        setButtonClickListener(R.id.buttonDubai, "Dubai");

        setImgClickListener(R.id.imageViewNewYork,"New York");
        setImgClickListener(R.id.imageViewLondon, "London");
        setImgClickListener(R.id.imageViewTokyo, "Tokyo");
        setImgClickListener(R.id.imageViewColombo, "Colombo");
        setImgClickListener(R.id.imageViewDubai, "Dubai");

        city = findViewById(R.id.textViewcity);
        temp = findViewById(R.id.textViewtemp);
        discription = findViewById(R.id.textViewdescription);
        wind = findViewById(R.id.textViewwind);
        img = findViewById(R.id.imageView);
        img.setImageDrawable(getDrawable(R.drawable.sun));
    }
    private void setButtonClickListener(int buttonId, String cityName) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            String apiKey = "989b50cf12ff4e1d6903557a3d2e1728";
            String url = "https://api.openweathermap.org/data/2.5/weather?q="+ cityName + "&appid=" + apiKey + "&units=metric";
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(()->
                    {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(url).build();
                        try{
                            Response response = client.newCall(request).execute();
                            String result = response.body().string();
                            runOnUiThread(()->updateUI(result));
                            Log.d("result",result);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
            );
        });
    }
    private void setImgClickListener(int buttonId, String cityName) {
        ImageView button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            String apiKey = "989b50cf12ff4e1d6903557a3d2e1728";
            String url = "https://api.openweathermap.org/data/2.5/weather?q="+ cityName + "&appid=" + apiKey + "&units=metric";
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(()->
                    {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(url).build();
                        try{
                            Response response = client.newCall(request).execute();
                            String result = response.body().string();

                            Intent intent = new Intent(MainActivity.this, allDetails.class);
                            intent.putExtra("response_data", result);
                            startActivity(intent);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
            );
        });
    }

    private void updateUI(String result) {
        if(result != null ){
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONObject main = jsonObject.getJSONObject("main");
                double temperature = main.getDouble("temp");
                double humidty = main.getDouble("humidity");
                double windspeed = jsonObject.getJSONObject("wind").getDouble("speed");
                String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
                String icon = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
                String cityname = jsonObject.getString("name");
                Log.d("result",""+humidty);
                city.setText(cityname);
                temp.setText(String.format("%.1f°C", temperature));
                discription.setText("description: "+description);
                wind.setText("wind speed: "+String.format("%.1f m/s", windspeed));
                String imageUrl = "https://openweathermap.org/img/wn/"+icon+"@2x.png";

                Glide.with(this)
                        .load(imageUrl)
                        .into(img);

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}