package com.districthut.islam.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirfatif.noorulhuda.R;
import com.mirfatif.noorulhuda.databinding.FragmentSendFundRequestBinding;


public class SendFundRequestFragment extends Fragment {


    public SendFundRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentSendFundRequestBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSendFundRequestBinding.inflate(inflater);

        return binding.getRoot();
    }
}