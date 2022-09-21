package com.districthut.islam.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.districthut.islam.adapters.HalalPlacesAdapter;
import com.districthut.islam.models.GPSTracker;
import com.districthut.islam.models.Place;
import com.districthut.islam.utils.AppManager;
import com.mirfatif.noorulhuda.databinding.ActivityHalalPlacesBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HalalPlacesActivity extends AppCompatActivity {

    ActivityHalalPlacesBinding binding;
    ArrayList<Place> places;
    HalalPlacesAdapter adapter;

    public Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppManager.checkTheme(this);
        super.onCreate(savedInstanceState);
        binding = ActivityHalalPlacesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        places = new ArrayList<>();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String type = getIntent().getStringExtra("type");

        if(type.equals("restaurant")) {
            getSupportActionBar().setTitle("Halal Places");
        } else {
            getSupportActionBar().setTitle("Nearby Masjids");
        }

        adapter=new HalalPlacesAdapter(this, places,type);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        binding.halalPlacesRecyler.setLayoutManager(manager);
        binding.halalPlacesRecyler.setAdapter(adapter);

        location = new Location("");
        GPSTracker gps = new GPSTracker(HalalPlacesActivity.this);
//        location.setLatitude(51.50734169385064);
//        location.setLongitude(-0.3054019107833187);
        if(gps.canGetLocation()) {
            loadNearByPlaces(gps.getLatitude(), gps.getLongitude(),type);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void loadNearByPlaces(double latitude, double longitude, String type)
//YOU Can change this type at your own will, e.g hospital, cafe, restaurant.... and see how it all works
    {
        //mMap.clear();
        Intent i = getIntent();
        float PROXIMITY_RADIUS = 500;
        String GOOGLE_BROWSER_API_KEY = "AIzaSyAVzgFRWuZVlW26OmkXPpWo9NvBlVYG8qQ";

        StringBuilder googlePlacesUrl =
                new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=").append(latitude).append(",").append(longitude);
        googlePlacesUrl.append("&radius=").append(PROXIMITY_RADIUS);
        googlePlacesUrl.append("&types=").append(type);
        if(type.equals("restaurant"))
            googlePlacesUrl.append("&keyword=").append("halal|halal restaurant|halal food|muslim food|muslim restaurant|pakistan food|pakistan|saudi");
        if(type.equals("mosque"))
            googlePlacesUrl.append("&keyword=").append("masjid|muslim prayer");

        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + GOOGLE_BROWSER_API_KEY);

        Log.e("url", googlePlacesUrl.toString());

        JsonObjectRequest request = new JsonObjectRequest(googlePlacesUrl.toString(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {
                        try {
                            if(result.getString("status").equals("OK")) {
                                JSONArray placesArray = result.getJSONArray("results");
                                for(int i = 0; i < placesArray.length(); i++) {
                                    JSONObject placesObj = placesArray.getJSONObject(i);
                                    boolean isOpen = false;
                                    if(placesObj.has("opening_hours")) {
                                        isOpen = placesObj.getJSONObject("opening_hours").getBoolean("open_now");
                                    } else {
                                        isOpen = false;
                                    }

                                    double rating = 0;
                                    int user_ratings_total = 0;
                                    if(placesObj.has("user_ratings_total")) {
                                        user_ratings_total = placesObj.getInt("user_ratings_total");
                                    }

                                    if(placesObj.has("rating")) {
                                        rating = placesObj.getDouble("rating");
                                    }

                                    Place place = new Place(
                                            placesObj.getString("business_status"),
                                            placesObj.getString("name"),
                                            placesObj.getString("vicinity"),
                                            placesObj.getJSONObject("geometry")
                                                    .getJSONObject("location").getDouble("lng"),
                                            placesObj.getJSONObject("geometry")
                                                    .getJSONObject("location").getDouble("lat"),
                                            user_ratings_total,
                                            rating,
                                            isOpen
                                    );
                                    places.add(place);
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                Log.e("No Items", "true");
                            }
                        } catch (JSONException e) {
                            Log.e("error", e.getLocalizedMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Log.e(TAG, "onErrorResponse: Error= " + error);
//                        Log.e(TAG, "onErrorResponse: Error= " + error.getMessage());
                    }
                });

        Volley.newRequestQueue(this).add(request);
    }

}