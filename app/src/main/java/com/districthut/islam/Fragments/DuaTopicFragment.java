package com.districthut.islam.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.districthut.islam.adapters.DuaTopicAdapter;
import com.districthut.islam.utils.DatabaseHelper;
import com.districthut.islam.Activities.DuaActivity;
import com.mirfatif.noorulhuda.databinding.FragmentDuaTopicBinding;

import java.util.ArrayList;


public class DuaTopicFragment extends Fragment {

    private String category;
    private String language;


    public DuaTopicFragment() {
        // Required empty public constructor
    }

    public static DuaTopicFragment newInstance(String category, String lang) {
        DuaTopicFragment fragment = new DuaTopicFragment();
        Bundle args = new Bundle();
        args.putString("category", category);
        args.putString("lang", lang);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString("category");
            language = getArguments().getString("lang");
        }

    }

    FragmentDuaTopicBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((DuaActivity)getActivity()).getSupportActionBar().setTitle(category);
        binding = FragmentDuaTopicBinding.inflate(inflater, container, false);

        ArrayList<String> topics = new ArrayList<>();
        DuaTopicAdapter adapter = new DuaTopicAdapter(getContext(),topics, language);
        final DatabaseHelper dbHeplper = new DatabaseHelper(getContext());
        try{
            dbHeplper.createDataBase();
            dbHeplper.openDataBase();
        }
        catch (Exception e)
        {

        }

        if(category.contains("All Duas") || category.contains("Semua Doa") || category.contains("تمام دعائیں"))
            topics.addAll(dbHeplper.GetAllTopics(language));
        else
            topics.addAll(dbHeplper.GetTopicsByCategory(category,language));

        binding.duaListView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.duaListView.setAdapter(adapter);

//        ListView lv = (ListView)view.findViewById(R.id.duaListView);
//        Bundle bundle=getArguments();
//        lv.setAdapter(new DuaAdapter(getActivity(), category, language));
        return binding.getRoot();
    }

}
