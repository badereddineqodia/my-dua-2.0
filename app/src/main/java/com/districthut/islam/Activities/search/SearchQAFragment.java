package com.districthut.islam.Activities.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.districthut.islam.adapters.SearchAdapter;
import com.districthut.islam.models.SearchModel;
import com.mirfatif.noorulhuda.databinding.FragmentSearchQABinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchQAFragment extends Fragment {

    public SearchQAFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentSearchQABinding binding;
    ArrayList<SearchModel> searchModels;
    SearchAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchQABinding.inflate(inflater);

        searchModels = new ArrayList<>();
        adapter = new SearchAdapter(getContext(), searchModels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(adapter);

        binding.searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchQuestions(v.getText().toString());
                    return true;
                }
                return false;
            }
        });

        return binding.getRoot();
    }

    void searchQuestions(String query) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://162.243.171.160:3000/questions/" + query;
        binding.recyclerView.showShimmer();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        binding.recyclerView.hideShimmer();
                        if (object.getString("status").equals("success")) {
                            searchModels.clear();
                            JSONArray array = object.getJSONArray("data");
                            for(int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                SearchModel model = new SearchModel();
                                model.setId(obj.getInt("id"));
                                model.setText(obj.getString("text"));
                                model.setHref(obj.getString("href"));
                                searchModels.add(model);
                            }
                            adapter.notifyDataSetChanged();
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