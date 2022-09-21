package com.districthut.islam.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.districthut.islam.models.FundRequest;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.mirfatif.noorulhuda.R;
import com.mirfatif.noorulhuda.databinding.FragmentFundBinding;
import com.mirfatif.noorulhuda.databinding.SampleFundsRowBinding;
import com.thefinestartist.finestwebview.FinestWebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FundFragment extends Fragment {

    FragmentFundBinding binding;
    ArrayList<FundRequest> fundRequests;
    FundAdapter adapter;

    public FundFragment() {
        // Required empty public constructor
        fundRequests = new ArrayList<>();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFundBinding.inflate(inflater);
        binding.toolbar.setTitle("Fund Requests");
        binding.toolbar.inflateMenu(R.menu.fund_menu);
        binding.toolbar.setNavigationOnClickListener(c-> {
            binding.toolbar.setTitle("Fund Requests");
            binding.toolbar.setNavigationIcon(null);
            getChildFragmentManager().popBackStack();
            binding.toolbar.getMenu().findItem(R.id.sendBtn).setVisible(true);
        });
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.sendBtn:
                        binding.toolbar.setNavigationIcon(R.drawable.back);
                        binding.toolbar.setTitle("Send Request");
                        binding.toolbar.getMenu().findItem(R.id.sendBtn).setVisible(false);
                        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                        transaction.add(R.id.fragmentContainer, new SendFundRequestFragment());
                        transaction.addToBackStack("");
                        transaction.commit();
                        break;
                }
                return false;
            }
        });

        adapter = new FundAdapter(getContext(), fundRequests);
        binding.recyclerView.setAdapter(adapter);
        getFundRequests();
        return binding.getRoot();
    }

    void getFundRequests() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://apps.districthut.com/islam/api/fund_requests/all?X-Api-Key=EFA7DA6DCA8A971858AEC912FE5A6B6A";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        if(object.getBoolean("status")) {
                            JSONArray fund_requests = object.getJSONObject("data").getJSONArray("fund_requests");
                            for (int i = 0; i < fund_requests.length(); i++) {
                                JSONObject obj = fund_requests.getJSONObject(i);
                                FundRequest request = new FundRequest(
                                  obj.getString("cause"),
                                        obj.getString("description"),
                                        obj.getString("goal"),
                                        obj.getString("image"),
                                        obj.getString("link"),
                                        obj.getString("location")
                                );
                                fundRequests.add(request);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Snackbar.make(binding.getRoot(), object.getString("message"), Snackbar.LENGTH_LONG).show();
                        }

                    } catch (JSONException error) {
                        showException(error);
                    }
                }, error -> showException(error));

        queue.add(stringRequest);
    }

    void showException(Exception error){
        if(error.getLocalizedMessage() == null || error.getLocalizedMessage().equals("")) {
            Snackbar.make(binding.getRoot(),"Not successful", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(binding.getRoot(),error.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    public class FundAdapter extends RecyclerView.Adapter<FundAdapter.FundViewHolder> {
        Context context;
        ArrayList<FundRequest> fundRequests;

        public FundAdapter(Context context, ArrayList<FundRequest> fundRequests) {
            this.context = context;
            this.fundRequests = fundRequests;
        }

        @NonNull
        @Override
        public FundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FundViewHolder(LayoutInflater.from(context).inflate(R.layout.sample_funds_row, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull FundViewHolder holder, int position) {
            FundRequest request = fundRequests.get(position);
            holder.binding.title.setText(request.getCause());
            Glide.with(context)
                    .load("https://apps.districthut.com/islam/uploads/fund_requests/" + request.getImage())
                    .into(holder.binding.image);
            holder.binding.location.setText(request.getLocation());

            holder.itemView.setOnClickListener(c-> {
                new FinestWebView.Builder(context)
                        .titleDefault("Loading...")
                        .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
                        .gradientDivider(false)
                        .iconDisabledColorRes(R.color.gray)
                        .iconPressedColorRes(R.color.black)
                        .backPressToClose(false)
                        .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit, R.anim.activity_close_enter, R.anim.activity_close_exit)
                        .show(request.getLink());
//                startActivity(
//                        new Intent(context, WebActivity.class)
//                        .putExtra("cause", request.getCause())
//                        .putExtra("link", request.getLink())
//                );
            });
        }

        @Override
        public int getItemCount() {
            return fundRequests.size();
        }

        public class FundViewHolder extends RecyclerView.ViewHolder {
            SampleFundsRowBinding binding;
            public FundViewHolder(@NonNull View itemView) {
                super(itemView);
                binding = SampleFundsRowBinding.bind(itemView);
            }
        }
    }
}