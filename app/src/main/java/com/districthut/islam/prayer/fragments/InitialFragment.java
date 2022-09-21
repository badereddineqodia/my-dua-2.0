package com.districthut.islam.prayer.fragments;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mirfatif.noorulhuda.R;
import com.districthut.islam.prayer.util.AppSettings;


public class InitialFragment extends Fragment implements View.OnClickListener{
    InitialConfigFragment.OnOptionSelectedListener mCallback;
    TextView mConfigureNow;
    TextView mUseDefault;

    public InitialFragment() {
        // Required empty public constructor
    }

    public static InitialFragment newInstance() {
        InitialFragment fragment = new InitialFragment();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_initial_config, container, false);
        mConfigureNow = (TextView) view.findViewById(R.id.configure_now);
        mUseDefault = (TextView) view.findViewById(R.id.use_default);
        mConfigureNow.setOnClickListener(this);
        mUseDefault.setOnClickListener(this);
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InitialConfigFragment.OnOptionSelectedListener) {
            mCallback = (InitialConfigFragment.OnOptionSelectedListener) context;
        } else {
            throw new IllegalArgumentException("activity should implement OnOptionSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mConfigureNow.getId()) {
            mCallback.onConfigNowSelected(0);

        } else if (v.getId() == mUseDefault.getId()) {
            AppSettings.getInstance(getActivity()).set(AppSettings.Key.HAS_DEFAULT_SET, true);
            mCallback.onUseDefaultSelected();
        }
    }

    public interface OnOptionSelectedListener {
        void onConfigNowSelected(int num);
        void onUseDefaultSelected();
    }


}
