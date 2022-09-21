package com.districthut.islam.Activities.search;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mirfatif.noorulhuda.databinding.ActivitySearchDetailBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchDetailActivity extends AppCompatActivity {

    ActivitySearchDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        searchQuestion(getIntent().getIntExtra("question_id",0));
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    void searchQuestion(int id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://162.243.171.160:3000/questions/q/"+id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);

                        binding.answer.setVisibility(View.VISIBLE);
                        if (object.getString("status").equals("success")) {
                            binding.answer.setText(Html.fromHtml(object.getJSONObject("data").getString("answer")));
                        } else {

                        }
                    } catch (JSONException error) {
                        Log.e("err123", error.getLocalizedMessage() + "");
                    } finally {

                    }
                },
                error -> {
                    Log.e("err123434", error.getMessage() + "");
                });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
    }
}