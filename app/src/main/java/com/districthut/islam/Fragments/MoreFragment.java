package com.districthut.islam.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.districthut.islam.Activities.AllahNamesActivity;
import com.districthut.islam.Activities.DuaActivity;
import com.districthut.islam.Activities.HajjUmmrahActivity;
import com.districthut.islam.Activities.HalalPlacesActivity;
import com.districthut.islam.Activities.LiveMakkahActivity;
import com.districthut.islam.Activities.SettingsActivity;
import com.districthut.islam.Activities.ShahadaActivity;
import com.districthut.islam.Activities.StoriesActivity;
import com.districthut.islam.Activities.calendar.CalendarActivity;
import com.districthut.islam.adapters.MenuAdapter;
import com.districthut.islam.models.MoreModel;
import com.districthut.islam.models.SampleSearchModel;
import com.districthut.islam.utils.beadscounter.BeadsActivity;
import com.mirfatif.noorulhuda.R;
import com.districthut.islam.utils.DatabaseHelper;
import com.districthut.islam.utils.OnRecyclerViewItemClickListener;
import com.mirfatif.noorulhuda.databinding.FragmentMoreBinding;
import java.util.ArrayList;

public class MoreFragment extends Fragment implements OnRecyclerViewItemClickListener {

    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentMoreBinding binding;
    DatabaseHelper dbHeplper;
    private ArrayList<SampleSearchModel> AllSearchTopics;

    ArrayList<MoreModel> moreItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMoreBinding.inflate(inflater, container, false);


        moreItems = new ArrayList<>();

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            moreItems.add(new MoreModel(R.drawable.halal_dark,"Restaurants","nearby_restaurant"));
            moreItems.add(new MoreModel(R.drawable.masjid_dark,"Nearby Masjid","nearby_masjid"));
            moreItems.add(new MoreModel(R.drawable.qibla_dark,"Hajj & Umrah","hajj"));
            moreItems.add(new MoreModel(R.drawable.premium_dark,"Premium","premium"));
            moreItems.add(new MoreModel(R.drawable.calendar_dark,"Calender","calendar"));
            moreItems.add(new MoreModel(R.drawable.dua_dark,"Duas","duas"));
            moreItems.add(new MoreModel(R.drawable.shahdah_dark,"Shahadah","shahada"));
            moreItems.add(new MoreModel(R.drawable.names_dark,"99 Names","99_names"));
            moreItems.add(new MoreModel(R.drawable.live_dark,"Makkah Live","makkah_live"));
            moreItems.add(new MoreModel(R.drawable.tasbeeh_dark,"Tasbih","tasbih"));
            moreItems.add(new MoreModel(R.drawable.stories_dark,"Prophet Stories","prophet_stories"));
            moreItems.add(new MoreModel(R.drawable.hadees_dark,"Hadith", "hadith"));
            moreItems.add(new MoreModel(R.drawable.settings_dark,"Settings", "settings"));
        } else {
            moreItems.add(new MoreModel(R.drawable.halal_food,"Restaurants","nearby_restaurant"));
            moreItems.add(new MoreModel(R.drawable.mosque_near_me,"Nearby Masjid","nearby_masjid"));
            moreItems.add(new MoreModel(R.drawable.hajj_umrah,"Hajj & Umrah","hajj"));
            moreItems.add(new MoreModel(R.drawable.premium,"Premium","premium"));
            moreItems.add(new MoreModel(R.drawable.calender,"Calender","calendar"));
            moreItems.add(new MoreModel(R.drawable.duas,"Duas","duas"));
            moreItems.add(new MoreModel(R.drawable.shahadah,"Shahadah","shahada"));
            moreItems.add(new MoreModel(R.drawable._99_names_of_allah,"99 Names","99_names"));
            moreItems.add(new MoreModel(R.drawable.hajj_umrah,"Makkah Live","makkah_live"));
            moreItems.add(new MoreModel(R.drawable.tasbih_more,"Tasbih","tasbih"));
            moreItems.add(new MoreModel(R.drawable.stories_of_prophets,"Prophet Stories","prophet_stories"));
            moreItems.add(new MoreModel(R.drawable.hadith,"Hadith", "hadith"));
            moreItems.add(new MoreModel(R.drawable.settings_light,"Settings", "settings"));
        }
//        moreModels.add(new MoreModel(R.drawable.ic_info,"About"));
//        moreModels.add(new MoreModel(R.drawable.dua_icon_small,"Community"));



        MenuAdapter adapter = new MenuAdapter(getContext(), moreItems);
        adapter.setOnItemClickListener(this);

        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        binding.recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onRecyclerViewItemClicked(int position, int id) {
        MoreModel item = moreItems.get(position);
        switch (item.getCode()) {
            case "nearby_restaurant":
                startActivity(new Intent(getActivity(), HalalPlacesActivity.class).putExtra("type", "restaurant"));
                break;
            case "nearby_masjid":
                startActivity(new Intent(getActivity(), HalalPlacesActivity.class).putExtra("type", "mosque"));
                break;
            case "hajj":
                startActivity(new Intent(getActivity(), HajjUmmrahActivity.class));
                break;
            case "premium":
                break;
            case "calendar":
                startActivity(new Intent(getActivity(), CalendarActivity.class));
                break;
            case "duas":
                startActivity(new Intent(getActivity(), DuaActivity.class));
                break;
            case "shahada":
                startActivity(new Intent(getActivity(), ShahadaActivity.class));
                break;
            case "makkah_live":
                startActivity(new Intent(getActivity(), LiveMakkahActivity.class));
                break;
            case "tasbih":
                startActivity(new Intent(getActivity(), BeadsActivity.class));

                break;
            case "99_names":
                startActivity(new Intent(getActivity(), AllahNamesActivity.class));
                break;
            case "prophet_stories":
                startActivity(new Intent(getActivity(), StoriesActivity.class));
                break;
            case "settings":
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                break;

//            case 0:
//                startActivity(new Intent(getActivity(), DuaActivity.class));
//                break;
//            case 1:
//                startActivity(new Intent(getActivity(), TasbeehActivity.class));
//                break;
//            case 2:
//               // Snackbar.make(binding.getRoot(),"Qibla Locator will be available in next update. JazakAllah", Snackbar.LENGTH_LONG).show();
//                startActivity(new Intent(getActivity(), QiblaActivity.class));
//                break;
//            case 3:
//                startActivity(new Intent(getActivity(), AllahNamesActivity.class));
//                break;
//            case 4:
//                startActivity(new Intent(getActivity(), MuhammadNamesActivity.class));
//                break;
//            case 5:
//                startActivity(new Intent(getActivity(), KalimaActivity.class));
//                break;
//            case 6:
//                startActivity(new Intent(getActivity(), ShahadaActivity.class));
//                break;
//            case 7:
//                dbHeplper = new DatabaseHelper(getContext());
//                try{
//                    dbHeplper.createDataBase();
//                    dbHeplper.openDataBase();
//                }
//                catch (Exception e)
//                {
//                    Log.e("err", e.getLocalizedMessage());
//                }
//                AllSearchTopics = dbHeplper.GetAllSearchTopics("en");
//                new SimpleSearchDialogCompat(getActivity(), "Search...",
//                        "What are you looking for...?", null, AllSearchTopics,
//                        new SearchResultListener<SampleSearchModel>() {
//                            @Override
//                            public void onSelected(BaseSearchDialogCompat dialog,
//                                                   SampleSearchModel item, int position) {
//                                Intent i = new Intent(getActivity(),DuaActivity.class);
//                                i.putExtra("topic",item.getTitle());
//                                i.putExtra("lang","en");
//                                startActivity(i);
//                                dialog.dismiss();
//                            }
//                        }).show();
//                break;
//            case 8:
//                startActivity(new Intent(getActivity(), BookmarkActivity.class));
//                break;
//            case 9:
//                try{
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getActivity().getPackageName())));
//                }
//                catch (ActivityNotFoundException e){
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getActivity().getPackageName())));
//                }
//                break;
//            case 10:
//                ShareApp();
//                break;
//            case 11:
//                startActivity(new Intent(getActivity(), MyPreferencesActivity.class));
//                break;
//            case 12:
//                startActivity(new Intent(getActivity(), About.class));
//                break;
        }
    }

    public void ShareApp()
    {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "There\\'s an app where you just select your emotions and " +
                "current feelings (e.g ANGRY,CONFIDENT,INSECURE etc. and it gives " +
                "you an Ayat or Surah (in ARABIC, URDU &amp; ENGLISH) that " +
                "correlates with it.\n" + "(Available on Playstore)\n" +
                "https://play.google.com/store/apps/details?id=com.mianasad.myislam\n\n"+
                "Pass it to everyone you know. It\\s Awesome!";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}