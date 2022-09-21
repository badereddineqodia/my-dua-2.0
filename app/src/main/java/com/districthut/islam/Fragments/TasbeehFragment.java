package com.districthut.islam.Fragments;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirfatif.noorulhuda.databinding.FragmentTasbeehBinding;

import static android.content.Context.MODE_PRIVATE;


public class TasbeehFragment extends Fragment {


    public TasbeehFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentTasbeehBinding binding;
    int i = 0;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Typeface typeface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTasbeehBinding.inflate(inflater, container, false);

        preferences = getActivity().getSharedPreferences("Tasbeeh", MODE_PRIVATE);
        editor = preferences.edit();
        typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Al-Mushaf.ttf");

        binding.arabic.setTypeface(typeface);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                editor.putInt("counts", i);
                editor.commit();
                binding.counter.setText(String.valueOf(i));
            }
        });

        binding.resetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = 0;
                editor.putInt("counts", i);
                editor.commit();
                binding.counter.setText(String.valueOf(i));
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        i = preferences.getInt("counts",0);
        binding.counter.setText(String.valueOf(i));
    }
}