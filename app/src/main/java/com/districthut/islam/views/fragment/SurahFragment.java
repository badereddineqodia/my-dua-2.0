package com.districthut.islam.views.fragment;

import static com.mirfatif.noorulhuda.prefs.MySettings.SETTINGS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.districthut.islam.Activities.Quran.adapter.SurahAdapter;
import com.districthut.islam.Activities.Quran.model.Surah;
import com.districthut.islam.naat.util.Constants;
import com.districthut.islam.naat.util.SDCardManager;
import com.districthut.islam.utils.DatabaseHelper;
import com.districthut.islam.utils.datasource.SurahDataSource;
import com.mirfatif.noorulhuda.R;
import com.mirfatif.noorulhuda.databinding.FragmentSurahBinding;
import com.mirfatif.noorulhuda.db.DbBuilder;
import com.mirfatif.noorulhuda.quran.MainActivity;
import com.mirfatif.noorulhuda.ui.dialog.AlertDialogFragment;
import com.mirfatif.noorulhuda.util.Utils;

import java.util.ArrayList;


public class SurahFragment extends Fragment {


    public SurahFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentSurahBinding binding;

    SharedPreferences.Editor editor;
    SharedPreferences pref;
    Long lastsura,lastaya;
    String lastSura_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSurahBinding.inflate(inflater, container, false);

        pref = getActivity().getSharedPreferences("QuranPref", 0); // 0 - for private mode
        editor = pref.edit();



        if(SDCardManager.isSdCardWritable()) {
            SDCardManager.CreateFolders(Constants.QURAN_PATH);
            SDCardManager.CreateFolders(Constants.TILAWAT_PATH);
            SDCardManager.CreateFolders(Constants.TRANSLATIONS_PATH);
        }

        DatabaseHelper dbHelper= new DatabaseHelper(getContext());
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
            SurahDataSource dataSource = new SurahDataSource(getContext());
            ArrayList<Surah> surahs = dataSource.getEnglishSurahArrayList();
            SurahAdapter surahAdapter = new SurahAdapter(surahs,getContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            binding.surahRecyclerView.setLayoutManager(linearLayoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.surahRecyclerView.getContext(),linearLayoutManager.getOrientation());
            binding.surahRecyclerView.addItemDecoration(dividerItemDecoration);
            binding.surahRecyclerView.setAdapter(surahAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }



        return binding.getRoot();
    }





//    void CheckLastSeen(){
//        lastSura_name = pref.getString("lastsura_arabic","");
//        if(!lastSura_name.equals("")) {
//            binding.lastSeenLayout.setVisibility(View.VISIBLE);
//            lastsura = pref.getLong("lastSura",0);
//            lastaya = pref.getLong("lastAya",0);
//            binding.suraArabicLastseen.setText(lastSura_name);
//            binding.suraAyaLastseen.setText("Sura: " +lastsura + " Aya:" + lastaya);
//
//            binding.lastSeenLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i = new Intent(getContext(), SurahDetailActivity.class);
//                    i.putExtra("surah_id",lastsura);
//                    i.putExtra("filepath","");
//                    i.putExtra("surahName",lastSura_name);
//                    i.putExtra("lastaya",lastaya);
//                    i.putExtra("play",false);
//                    startActivity(i);
//                }
//            });
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
//        CheckLastSeen();
    }
}