package com.mianasad.myislam.views

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.applovin.mediation.*
import com.applovin.mediation.ads.MaxAdView
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkUtils
import com.districthut.islam.Activities.calendar.CalendarActivity
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar
import com.mianasad.myislam.viewmodel.MainViewModel
import com.mirfatif.noorulhuda.R
import com.mirfatif.noorulhuda.databinding.ActivityHomeBinding
import com.mirfatif.noorulhuda.prefs.MySettings
import com.yayandroid.locationmanager.base.LocationBaseActivity
import com.yayandroid.locationmanager.configuration.DefaultProviderConfiguration
import com.yayandroid.locationmanager.configuration.GooglePlayServicesConfiguration
import com.yayandroid.locationmanager.configuration.LocationConfiguration
import com.yayandroid.locationmanager.configuration.PermissionConfiguration
import com.yayandroid.locationmanager.constants.ProviderType
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class HomeActivity : LocationBaseActivity(), MaxAdListener {

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityHomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var interstitialAd: MaxInterstitialAd
    private var retryAttempt = 0.0
    private var adView: MaxAdView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)





        // Make sure to set the mediation provider value to "max" to ensure proper functionality
        AppLovinSdk.getInstance(this).mediationProvider = "max"
        AppLovinSdk.getInstance(this).initializeSdk {
            createInterstitialAd()
        }

        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.mainFragment, R.id.moreFragment, R.id.surahFragment, R.id.duaCategoriesFragment)
        )


        val navController = binding.fragmentContainerView.getFragment<NavHostFragment>().navController

        setupActionBarWithNavController(
            navController, appBarConfiguration
        )

        binding.bottom.setupWithNavController(navController)

        binding.bottom.setOnItemSelectedListener {
//            if ( interstitialAd.isReady)
//            {
//                interstitialAd.showAd();
//            }

            NavigationUI.onNavDestinationSelected(it,findNavController(R.id.fragmentContainerView))
            findNavController(R.id.fragmentContainerView).popBackStack(it.itemId, inclusive = false)
            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainFragment -> {
                    supportActionBar?.title = ""

                    binding.actionBarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    resources.getDimension(R.dimen._12font_mdp))
                    binding.actionBarTitle.setTypeface(Typeface.create(binding.actionBarTitle.typeface, Typeface.BOLD), Typeface.NORMAL);

                    binding.islamicDate.visibility = View.VISIBLE
                    if(binding.ratingStar.visibility != View.VISIBLE)
                        binding.ratingStar.visibility = View.VISIBLE


                    setDate()
                    //supportActionBar?.hide()
                }
                else -> {
                    binding.ratingStar.visibility = View.INVISIBLE
                    supportActionBar?.title = ""
                    binding.actionBarTitle.text = destination.label
                    binding.actionBarTitle.textSize = 20F
                    binding.actionBarTitle.setTypeface(Typeface.create(binding.actionBarTitle.typeface, Typeface.NORMAL), Typeface.NORMAL);
                    binding.islamicDate.visibility = View.GONE
                }
                    //supportActionBar?.show()
            }
        }

        getLocation()

        //createBannerAd()
    }

    fun createBannerAd()
    {
        adView = MaxAdView("742a12c0e7b808ab", this)
        adView?.setListener(object : MaxAdViewAdListener {
            override fun onAdLoaded(ad: MaxAd?) {
                binding.smallAd.visibility = View.VISIBLE
            }

            override fun onAdDisplayed(ad: MaxAd?) {
                TODO("Not yet implemented")
            }

            override fun onAdHidden(ad: MaxAd?) {
                TODO("Not yet implemented")
            }

            override fun onAdClicked(ad: MaxAd?) {
                TODO("Not yet implemented")
            }

            override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
                TODO("Not yet implemented")
            }

            override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
                TODO("Not yet implemented")
            }

            override fun onAdExpanded(ad: MaxAd?) {
                TODO("Not yet implemented")
            }

            override fun onAdCollapsed(ad: MaxAd?) {
                TODO("Not yet implemented")
            }

        })

        // Stretch to the width of the screen for banners to be fully functional
        val width = ViewGroup.LayoutParams.MATCH_PARENT

        // Get the adaptive banner height.
        val heightDp = MaxAdFormat.BANNER.getAdaptiveSize(this).height
        val heightPx = AppLovinSdkUtils.dpToPx(this, heightDp)

        adView?.layoutParams = FrameLayout.LayoutParams(width, heightPx)
        adView?.setExtraParameter("adaptive_banner", "true")

        // Set background or background color for banners to be fully functional
        adView?.setBackgroundColor(Color.WHITE)


        binding.smallAd.addView(adView)

        // Load the ad
        adView?.loadAd()
    }


    fun createInterstitialAd()
    {
        interstitialAd = MaxInterstitialAd( "8419c9d8f63cdb3b", this )
        interstitialAd.setListener( this )

        // Load the first ad
        interstitialAd.loadAd()
    }

    // MAX Ad Listener
    override fun onAdLoaded(maxAd: MaxAd)
    {
        // Interstitial ad is ready to be shown. interstitialAd.isReady() will now return 'true'

        // Reset retry attempt
        retryAttempt = 0.0


    }

    override fun onAdLoadFailed(adUnitId: String?, error: MaxError?)
    {
        // Interstitial ad failed to load
        // AppLovin recommends that you retry with exponentially higher delays up to a maximum delay (in this case 64 seconds)

        error?.let { Log.e("error", it.message) }

        retryAttempt++
        val delayMillis = TimeUnit.SECONDS.toMillis( Math.pow( 2.0, Math.min( 6.0, retryAttempt ) ).toLong() )

        Handler().postDelayed( { interstitialAd.loadAd() }, delayMillis )
    }

    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?)
    {
        // Interstitial ad failed to display. AppLovin recommends that you load the next ad.
        interstitialAd.loadAd()
    }

    override fun onAdDisplayed(maxAd: MaxAd) {}

    override fun onAdClicked(maxAd: MaxAd) {}

    override fun onAdHidden(maxAd: MaxAd)
    {
        // Interstitial ad is hidden. Pre-load the next ad
        interstitialAd.loadAd()
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    fun setDate() {
        val cal = UmmalquraCalendar()
        binding.actionBarTitle.text = String.format(
            "%d %s, %d AH",
            cal[Calendar.DAY_OF_MONTH],
            cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale("en")),
            cal[Calendar.YEAR]
        )
        val c = Calendar.getInstance().time
        val df = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault())
        val formattedDate = df.format(c)
        binding.islamicDate.text = formattedDate
        val dateClickListener =
            View.OnClickListener { view: View? ->
                startActivity(
                    Intent(
                        this,
                        CalendarActivity::class.java
                    )
                )
            }
//        binding.currentDate.setOnClickListener(dateClickListener)
//        binding.islamicDate.setOnClickListener(dateClickListener)
    }

    //////////////////////////////////////////////////////////////////
    ///////////////////////////// GENERAL ////////////////////////////
    //////////////////////////////////////////////////////////////////


    private var awesomeConfiguration: LocationConfiguration = LocationConfiguration.Builder()
        .keepTracking(false)
        .askForPermission(
            PermissionConfiguration.Builder()
                .rationaleMessage("Location permission required for prayer times!")
                .requiredPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
                .build()
        )
        .useGooglePlayServices(
            GooglePlayServicesConfiguration.Builder()
                .fallbackToDefault(true)
                .askForGooglePlayServices(false)
                .askForSettingsApi(true)
                .failOnSettingsApiSuspended(false)
                .ignoreLastKnowLocation(false)
                .setWaitPeriod((20 * 1000).toLong())
                .build()
        )
        .useDefaultProviders(
            DefaultProviderConfiguration.Builder()
                .requiredTimeInterval((5 * 60 * 1000).toLong())
                .requiredDistanceInterval(0)
                .acceptableAccuracy(5.0f)
                .acceptableTimePeriod((5 * 60 * 1000).toLong())
                .gpsMessage("Turn on GPS?")
                .setWaitPeriod(ProviderType.GPS, (20 * 1000).toLong())
                .setWaitPeriod(ProviderType.NETWORK, (20 * 1000).toLong())
                .build()
        )
        .build()

    override fun onLocationChanged(location: Location?) {
        var timeZone = TimeZone.getDefault()
        MySettings.SETTINGS.saveLocation(
            "-",
            timeZone.id,
            location?.longitude!!.toFloat(),
            location?.latitude!!.toFloat()
        )
        viewModel.locationUpdated(true)

        Log.e("lat", "${location?.latitude} ---- ")
        Log.e("lng", "${location?.longitude}----")

        //saveLocation("-", tz.id, longitude.toFloat(), latitude.toFloat())
    }

    override fun onLocationFailed(type: Int) {
        TODO("Not yet implemented")
    }

    override fun getLocationConfiguration(): LocationConfiguration {
        return awesomeConfiguration
    }
}