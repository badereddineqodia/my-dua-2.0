package com.districthut.islam.naat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.adroitandroid.chipcloud.ChipCloud;
import com.adroitandroid.chipcloud.ChipListener;
import com.districthut.islam.adapters.CustomGrid;
import com.districthut.islam.models.SampleSearchModel;
import com.mirfatif.noorulhuda.R;


import java.util.ArrayList;


public class Naat_Main_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public Naat_Main_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Naat_Main_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Naat_Main_Fragment newInstance(String param1, String param2) {
        Naat_Main_Fragment fragment = new Naat_Main_Fragment();
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

    GridView grid;
    String[] web = {
            "Junaid Jamshed",
            "Syed Fasihuddin",
            "Saeed Hashmi",
            "Siddique Ismail",
            "Yousuf Memon",
            "Furqan Qadri",
            "Mushtaq Qadri",
            "Khurshid Ahmed",
            "Owais Qadri",
            "Imran Sheikh",
            "Muhammad Zahid",
            "Marghoub Hamdani",
            "Tahir Qadri",
            "Unknown",
            "Waheed Zafar",
            "Hafiz Abid",
            "Muhammad Shakeel",
            "Rehan Attari",
            "Shahbaz Qamar",
    } ;

    ArrayList<String> images;
    private ArrayList<SampleSearchModel> AllSearchTopics;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    String lang;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_naat__main_, container, false);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = pref.edit();

        lang = pref.getString("LANG","ur");

        images = new ArrayList<>();
        images.add("naatkhawa/junaid_jamshaid.jpg");
        images.add("naatkhawa/fasihuddin.png");
        images.add("naatkhawa/saeed_hashmi.jpg");
        images.add("naatkhawa/islmail.jpg");
        images.add("naatkhawa/yusufmemon.jpg");
        images.add("naatkhawa/syedfurqanqadri.jpg");
        images.add("naatkhawa/mushtaq_qadri.jpg");
        images.add("naatkhawa/khursheedahmed.jpg");
        images.add("naatkhawa/owais_raza_qadri.jpg");
        images.add("naatkhawa/imranatari.jpg");
        images.add("naatkhawa/zahirattar.jpg");
        images.add("naatkhawa/marghoobhamdani.jpg");
        images.add("naatkhawa/tahirqadri.jpg");
        images.add("naatkhawa/unknown.png");
        images.add("naatkhawa/waheedzafar.jpg");
        images.add("naatkhawa/abidraza.jpg");
        images.add("naatkhawa/shakeelatari.jpg");
        images.add("naatkhawa/rehanqadri.jpg");
        images.add("naatkhawa/qamarfareedi.jpg");
        final String[] array = {
                getString(R.string.downloads),
                getString(R.string.english),
                getString(R.string.view_all)
        };


        CustomGrid adapter = new CustomGrid(getActivity(), web, images);
        grid=(GridView)view.findViewById(R.id.grid);
        final ChipCloud chipCloud = (ChipCloud) view.findViewById(R.id.chip_cloud);
        new ChipCloud.Configure()
                .chipCloud(chipCloud)
                .selectTransitionMS(500)
                .deselectTransitionMS(250)
                .labels(array)
                .mode(ChipCloud.Mode.SINGLE)
                .allCaps(false)
                .gravity(ChipCloud.Gravity.CENTER)
                .textSize(getResources().getDimensionPixelSize(R.dimen.chiptextsize))
                .verticalSpacing(getResources().getDimensionPixelSize(R.dimen.vertical_spacing))
                .minHorizontalSpacing(getResources().getDimensionPixelSize(R.dimen.min_horizontal_spacing))

                .chipListener(new ChipListener() {
                    @Override
                    public void chipSelected(int index) {
                        if(array[index].equals(getString(R.string.view_all))) {
                            NaatKhawa_NaatsFragment naatkhawafragment = new NaatKhawa_NaatsFragment();
                            Bundle args = new Bundle();
                            args.putBoolean("search",false);
                            args.putString("naatkhawa",array[index]);
                            naatkhawafragment.setArguments(args);
                            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.frameLayout, naatkhawafragment).addToBackStack(null).commit();
                        }
                        if(array[index].equals(getString(R.string.english))) {
                            NaatKhawa_NaatsFragment naatkhawafragment = new NaatKhawa_NaatsFragment();
                            Bundle args = new Bundle();
                            args.putBoolean("search",false);
                            args.putString("naatkhawa",array[index]);
                            naatkhawafragment.setArguments(args);
                            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.frameLayout, naatkhawafragment).addToBackStack(null).commit();
                        }
                        if(array[index].equals(getString(R.string.downloads))) {
                            NaatKhawa_NaatsFragment naatkhawafragment = new NaatKhawa_NaatsFragment();
                            Bundle args = new Bundle();
                            args.putBoolean("search",false);
                            args.putString("naatkhawa",array[index]);
                            naatkhawafragment.setArguments(args);
                            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.frameLayout, naatkhawafragment).addToBackStack(null).commit();
                        }
                        /*if(array[index].equals(getString(R.string.search))){
                            final DatabaseHelper helper = new DatabaseHelper(getContext());
                            try {
                                helper.createDataBase();
                                helper.openDataBase();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            AllSearchTopics = helper.GetNaatSearchSample();
                            new SimpleSearchDialogCompat(getActivity(), "asdsd",
                                    " sdsd", null, AllSearchTopics,
                                    new SearchResultListener<SampleSearchModel>() {
                                        @Override
                                        public void onSelected(BaseSearchDialogCompat dialog,
                                                               SampleSearchModel item, int position) {
                                            Intent i = new Intent(getActivity(),PlayNaatActivity.class);
                                            i.putExtra("title",item.getTitle());
                                            i.putExtra("url",helper.GetNaatUrlByName(item.getTitle()));
                                            startActivity(i);
                                            chipCloud.setSelected(false);
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }*/
                    }
                    @Override
                    public void chipDeselected(int index) {
                        //...
                    }
                })
                .build();

        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                NaatKhawa_NaatsFragment naatkhawafragment = new NaatKhawa_NaatsFragment();
                Bundle args = new Bundle();
                args.putBoolean("search",false);
                args.putString("naatkhawa",web[position]);
                naatkhawafragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.frameLayout, naatkhawafragment).addToBackStack(null).commit();
            }
        });

        /*
        final ArrayList<String> menuItems = new ArrayList<>();
        menuItems.add("Junaid Jamshaid");
        menuItems.add("Aamir Liaquat");
        menuItems.add("Awais Raza Qadri");
        menuItems.add("Saeed Hashmi");

        RecyclerView lv=(RecyclerView) view.findViewById(R.id.naatKhawa_rv);
        RecycleClick.addTo(lv).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent i;
                NaatKhawa_NaatsFragment naatkhawafragment = new NaatKhawa_NaatsFragment();
                Bundle args = new Bundle();
                args.putString("naatkhawa",menuItems.get(position));
                naatkhawafragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.frameLayout, naatkhawafragment).addToBackStack(null).commit();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        lv.setLayoutManager(linearLayoutManager);
        lv.setAdapter(new MoreAdapter(getActivity(), menuItems,"en","naats"));
        */
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
