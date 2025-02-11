package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class allDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String jsonData = getIntent().getStringExtra("response_data");

        // Initialize the TextViews
        TextView cityNameTextView = findViewById(R.id.cityNameTextView);
        TextView temperatureTextView = findViewById(R.id.temperatureTextView);
        TextView weatherDescriptionTextView = findViewById(R.id.weatherDescriptionTextView);
        TextView humidityTextView = findViewById(R.id.humidityTextView);
        TextView windSpeedTextView = findViewById(R.id.windSpeedTextView);
        TextView sunriseTextView = findViewById(R.id.sunriseTextView);
        TextView sunsetTextView = findViewById(R.id.sunsetTextView);

        try {
            // Parse the JSON data
            JSONObject jsonObject = new JSONObject(jsonData);

            // Extract details
            String cityName = jsonObject.getString("name");
            JSONObject main = jsonObject.getJSONObject("main");
            double temperature = main.getDouble("temp");
            int humidity = main.getInt("humidity");

            JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);
            String description = weather.getString("description");

            JSONObject wind = jsonObject.getJSONObject("wind");
            double windSpeed = wind.getDouble("speed");

            JSONObject sys = jsonObject.getJSONObject("sys");
            long sunrise = sys.getLong("sunrise");
            long sunset = sys.getLong("sunset");

            // Format sunrise and sunset times
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            String sunriseTime = timeFormat.format(new Date(sunrise * 1000));
            String sunsetTime = timeFormat.format(new Date(sunset * 1000));

            // Update the TextViews with the extracted data
            cityNameTextView.setText(cityName);
            temperatureTextView.setText(String.format("Temperature: %.1f°C", temperature));
            weatherDescriptionTextView.setText("Description: " + description);
            humidityTextView.setText("Humidity: " + humidity + "%");
            windSpeedTextView.setText(String.format("Wind Speed: %.1f m/s", windSpeed));
            sunriseTextView.setText("Sunrise: " + sunriseTime);
            sunsetTextView.setText("Sunset: " + sunsetTime);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void btnClick(View v){
        Intent intent = new Intent(allDetails.this,MainActivity.class);
        startActivity(intent);
    }
}