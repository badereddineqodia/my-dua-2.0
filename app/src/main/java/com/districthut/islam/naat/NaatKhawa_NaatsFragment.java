package com.districthut.islam.naat;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.districthut.islam.adapters.MoreAdapter;
import com.mirfatif.noorulhuda.R;
import com.districthut.islam.utils.DatabaseHelper;

import java.util.ArrayList;


public class NaatKhawa_NaatsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public NaatKhawa_NaatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NaatKhawa_NaatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NaatKhawa_NaatsFragment newInstance(String param1, String param2) {
        NaatKhawa_NaatsFragment fragment = new NaatKhawa_NaatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    ArrayList<NaatModel> naats;
    RecyclerView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_naat_khawa__naats, container, false);
        final DatabaseHelper dbHeplper = new DatabaseHelper(getContext());
        try{
            dbHeplper.createDataBase();
            dbHeplper.openDataBase();
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), "Database error!", Toast.LENGTH_SHORT).show();
        }

        String naatkhawa = "";
        String SEARCH_QUERY = "";
        if(!getArguments().getBoolean("search")) {
            naatkhawa = getArguments().getString("naatkhawa");
            ((NaatsActivity)getActivity()).getSupportActionBar().setTitle(naatkhawa);
        } else {
            SEARCH_QUERY = getArguments().getString("query");
            ((NaatsActivity) getActivity()).getSupportActionBar().setTitle(SEARCH_QUERY);
        }
        if(!SEARCH_QUERY.isEmpty()){
            naats = dbHeplper.SearchNaats(SEARCH_QUERY);
        } else
        {
            if(naatkhawa.equals(getString(R.string.view_all)))
                naats = dbHeplper.GetAllNaats();
            else if(naatkhawa.equals(getString(R.string.downloads)))
                naats = dbHeplper.GetDownloadedNaats();
            else if(naatkhawa.equals(getString(R.string.english)))
                naats = dbHeplper.GetEnglishNaats();
            else
                naats = dbHeplper.GetNaatsByNaatKhawa(naatkhawa);
        }




        lv=(RecyclerView) view.findViewById(R.id.naatKhawa_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        lv.setLayoutManager(linearLayoutManager);
        lv.setAdapter(new MoreAdapter(getActivity(),"en","naatsdetail",naats));
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
