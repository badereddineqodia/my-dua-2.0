package com.districthut.islam.Fragments;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.districthut.islam.utils.DatabaseHelper;
import com.mirfatif.noorulhuda.R;
import com.districthut.islam.adapters.CategoriesListAdapter;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DuaCategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DuaCategoriesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DuaCategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @paramparam1 Parameter 1.
     * @paramparam2 Parameter 2.
     * @return A new instance of fragment DuaCategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DuaCategoriesFragment newInstance() {
        DuaCategoriesFragment fragment = new DuaCategoriesFragment();

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

    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dua_categories, container, false);
        final DatabaseHelper dbHeplper = new DatabaseHelper(getContext());
        try{
            dbHeplper.createDataBase();
            dbHeplper.openDataBase();
        }
        catch (Exception e)
        {

        }
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String lang = SP.getString("lang", "en");
        ArrayList<String> o = dbHeplper.getCategories(lang);
        String [] prgmNameList = o.toArray(new String[0]);




        int prgmImages[] = {
                R.drawable.c1,
                R.drawable.search,
                R.drawable.c3,
                R.drawable.c4,
                R.drawable.c5,
                R.drawable.c6,
                R.drawable.c7,
                R.drawable.c8,
                R.drawable.c9,
                R.drawable.c10,
                R.drawable.c11,
                R.drawable.c12,
                R.drawable.c13,
                R.drawable.c14,
                R.drawable.c15,
                R.drawable.c16,
                R.drawable.c17,
                R.drawable.c18,
                R.drawable.c19,
                R.drawable.c20,
                R.drawable.c21,
                R.drawable.c22,
                R.drawable.c23,
                R.drawable.c24,
                R.drawable.c25,
                R.drawable.c26,
                R.drawable.c27,
                R.drawable.c28,
                R.drawable.c29,
                R.drawable.c30,
                R.drawable.c31,
                R.drawable.c32,
                R.drawable.c33,
                R.drawable.c34,
                R.drawable.c35,
                R.drawable.c36,
                R.drawable.c37,
                R.drawable.c38};








        lv=(ListView) view.findViewById(R.id.listView);
        lv.setAdapter(new CategoriesListAdapter(getActivity(), prgmNameList,prgmImages,lang));
        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
