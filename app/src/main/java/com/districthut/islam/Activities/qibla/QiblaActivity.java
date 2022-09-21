package com.districthut.islam.Activities.qibla;

import android.Manifest;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.districthut.islam.models.GPSTracker;
import com.mirfatif.noorulhuda.R;
import com.mirfatif.noorulhuda.databinding.ActivityQiblaBinding;
import com.yayandroid.locationmanager.base.LocationBaseActivity;
import com.yayandroid.locationmanager.configuration.DefaultProviderConfiguration;
import com.yayandroid.locationmanager.configuration.GooglePlayServicesConfiguration;
import com.yayandroid.locationmanager.configuration.LocationConfiguration;
import com.yayandroid.locationmanager.configuration.PermissionConfiguration;
import com.yayandroid.locationmanager.constants.ProviderType;


public class QiblaActivity extends LocationBaseActivity implements SensorEventListener {

    private float currentDegree = 0f;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer, mMagnetometer;
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];
    private boolean mLastAccelerometerSet = false, switchView = false,
            pointerPosition = true, mLastMagnetometerSet = false, start = false, mapReady = false;
    private double previousAzimuthInDegrees = 0f;
    private long SensorSendTime;
    private float pointerFirstPositionX, pointerFirstPositionY, smallCircleRadius, newX, newY;
    private double lastRoll, lastPitch, lastTime;
    private GPSTracker gps;

//    @Override
//    public LocationConfiguration getLocationConfiguration() {
//        return Configurations.defaultConfiguration("Please allow location permissions to get accurate Qibla direction.", "Would you mind to turn GPS on?");
//
//    }

    ActivityQiblaBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQiblaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //gps = new GPSTracker(QiblaActivity.this);

       // getLocation();

        binding.degreeLbl.setText(getString(R.string.qibla_direction) + " " );

        //init sensor services
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //getPrayerTimer();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    int qibladegree(double lat,double lon)
    {
        return (int)QuiblaCalculator.doCalculate(lat,lon);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, mAccelerometer);
        mSensorManager.unregisterListener(this, mMagnetometer);
    }


    /**
     * Function to make compass indicator stable
     *
     * @param input      Input
     * @param lastOutput Lst output
     * @param dt         Last time
     * @return New output
     */
    public double lowPass(double input, double lastOutput, double dt) {
        double elapsedTime = dt - SensorSendTime;
        Log.d("TIMESEND", elapsedTime + "");
        SensorSendTime = (long) dt;
        elapsedTime = elapsedTime / 1000;
        final double lagConstant = 1;
        double alpha = elapsedTime / (lagConstant + elapsedTime);
        return alpha * input + (1 - alpha) * lastOutput;
    }

    /**
     * Function to make compass level indicator stable
     *
     * @param input      Input
     * @param lastOutput Lst output
     * @param dt         Last time
     * @return New output
     */
    public double lowPassPointerLevel(double input, double lastOutput, double dt) {
        final double lagConstant = 0.25;
        double alpha = dt / (lagConstant + dt);
        return alpha * input + (1 - alpha) * lastOutput;
    }

    /**
     * override function return every change
     *
     * @param event Sensor changes
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        double startTime = System.currentTimeMillis();

        if (event.sensor == mAccelerometer) {
            mLastAccelerometer = event.values;
            mLastAccelerometerSet = true;
        } else if (event.sensor == mMagnetometer) {
            mLastMagnetometer = event.values;
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            boolean success = SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mR, mOrientation);
            float azimuthInRadians = mOrientation[0];
            double azimuthInDegress = -(float) (Math.toDegrees(azimuthInRadians) + 360) % 360;

            if (Math.abs(azimuthInDegress - previousAzimuthInDegrees) > 300) {
                previousAzimuthInDegrees = azimuthInDegress;
            }

            azimuthInDegress = lowPass(azimuthInDegress, previousAzimuthInDegrees, startTime);



            RotateAnimation ra = new RotateAnimation(
                    (float) previousAzimuthInDegrees,
                    (float) azimuthInDegress,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f);

            ra.setDuration(500);
            ra.setFillAfter(true);
            binding.compassContainer.startAnimation(ra);
            binding.innerplace.startAnimation(ra);

            previousAzimuthInDegrees = azimuthInDegress;


            if (pointerPosition == true) {
                pointerFirstPositionX = binding.compassLevel.getX();
                pointerFirstPositionY = binding.compassLevel.getY();
                smallCircleRadius = binding.smallCircle.getX();
                pointerPosition = false;
            }

            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(mR, orientation);
                double yaw = orientation[0] * 57.2957795f;
                double pitch = orientation[1] * 57.2957795f;
                double roll = orientation[2] * 57.2957795f;
                if (pitch > 90) pitch -= 180;
                if (pitch < -90) pitch += 180;
                if (roll > 90) roll -= 180;
                if (roll < -90) roll += 180;

                double time = System.currentTimeMillis();

                if (!start) {
                    lastTime = time;
                    lastRoll = roll;
                    lastPitch = pitch;
                }
                start = true;


                double dt = (time - lastTime) / 1000.0;
                roll = lowPassPointerLevel(roll, lastRoll, dt);
                pitch = lowPassPointerLevel(pitch, lastPitch, dt);
                lastTime = time;
                lastRoll = roll;
                lastPitch = pitch;

                newX = (float) (pointerFirstPositionX + pointerFirstPositionX * roll / 90.0);
                newY = (float) (pointerFirstPositionY + pointerFirstPositionY * pitch / 90.0);

                binding.compassLevel.setX(newX);
                binding.compassLevel.setY(newY);

                if (smallCircleRadius / 3 < Math.sqrt((roll * roll) + (pitch * pitch))) {
                    binding.compassLevel.setImageResource(R.drawable.ic_error_pointer);
                } else {
                    binding.compassLevel.setImageResource(R.drawable.ic_level_pointer);
                }
            }



        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    private void getPrayerTimer() {
        // create class object

        // check if GPS enabled
        if(gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            binding.degreeLbl.setText("Qibla direction from North: " + qibladegree(latitude,longitude) + "°");
            //animate compass pointer
            RotateAnimation ra = new RotateAnimation(currentDegree, qibladegree(latitude,longitude),
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            ra.setDuration(400);
            ra.setFillAfter(true);
            binding.needle.startAnimation(ra);
            binding.poinerInner.startAnimation(ra);
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

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

        binding.degreeLbl.setText("Qibla direction from North: " + qibladegree(latitude,longitude) + "°");
        //animate compass pointer
        RotateAnimation ra = new RotateAnimation(currentDegree, qibladegree(latitude,longitude),
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(400);
        ra.setFillAfter(true);
        binding.needle.startAnimation(ra);
        binding.poinerInner.startAnimation(ra);

//        HomeActivity.this.location = new Location("");
//        HomeActivity.this.location.setLongitude(longitude);
//        HomeActivity.this.location.setLatitude(latitude);
//
//        settings.setLatFor(0,latitude);
//        settings.setLngFor(0,longitude);
//
//        cache.saveString(UserPreference.PREF_LOCATION_LATITUDE, String.valueOf(latitude));
//        cache.saveString(UserPreference.PREF_LOCATION_LONGITUDE, String.valueOf(longitude));
//
//        Log.e("lat", latitude + " ---- ");
//        Log.e("lng", longitude + "----");
//
//        saveLocation("-","Asia/Karachi", (float) longitude,(float) latitude);
//        homeFragment.GetPrayers();
    }

    @Override
    public void onLocationFailed(int type) {

    }

//    @Override
//    public void onLocationChanged(Location location) {
//        binding.degreeLbl.setText("Qibla direction from North: " + qibladegree(location.getLatitude(),location.getLongitude()) + "°");
//        //animate compass pointer
//        RotateAnimation ra = new RotateAnimation(currentDegree, qibladegree(location.getLatitude(),location.getLongitude()),
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        ra.setDuration(400);
//        ra.setFillAfter(true);
//        binding.needle.startAnimation(ra);
//        binding.poinerInner.startAnimation(ra);
//    }
//
//    @Override
//    public void onLocationFailed(int type) {
//
//    }
}
