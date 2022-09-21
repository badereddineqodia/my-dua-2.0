package com.districthut.islam.Activities;

import static com.mirfatif.noorulhuda.prayer.PrayerData.METHOD_LOCATIONS;
import static com.mirfatif.noorulhuda.prefs.MySettings.SETTINGS;

import android.Manifest;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import com.batoulapps.adhan.Coordinates;
import com.bumptech.glide.Glide;
import com.districthut.islam.Activities.search.SearchQAFragment;
import com.districthut.islam.Fragments.FundFragment;
import com.districthut.islam.prayer.scheduler.SalaatAlarmReceiver;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import com.mirfatif.noorulhuda.BuildConfig;
import com.districthut.islam.Fragments.HomeFragment;
import com.districthut.islam.Fragments.MoreFragment;
import com.districthut.islam.Fragments.SurahFragment;
import com.districthut.islam.models.GPSTracker;
import com.districthut.islam.UserPreference;
import com.districthut.islam.utils.DatabaseHelper;
import com.mirfatif.noorulhuda.R;
import com.districthut.islam.models.SampleSearchModel;
import com.mirfatif.noorulhuda.databinding.ActivityMainBinding;
import com.districthut.islam.naat.NaatsActivity;
import com.districthut.islam.prayer.fragments.PrayerTimesFragment;
import com.districthut.islam.prayer.util.AppSettings;
import com.hk.kbottomnavigation.KBottomNavigation;
import com.yayandroid.locationmanager.base.LocationBaseActivity;
import com.yayandroid.locationmanager.configuration.DefaultProviderConfiguration;
import com.yayandroid.locationmanager.configuration.GooglePlayServicesConfiguration;
import com.yayandroid.locationmanager.configuration.LocationConfiguration;
import com.yayandroid.locationmanager.configuration.PermissionConfiguration;
import com.yayandroid.locationmanager.constants.ProviderType;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class HomeActivity extends LocationBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        EasyPermissions.PermissionCallbacks {

    public static Location location;

    AppSettings settings;

    BottomNavigationView bottomNavigationView;
    //private AdView mAdView;
    DatabaseHelper dbHeplper;
    private ArrayList<SampleSearchModel> AllSearchTopics;
    String Language = "";
    SharedPreferences SP ;
    private PendingIntent pendingIntent;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public ActivityMainBinding binding;
    HomeFragment homeFragment;
    SurahFragment surahFragment;
    FirebaseRemoteConfig mFirebaseRemoteConfig;
    //InterstitialAd interstitialAd;
    GPSTracker gps;
    private UserPreference cache;
    private static final int REQUEST_CODE_PERMISSION = 11;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    boolean isLocationAllow=false;
    double locationLatitude=0;
    double locationLongitude=0;
//    @Override
//    public LocationConfiguration getLocationConfiguration() {
//        return Configurations.defaultConfiguration("Please allow location permissions to get accurate prayer timings.", "Would you mind to turn GPS on?");
//    }

    private final int ID_HOME = 1;
    private final int ID_QURAN = 2;
    private final int ID_SEARCH = 3;
    private final int ID_DONATION = 4;
    private final int ID_MORE = 5;


    LocationConfiguration awesomeConfiguration = new LocationConfiguration.Builder()
            .keepTracking(false)
            .askForPermission(new PermissionConfiguration.Builder()
                    .rationaleMessage("Location permission required for prayer times!")
                    .requiredPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION })
                    .build())
            .useGooglePlayServices(new GooglePlayServicesConfiguration.Builder()
                    .fallbackToDefault(true)
                    .askForGooglePlayServices(false)
                    .askForSettingsApi(true)
                    .failOnSettingsApiSuspended(false)
                    .ignoreLastKnowLocation(false)
                    .setWaitPeriod(20 * 1000)
                    .build())
            .useDefaultProviders(new DefaultProviderConfiguration.Builder()
                    .requiredTimeInterval(5 * 60 * 1000)
                    .requiredDistanceInterval(0)
                    .acceptableAccuracy(5.0f)
                    .acceptableTimePeriod(5 * 60 * 1000)
                    .gpsMessage("Turn on GPS?")
                    .setWaitPeriod(ProviderType.GPS, 20 * 1000)
                    .setWaitPeriod(ProviderType.NETWORK, 20 * 1000)
                    .build())
            .build();


    @Override
    public LocationConfiguration getLocationConfiguration() {
        return awesomeConfiguration;
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        HomeActivity.this.location = new Location("");
        HomeActivity.this.location.setLongitude(longitude);
        HomeActivity.this.location.setLatitude(latitude);

        settings.setLatFor(0,latitude);
        settings.setLngFor(0,longitude);

        cache.saveString(UserPreference.PREF_LOCATION_LATITUDE, String.valueOf(latitude));
        cache.saveString(UserPreference.PREF_LOCATION_LONGITUDE, String.valueOf(longitude));

        Log.e("lat", latitude + " ---- ");
        Log.e("lng", longitude + "----");
        TimeZone tz = TimeZone.getDefault();
        saveLocation("-", tz.getID(), (float) longitude,(float) latitude);
        homeFragment.GetPrayers();

    }

    private void updateAlarmStatus() {
        AppSettings settings = AppSettings.getInstance(this);

        SalaatAlarmReceiver sar = new SalaatAlarmReceiver();
        boolean isAlarmSet = settings.isAlarmSetFor(0);
        sar.setAlarm(this);
    }

    @Override
    public void onLocationFailed(int type) {

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences defaultPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDarkMode =  defaultPreferences.getBoolean("dark_mode",false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.AppTheme_NoActionBarDark);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            setTheme(R.style.AppTheme_NoActionBar);
        }
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        settings = AppSettings.getInstance(this);
//        fetchRemoteConfig();
        cache=UserPreference.getInstance(this);

        preferences = getSharedPreferences("app",MODE_PRIVATE);
        editor = preferences.edit();





        //setupPrayerTimes();
//        getLocation();


        homeFragment = new HomeFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, homeFragment);
        transaction.commit();



        binding.bottomMenu.add(new KBottomNavigation.Model(ID_QURAN,R.drawable.quran));
        binding.bottomMenu.add(new KBottomNavigation.Model(ID_SEARCH,R.drawable.ic_search));
        binding.bottomMenu.add(new KBottomNavigation.Model(ID_HOME,R.drawable.ic_masjid));
        binding.bottomMenu.add(new KBottomNavigation.Model(ID_DONATION,R.drawable.donation));
        binding.bottomMenu.add(new KBottomNavigation.Model(ID_MORE,R.drawable.more));

        binding.bottomMenu.show(ID_HOME,false);
        //binding.viewpager.setCurrentItem(2);

        binding.bottomMenu.setOnClickMenuListener(new Function1<KBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(KBottomNavigation.Model model) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                switch (model.getId()) {
                    case ID_HOME:
//                        binding.viewpager.setCurrentItem(2);
//                        binding.surahRecyclerView.setVisibility(View.GONE);
                        getSupportFragmentManager().popBackStack();
                        break;
                    case ID_QURAN:
                        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        Fragment f = getSupportFragmentManager().findFragmentById(R.id.content);
                        if(f instanceof HomeFragment) {} else {
                            getSupportFragmentManager().popBackStack();
                        }
                        transaction.add(R.id.content, new SurahFragment());
                        transaction.addToBackStack(null);

                       // transaction.commit();

//                        binding.surahRecyclerView.setVisibility(View.VISIBLE);
//                        DatabaseHelper dbHelper= new DatabaseHelper(MainActivity.this);
//                        try {
//                            dbHelper.createDataBase();
//                            dbHelper.openDataBase();
//                            SurahDataSource dataSource = new SurahDataSource(MainActivity.this);
//                            ArrayList<Surah> surahs = dataSource.getEnglishSurahArrayList();
//                            SurahAdapter surahAdapter = new SurahAdapter(surahs, MainActivity.this);
//                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
//                            binding.surahRecyclerView.setLayoutManager(linearLayoutManager);
//                            binding.surahRecyclerView.setAdapter(surahAdapter);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }


                        //binding.viewpager.setCurrentItem(0);
                        //transaction.replace(R.id.content, new SurahFragment());
                        break;
                    case ID_SEARCH:
//                        binding.surahRecyclerView.setVisibility(View.GONE);
//                        binding.viewpager.setCurrentItem(1);

                        if(getSupportFragmentManager().findFragmentById(R.id.content) instanceof HomeFragment) {} else {
                            getSupportFragmentManager().popBackStack();
                        }
                        transaction.add(R.id.content, new SearchQAFragment());
                        transaction.addToBackStack(null);
                        break;
                    case ID_DONATION:
//                        binding.surahRecyclerView.setVisibility(View.GONE);
//                        binding.bottomMenu.show(ID_HOME, true);
//                        Snackbar.make(binding.getRoot(), "Feature not available.",Snackbar.LENGTH_LONG).show();
                        if(getSupportFragmentManager().findFragmentById(R.id.content) instanceof HomeFragment) {} else {
                            getSupportFragmentManager().popBackStack();
                        }
                        transaction.add(R.id.content, new FundFragment());
                        transaction.addToBackStack(null);
                        break;
                    case ID_MORE:
//                        binding.surahRecyclerView.setVisibility(View.GONE);
//                        binding.viewpager.setCurrentItem(3);
                        if(getSupportFragmentManager().findFragmentById(R.id.content) instanceof HomeFragment) {} else {
                            getSupportFragmentManager().popBackStack();
                        }
                        transaction.add(R.id.content, new MoreFragment());
                        transaction.addToBackStack(null);
                        break;
                }
                transaction.commit();
                return null;
            }
        });

//        binding.viewpager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
//        binding.viewpager.addOnPageChangeListener(new MyOnPageChangeListener());


        getLocation();
        //loadNamazTimes();
    }



    void loadNamazTimes() {
//        if(mFirebaseRemoteConfig.getBoolean("isPrayerTimesStopped")) {
//            return;
//        }


        isLocationAllow=cache.getBoolean(UserPreference.PREF_IS_ALLOW_LOCATION,false);
        gps = new GPSTracker(HomeActivity.this);
        locationLatitude=  settings.getLatFor(0);
        locationLongitude=  settings.getLngFor(0);
        if(gps.canGetLocation()) {
            try {
                if (ActivityCompat.checkSelfPermission(this, mPermission)
                        != getPackageManager().PERMISSION_GRANTED) {
                    // If any permission above not allowed by user, this condition will
                    EasyPermissions.requestPermissions(
                            new PermissionRequest.Builder(this, 11, new String[]{Manifest.permission.ACCESS_FINE_LOCATION})
                                    .setRationale("Please provide permission for prayers")
                                    .setPositiveButtonText("Okay")
                                    .setNegativeButtonText("Cancel")
                                    .build());
                }
                else {
                    getPrayerTimer();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (locationLongitude==0&&locationLatitude==0)
        {
            showSettingsAlert();
        }

        setupPrayerTimes();
    }

    private void saveLocation(String city, String tzId, float... lngLat) {
        if (lngLat.length != 2) {
            SETTINGS.removeLngLat();
        } else {
            SETTINGS.saveLngLat(lngLat[0], lngLat[1]);
        }
        SETTINGS.setLocTimeZoneId(tzId);


        SETTINGS.setCityName(city);
        setNearestMethod();
    }

    private void setNearestMethod() {
        Coordinates location = SETTINGS.getLngLat();
        if (location == null) {
            return;
        }
        Location loc1 = new Location("");
        loc1.setLatitude(location.latitude);
        loc1.setLongitude(location.longitude);
        Location loc2 = new Location("");
        float minDist = Float.MAX_VALUE;
        int order = -1;
        for (int i = 0; i < METHOD_LOCATIONS.length; i++) {
            Coordinates lngLat = METHOD_LOCATIONS[i];
            if (lngLat.latitude == 0 && lngLat.longitude == 0) {
                continue;
            }
            loc2.setLatitude(lngLat.latitude);
            loc2.setLongitude(lngLat.longitude);
            float dist = loc1.distanceTo(loc2);
            if (dist < minDist) {
                minDist = dist;
                order = i;
            }
        }
        if (order >= 0) {
            SETTINGS.setCalcMethod(order);
            int finalOrder = order;

        }
    }


    private void getPrayerTimer() {
        // check if GPS enabled
        if(gps.canGetLocation()) {
            GPSTracker gpsTracker = new GPSTracker(this);
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            HomeActivity.this.location = new Location("");
            HomeActivity.this.location.setLongitude(longitude);
            HomeActivity.this.location.setLatitude(latitude);

            settings.setLatFor(0,latitude);
            settings.setLngFor(0,longitude);

            cache.saveString(UserPreference.PREF_LOCATION_LATITUDE, String.valueOf(latitude));
            cache.saveString(UserPreference.PREF_LOCATION_LONGITUDE, String.valueOf(longitude));

            Log.e("lat", latitude + " ---- ");
            Log.e("lng", longitude + "----");
            // gps.showSettingsAlert();
            TimeZone tz = TimeZone.getDefault();

            saveLocation("-",tz.getID(), (float) longitude,(float) latitude);
            homeFragment.GetPrayers();

//            homeFragment.GetPrayerName();
//            homeFragment.GetPrayerNames();

            // \n is for new line
          /*  Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();*/
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
           // if (locationLatitude.equals("") && locationLongitude.equals("")) {
            if (locationLatitude==0 && locationLongitude==0) {
                //gps.showSettingsAlert();
                showSettingsAlert();
            }
        }
    }
    void fetchRemoteConfig() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        mFirebaseRemoteConfig.fetchAndActivate().addOnSuccessListener(new OnSuccessListener<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                if(mFirebaseRemoteConfig.getLong("versionCode") > BuildConfig.VERSION_CODE) {
                    showUpdateDialog();
                    return;
                }

                if(mFirebaseRemoteConfig.getBoolean("homepage_dialog_enabled")) {
                    showFrontDialog();
                }
            }
        });
    }

    void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        View dialogView = LayoutInflater.from(HomeActivity.this).inflate(R.layout.front_dialog, null);
        final AlertDialog dialog;

        TextView heading = dialogView.findViewById(R.id.heading);
        TextView detail = dialogView.findViewById(R.id.detail);
        ImageView image = dialogView.findViewById(R.id.image);

        if(!mFirebaseRemoteConfig.getString("update_dialog_image").equals("null")) {
            Glide.with(HomeActivity.this)
                    .load(mFirebaseRemoteConfig.getString("dialog_image"))
                    .placeholder(R.drawable.imageplaceholder)
                    .into(image);
        } else {
            image.setVisibility(View.GONE);
        }

        heading.setText(Html.fromHtml(mFirebaseRemoteConfig.getString("update_dialog_heading")));
        detail.setText(Html.fromHtml(mFirebaseRemoteConfig.getString("update_dialog_detail")));

        builder.setView(dialogView);
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if(mFirebaseRemoteConfig.getBoolean("isUpdateDialogCancelable")) {
            dialogView.findViewById(R.id.closeBtn).setVisibility(View.VISIBLE);
            dialogView.findViewById(R.id.closeBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        } else {
            dialogView.findViewById(R.id.closeBtn).setVisibility(View.GONE);
        }



        Button actionBtn = dialogView.findViewById(R.id.actionBtn);
        actionBtn.setText(mFirebaseRemoteConfig.getString("update_action_button"));
        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = mFirebaseRemoteConfig.getString("update_dialog_path");
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(i);
                } catch (ActivityNotFoundException anfe) {
                    try {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(i);
                    } catch (Exception e) {
                        dialog.dismiss();
                    }
                }
            }
        });


        dialog.show();
    }

    void showFrontDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        View dialogView = LayoutInflater.from(HomeActivity.this).inflate(R.layout.front_dialog, null);
        final AlertDialog dialog;

        TextView heading = dialogView.findViewById(R.id.heading);
        TextView detail = dialogView.findViewById(R.id.detail);
        ImageView image = dialogView.findViewById(R.id.image);

        if(!mFirebaseRemoteConfig.getString("dialog_image").equals("null")) {
            Glide.with(HomeActivity.this)
                    .load(mFirebaseRemoteConfig.getString("dialog_image"))
                    .placeholder(R.drawable.imageplaceholder)
                    .into(image);
        } else {
            image.setVisibility(View.GONE);
        }

        heading.setText(Html.fromHtml(mFirebaseRemoteConfig.getString("dialog_heading")));
        detail.setText(Html.fromHtml(mFirebaseRemoteConfig.getString("dialog_detail")));

        builder.setView(dialogView);
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if(mFirebaseRemoteConfig.getBoolean("isDialogCancelable")) {
            dialogView.findViewById(R.id.closeBtn).setVisibility(View.VISIBLE);
            dialogView.findViewById(R.id.closeBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        } else {
            dialogView.findViewById(R.id.closeBtn).setVisibility(View.GONE);
        }



        Button actionBtn = dialogView.findViewById(R.id.actionBtn);
        actionBtn.setText(mFirebaseRemoteConfig.getString("dialog_action_button"));
        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = mFirebaseRemoteConfig.getString("dialog_path");
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(i);
                } catch (ActivityNotFoundException anfe) {
                    try {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(i);
                    } catch (Exception e) {
                        dialog.dismiss();
                    }
                }
            }
        });


        dialog.show();
    }

    public void replacePrayersFragment() {
        locationLatitude=  settings.getLatFor(0);
        locationLongitude=  settings.getLngFor(0);
       // if(location != null) {
            //if (!locationLatitude.equals("") && !locationLongitude.equals("")) {
                if (locationLatitude>0 && locationLongitude>0) {

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, PrayerTimesFragment.newInstance(0, location));
            transaction.commit();
        } else {
            Snackbar.make(binding.getRoot(),"Location not available.", Snackbar.LENGTH_LONG).show();
            //Toast.makeText(MainActivity.this, "Location not available.", Toast.LENGTH_SHORT).show();
        }
    }

    void setupPrayerTimes() {
        AppSettings.getInstance(this).set(AppSettings.Key.HAS_DEFAULT_SET, true);
        settings = AppSettings.getInstance(this);
        //INIT APP
        if (!settings.getBoolean(AppSettings.Key.IS_INIT)) {
            settings.set(settings.getKeyFor(AppSettings.Key.IS_ALARM_SET,         0), true);
            settings.set(settings.getKeyFor(AppSettings.Key.IS_FAJR_ALARM_SET,    0), true);
            settings.set(settings.getKeyFor(AppSettings.Key.IS_DHUHR_ALARM_SET,   0), true);
            settings.set(settings.getKeyFor(AppSettings.Key.IS_ASR_ALARM_SET,     0), true);
            settings.set(settings.getKeyFor(AppSettings.Key.IS_MAGHRIB_ALARM_SET, 0), true);
            settings.set(settings.getKeyFor(AppSettings.Key.IS_ISHA_ALARM_SET,    0), true);
            settings.set(AppSettings.Key.USE_ADHAN, true);
            settings.set(AppSettings.Key.IS_INIT, true);
        }
    }



    @AfterPermissionGranted(12)
    private void startNaatsActivity() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Intent i = new Intent(HomeActivity.this, NaatsActivity.class);
            startActivity(i);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "Please allow storage permission to download or play naats. ",
                    12, perms);
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

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mianasad.myislam"));
            startActivity(browserIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_prayers) {
            startActivity(new Intent(HomeActivity.this, PrayersActivity.class));
        } else if (id == R.id.categories) {
            Intent i = new Intent(HomeActivity.this,DuaActivity.class);
            startActivity(i);
        }  else if (id == R.id.nav_manage) {
            Intent i = new Intent(this, MyPreferencesActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_share) {
            ShareApp();
        } else if (id == R.id.nav_kalimas) {
            Intent i1 = new Intent(HomeActivity.this,KalimaActivity.class);
            startActivity(i1);
        } else if (id == R.id.nav_naats) {
            Intent i1 = new Intent(HomeActivity.this,NaatsActivity.class);
            startActivity(i1);
        } else if (id == R.id.nav_allah) {
            Intent i1 = new Intent(HomeActivity.this,AllahNamesActivity.class);
            startActivity(i1);
        }
        else if (id == R.id.nav_send) {
            Intent i1 = new Intent(HomeActivity.this, About.class);
            startActivity(i1);
        } else if (id == R.id.review) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mianasad.myislam"));
            startActivity(browserIntent);
        }else if (id == R.id.nav_search) {
            Language = SP.getString("lang", "en");
            AllSearchTopics = dbHeplper.GetAllSearchTopics(Language);
            new SimpleSearchDialogCompat(HomeActivity.this, "Search...",
                    "What are you looking for...?", null, AllSearchTopics,
                    new SearchResultListener<SampleSearchModel>() {
                        @Override
                        public void onSelected(BaseSearchDialogCompat dialog,
                                               SampleSearchModel item, int position) {
                            Intent i = new Intent(HomeActivity.this,DuaActivity.class);
                            i.putExtra("topic",item.getTitle());
                            i.putExtra("lang",Language);
                            startActivity(i);
                            dialog.dismiss();
                        }
                    }).show();
        } else if(id == R.id.nav_privacy) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://app.mianasad.com/privacy_policy.html"));
            startActivity(browserIntent);
        }


        return true;
    }

    public int getColor(){
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(130), rnd.nextInt(70), rnd.nextInt(100));
        return color;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 07) {
            gps = new GPSTracker(HomeActivity.this);

            Toast.makeText(getApplicationContext(),"show",Toast.LENGTH_LONG).show();
            if(gps.canGetLocation()) {
                try {
                    if (ActivityCompat.checkSelfPermission(this, mPermission)
                            != getPackageManager().PERMISSION_GRANTED) {
                        // If any permission above not allowed by user, this condition will
                        EasyPermissions.requestPermissions(
                                new PermissionRequest.Builder(this, 11, new String[]{Manifest.permission.ACCESS_FINE_LOCATION})
                                        .setRationale("Please provide permission for prayers")
                                        .setPositiveButtonText("Okay")
                                        .setNegativeButtonText("Cancel")
                                        .build());
                    }
                    else {
                        getLocation();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode,List<String> perms) {
            getLocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode,List<String> perms) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
//    @Override
//    public void onLocationChanged(Location location) {
//        this.location = location;
//        settings.setLatFor(0,location.getLatitude());
//        settings.setLngFor(0,location.getLongitude());
//        homeFragment.GetPrayerName();
//        //GetPrayerName();
//    }
//
//    @Override
//    public void onLocationFailed(int type) {
//
//    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    public void showSettingsAlert() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent,07);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
}
