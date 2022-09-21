package com.districthut.islam.Activities;

import static com.districthut.islam.utils.Helper.API_KEY;
import static com.districthut.islam.utils.Helper.BASE_URL_API;
import static com.districthut.islam.utils.Helper.STORIES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.districthut.islam.adapters.HajjUmmrahAdapter;
import com.districthut.islam.models.GenericDataModel;
import com.districthut.islam.utils.AppManager;
import com.districthut.islam.utils.Helper;
import com.mirfatif.noorulhuda.databinding.ActivityStoriesBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StoriesActivity extends AppCompatActivity {

    ActivityStoriesBinding binding;
    ArrayList<GenericDataModel> items;
    HajjUmmrahAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppManager.checkTheme(this);
        super.onCreate(savedInstanceState);
        binding = ActivityStoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        items= new ArrayList<>();

        Helper.CURRENT_ACTIVITY = "stories";

        adapter=new HajjUmmrahAdapter(this,items);
        binding.storiesRecyler.setLayoutManager(new GridLayoutManager(this, 2));
        binding.storiesRecyler.setAdapter(adapter);

        binding.storiesRecyler.showShimmer();

        getData();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = BASE_URL_API + STORIES + API_KEY + "&sort_field=id&sort_order=ASC";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        binding.storiesRecyler.hideShimmer();
                        if (object.getBoolean("status")) {
                            JSONArray dataArray = object.getJSONObject("data").getJSONArray("stories");
                            for(int i =0; i < dataArray.length(); i++) {
                                JSONObject obj = dataArray.getJSONObject(i);
                                GenericDataModel model = new GenericDataModel(
                                        obj.getString("title"),
                                        obj.getString("detail"),
                                        obj.getString("image"),
                                        obj.getString("created_at"));
                                items.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Snackbar.make(binding.getRoot(), object.getString("message"), Snackbar.LENGTH_LONG).show();
                        }
                    } catch (JSONException error) {
                        Log.e("err", error.getLocalizedMessage() + "");
                    } finally {

                    }
                },
                error -> {
                    Log.e("err", error.getLocalizedMessage() + "");
                });

        queue.add(stringRequest);
    }
}