package com.districthut.islam.prayer.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mirfatif.noorulhuda.R;
import com.districthut.islam.UserPreference;
import com.districthut.islam.prayer.Constants;
import com.districthut.islam.prayer.OnboardingActivity;
import com.districthut.islam.prayer.SetAlarmActivity;
import com.districthut.islam.prayer.scheduler.RamadanAlarmReceiver;
import com.districthut.islam.prayer.scheduler.SalaatAlarmReceiver;
import com.districthut.islam.prayer.util.AppSettings;
import com.districthut.islam.prayer.util.PrayTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class PrayerTimesFragment extends Fragment implements Constants,View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private UserPreference cache;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static boolean sIsAlarmInit = false;
    int mIndex = 0;
    Location mLastLocation;
    TextView mAlarm;
    View mRamadanContainer;
    double locationLatitude=0;
    double locationLongitude=0;
    public PrayerTimesFragment() {
        // Required empty public constructor
    }

    public static PrayerTimesFragment newInstance(int index, Location location) {
        PrayerTimesFragment fragment = new PrayerTimesFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_ALARM_INDEX, index);
        args.putParcelable(EXTRA_LAST_LOCATION, location);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cache=UserPreference.getInstance(getContext());

        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mIndex = getArguments().getInt(EXTRA_ALARM_INDEX);
            mLastLocation = (Location) getArguments().getParcelable(EXTRA_LAST_LOCATION);
        }
    }
    String lang;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    ImageView fajar,zuhr,asr,maghrib,isha;

    AppSettings settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()); // 0 - for private mode
        editor = pref.edit();
        lang = pref.getString("LANG", "");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prayer_times, container, false);



        init(view);
        settings = AppSettings.getInstance(getActivity());
        fajar = view.findViewById(R.id.fajaricon);
        fajar.setOnClickListener(this);
        zuhr = view.findViewById(R.id.zuhricon);
        zuhr.setOnClickListener(this);
        asr = view.findViewById(R.id.asricon);
        asr.setOnClickListener(this);
        maghrib = view.findViewById(R.id.magribicon);
        maghrib.setOnClickListener(this);
        isha = view.findViewById(R.id.ishaicon);
        isha.setOnClickListener(this);

        boolean fajarstatus = getPrayerAlarmStatus(0);
        if(fajarstatus)
            fajar.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.volume));
        else
            fajar.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.mute));
        boolean zuhrstatus = getPrayerAlarmStatus(1);
        if(zuhrstatus)
            zuhr.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.volume));
        else
            zuhr.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.mute));
        boolean asrstatus = getPrayerAlarmStatus(2);
        if(asrstatus)
            asr.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.volume));
        else
            asr.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.mute));
        boolean magribstatus = getPrayerAlarmStatus(3);
        if(magribstatus)
            maghrib.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.volume));
        else
            maghrib.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.mute));
        boolean ishastatus = getPrayerAlarmStatus(4);
        if(ishastatus)
            isha.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.volume));
        else
            isha.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.mute));



        return view;
    }

    @Override
    public void onClick(View v) {
        int index = Integer.parseInt(v.getTag() +"");
        boolean isSet = getPrayerAlarmStatus(index);
        setPrayerAlarmStatus(index, !isSet);
        v.setSelected(!isSet);
        if(getPrayerAlarmStatus(index)){
            ((ImageView)v).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.volume));
        } else {
            ((ImageView)v).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.mute));
        }
    }

    private void setPrayerAlarmStatus(int prayerIndex, boolean isOn) {
        String key = getPrayerKeyFromIndex(prayerIndex);

        if (key != null) {
            settings.set(key, isOn);
        }
    }

    private boolean getPrayerAlarmStatus(int prayerIndex) {
        String key = getPrayerKeyFromIndex(prayerIndex);

        if (key != null) {
            return settings.getBoolean(key);
        }
        return false;
    }

    private String getPrayerKeyFromIndex(int prayerIndex) {
        String key = null;
        switch (prayerIndex) {
            case 0:
                key = settings.getKeyFor(AppSettings.Key.IS_FAJR_ALARM_SET, mIndex);
                break;
            case 1:
                key = settings.getKeyFor(AppSettings.Key.IS_DHUHR_ALARM_SET, mIndex);
                break;
            case 2:
                key = settings.getKeyFor(AppSettings.Key.IS_ASR_ALARM_SET, mIndex);
                break;
            case 3:
                key = settings.getKeyFor(AppSettings.Key.IS_MAGHRIB_ALARM_SET, mIndex);
                break;
            case 4:
                key = settings.getKeyFor(AppSettings.Key.IS_ISHA_ALARM_SET, mIndex);
                break;
        }
        return key;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        MenuInflater inflat = inflater;
//        inflater.inflate(R.menu.prayer_menu, menu);
//        super.onCreateOptionsMenu(menu, inflat);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.action_settings:
                Intent i = new Intent(getActivity(), OnboardingActivity.class);
                startActivity(i);
                // Do Fragment menu item stuff here
                return true;

            default:
                break;
        }

        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity()); // 0 - for private mode
        editor = pref.edit();
        lang = pref.getString("LANG", "");
        Configuration config = context.getResources().getConfiguration();
        if (! "".equals(lang) && ! config.locale.getLanguage().equals(lang)) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    protected void init(View view) {
        // In future releases we will add more cards.
        // Then we'll need to do this for each card.
        // For now it's included in the layout which
        // makes it easier to work with the layout editor.
        // inflater.inflate(R.layout.view_prayer_times, timesContainer, true);

        AppSettings settings = AppSettings.getInstance(getActivity());
        locationLatitude=  settings.getLatFor(0);
        locationLongitude=  settings.getLngFor(0);
        if (locationLatitude == 0&&locationLongitude==0) {
            return;
        }

//Toolbar will now take on default Action Bar characteristics
       // LinkedHashMap<String, String> prayerTimes = PrayTime.getPrayerTimes(getActivity(), mIndex, mLastLocation.getLatitude(), mLastLocation.getLongitude());
        LinkedHashMap<String, String> prayerTimes =
                PrayTime.getPrayerTimes(getActivity(), mIndex, locationLatitude, locationLongitude);



        //TextView title = (TextView) view.findViewById(R.id.card_title);
        //title.setText(TimeZone.getDefault().getID());

        TextView fajr = (TextView) view.findViewById(R.id.fajr);
        TextView dhuhr = (TextView) view.findViewById(R.id.dhuhr);
        TextView asr = (TextView) view.findViewById(R.id.asr);
        TextView maghrib = (TextView) view.findViewById(R.id.maghrib);
        TextView isha = (TextView) view.findViewById(R.id.isha);
        TextView sunrise = (TextView) view.findViewById(R.id.sunrise);
        TextView sunset = (TextView) view.findViewById(R.id.sunset);

        LinearLayout fajarCard = (LinearLayout)view.findViewById(R.id.fajarCard);
        LinearLayout sunriseCard = (LinearLayout)view.findViewById(R.id.sunriseCard);
        LinearLayout zuhrCard = (LinearLayout)view.findViewById(R.id.zuhrCard);
        LinearLayout asrCard = (LinearLayout)view.findViewById(R.id.asrCard);
        LinearLayout maghribCard = (LinearLayout)view.findViewById(R.id.magribCard);
        LinearLayout sunsetCard = (LinearLayout)view.findViewById(R.id.sunsetCard);
        LinearLayout ishaCard = (LinearLayout)view.findViewById(R.id.ishaCard);

        switch (GetPrayerName())
        {
            case "Fajr":
                fajarCard.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            case "Sunrise":
                sunriseCard.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            case "Dhuhr":
                zuhrCard.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            case "Asr":
                asrCard.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            case "Sunset":
                maghribCard.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            case "Maghrib":
                maghribCard.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            case "Isha":
                ishaCard.setBackgroundColor(getResources().getColor(R.color.green));
                break;
        }

        mAlarm = (TextView) view.findViewById(R.id.alarm);
        mRamadanContainer = view.findViewById(R.id.ramadan_container);

        fajr.setText(prayerTimes.get(String.valueOf(fajr.getTag())));
        dhuhr.setText(prayerTimes.get(String.valueOf(dhuhr.getTag())));
        asr.setText(prayerTimes.get(String.valueOf(asr.getTag())));
        maghrib.setText(prayerTimes.get(String.valueOf(maghrib.getTag())));
        isha.setText(prayerTimes.get(String.valueOf(isha.getTag())));
        sunrise.setText(prayerTimes.get(String.valueOf(sunrise.getTag())));
        sunset.setText(prayerTimes.get(String.valueOf(sunset.getTag())));

        //set text for the first card.
        setAlarmButtonText(mAlarm, mIndex);
        setAlarmButtonClickListener(mAlarm, mIndex);

        if (!sIsAlarmInit) {
            if (AppSettings.getInstance().isDefaultSet()) {
               // AppSettings.getInstance().setLatFor(mIndex, mLastLocation.getLatitude());
                AppSettings.getInstance().setLatFor(mIndex, locationLatitude);
                //AppSettings.getInstance().setLngFor(mIndex, mLastLocation.getLongitude());
                AppSettings.getInstance().setLngFor(mIndex, locationLongitude);
                updateAlarmStatus();
                sIsAlarmInit = true;
            }
        }
    }

    private void setAlarmButtonText(TextView button, int index) {
        boolean isAlarmSet = AppSettings.getInstance(getActivity()).isAlarmSetFor(index);
        int isAlarmSetInt = isAlarmSet ? 0 : 1;
        String buttonText = getResources().getQuantityString(R.plurals.button_alarm, isAlarmSetInt);
        button.setText(buttonText);
        boolean isRamadanSet = AppSettings.getInstance(getActivity()).getBoolean(AppSettings.Key.IS_RAMADAN);
        mRamadanContainer.setVisibility(isRamadanSet? View.VISIBLE : View.GONE);
    }

    private void setAlarmButtonClickListener(TextView alarm, int index) {
        alarm.setOnClickListener(new View.OnClickListener() {
            int index = 0;


            @Override
            public void onClick(View v) {
                AppSettings settings = AppSettings.getInstance(getActivity());
                settings.setLatFor(mIndex, mLastLocation.getLatitude());
                settings.setLngFor(mIndex, mLastLocation.getLongitude());
                Intent intent = new Intent(getActivity(), SetAlarmActivity.class);
                intent.putExtra(EXTRA_ALARM_INDEX, index);
                startActivityForResult(intent, REQUEST_SET_ALARM);
            }

            public View.OnClickListener init(int index) {
                this.index = index;
                return this;
            }

        }.init(index));
    }

    public void setLocation(Location location) {
        mLastLocation = location;
        AppSettings.getInstance().setLatFor(mIndex, location.getLatitude());
        AppSettings.getInstance().setLngFor(mIndex, location.getLatitude());
        if (isAdded()) {
            init(getView());
        }
    }

    private void updateAlarmStatus() {
        setAlarmButtonText(mAlarm, mIndex);

        AppSettings settings = AppSettings.getInstance(getActivity());

        SalaatAlarmReceiver sar = new SalaatAlarmReceiver();
        boolean isAlarmSet = settings.isAlarmSetFor(mIndex);
        sar.cancelAlarm(getActivity());
        if (isAlarmSet) {
            sar.setAlarm(getActivity());
        }


        RamadanAlarmReceiver rar = new RamadanAlarmReceiver();
        boolean isRamadanAlarmSet = settings.getBoolean(AppSettings.Key.IS_RAMADAN);
        rar.cancelAlarm(getActivity());
        if (isRamadanAlarmSet) {
            rar.setAlarm(getActivity());
        }
    }

    String GetPrayerName() {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTimeInMillis(System.currentTimeMillis());
        // Set the alarm's trigger time to 8:30 a.m.

        int alarmIndex = 0;

        Calendar then = Calendar.getInstance(TimeZone.getDefault());
        then.setTimeInMillis(System.currentTimeMillis());

        AppSettings settings = AppSettings.getInstance(getActivity());


        double lat = settings.getLatFor(alarmIndex);
        double lng = settings.getLngFor(alarmIndex);
        LinkedHashMap<String, String> prayerTimes = PrayTime.getPrayerTimes(getActivity(), alarmIndex, lat, lng, PrayTime.TIME_24);
        List<String> prayerNames = new ArrayList<>(prayerTimes.keySet());

        String nameOfPrayerFound = null;
        for (String prayer : prayerNames) {

            then = getCalendarFromPrayerTime(then, prayerTimes.get(prayer));
            nameOfPrayerFound = prayer;
            if (then.after(now)) {
                nameOfPrayerFound = prayer;
                break;
            }
        }

        return nameOfPrayerFound;
    }

    private Calendar getCalendarFromPrayerTime(Calendar cal, String prayerTime) {
        String[] time = prayerTime.split(":");
        cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(time[0]));
        cal.set(Calendar.MINUTE, Integer.valueOf(time[1]));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SET_ALARM) {
            if (resultCode == Activity.RESULT_OK) {
                updateAlarmStatus();
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
