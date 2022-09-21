package com.mianasad.myislam.views.fragment


import android.content.Intent
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdFormat
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.applovin.sdk.AppLovinSdkUtils
import com.districthut.islam.Activities.calendar.CalendarActivity
import com.districthut.islam.UserPreference
import com.districthut.islam.adapters.FeelingsAdapter
import com.districthut.islam.adapters.onFeelingSelected
import com.districthut.islam.utils.GridDividerDecoration
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar
import com.mianasad.myislam.models.database.feeling.Dua
import com.mianasad.myislam.utils.database.AppDatabase
import com.mianasad.myislam.viewmodel.DatabaseViewModel
import com.mianasad.myislam.viewmodel.MainViewModel
import com.mianasad.myislam.views.HomeActivity
import com.mirfatif.noorulhuda.R
import com.mirfatif.noorulhuda.databinding.FragmentMainBinding
import com.mirfatif.noorulhuda.prayer.PrayerData
import com.mirfatif.noorulhuda.prefs.MySettings
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var dbViewModel: DatabaseViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var feelingDialog: AlertDialog
    private lateinit var appDatabase: AppDatabase
    private lateinit var feelingAdapter: FeelingsAdapter
    private var createdView: View? = null

    private lateinit var adView: MaxAdView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(createdView == null) {
            binding = FragmentMainBinding.inflate(inflater, container, false)
            createdView = binding.root

            appDatabase = AppDatabase.getInstance(requireContext())!!
            dbViewModel = DatabaseViewModel(appDatabase!!)


            binding.feelingFeature.feelingTxt.setOnClickListener {
                showFeelingDialog()
            }

            binding.duas.setOnClickListener{
                it.findNavController().navigate(R.id.duaCategoriesFragment)
            }

            binding.quran.setOnClickListener {

                it.findNavController().navigate(R.id.surahFragment)
            }

            setDate()

            viewModel.locationUpdate.observe(viewLifecycleOwner)  { locationUpdated ->
                if(locationUpdated) {
                    val data: PrayerData = PrayerData.getPrayerData()
                    binding.prayerName.text = resources.getString(MySettings.SETTINGS.NAMES[data.nextPrayer])
                    binding.prayerTime.text = data.strTimes[data.nextPrayer]
                    binding.timer.setEndTime(data.nextPrayerTime)
                    binding.locationMessage.visibility = View.GONE
                    binding.prayerTimingGroup.visibility = View.VISIBLE


                    getAddress(
                        MySettings.SETTINGS.lngLat.latitude
                        ,
                        MySettings.SETTINGS.lngLat.longitude
                    )

                } else {
                    binding.prayerTimingGroup.visibility = View.GONE
                    binding.locationMessage.visibility = View.VISIBLE
                }
            }

            createMediumRectangle()
        }



        return binding.root
    }

    fun createMediumRectangle() {
        adView = MaxAdView("13c3f725d8e47fb6", MaxAdFormat.MREC, activity)
        adView.setListener(object : MaxAdViewAdListener {
            override fun onAdExpanded(ad: MaxAd) {

            }
            override fun onAdCollapsed(ad: MaxAd) {}
            override fun onAdLoaded(ad: MaxAd) {
                binding.adCard.visibility = View.VISIBLE
            }
            override fun onAdDisplayed(ad: MaxAd) {}
            override fun onAdHidden(ad: MaxAd) {}
            override fun onAdClicked(ad: MaxAd) {}
            override fun onAdLoadFailed(adUnitId: String, error: MaxError) {}
            override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {}
        })

        // MREC width and height are 300 and 250 respectively, on phones and tablets
        val widthPx = AppLovinSdkUtils.dpToPx(activity, 300)
        val heightPx = AppLovinSdkUtils.dpToPx(activity, 250)

        adView?.layoutParams = FrameLayout.LayoutParams(widthPx, heightPx)

        // Set background or background color for MRECs to be fully functional
        adView?.setBackgroundColor(Color.WHITE)


        binding.adLayout.addView(adView)

        // Load the ad
        adView?.loadAd()
    }

    fun getAddress(lat: Double, lng: Double) {
        val geocoder = Geocoder(activity, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            val obj = addresses[0]
            binding.city.text = obj.locality
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun setDate() {
        val cal = UmmalquraCalendar()
        binding.islamicDate.text = String.format(
            "%d %s, %d AH",
            cal[Calendar.DAY_OF_MONTH],
            cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale("en")),
            cal[Calendar.YEAR]
        )
        val c = Calendar.getInstance().time
        val df = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault())
        val formattedDate = df.format(c)
        binding.currentDate.text = formattedDate
        val dateClickListener =
            View.OnClickListener { view: View? ->
                startActivity(
                    Intent(
                        activity,
                        CalendarActivity::class.java
                    )
                )
            }
        binding.currentDate.setOnClickListener(dateClickListener)
        binding.islamicDate.setOnClickListener(dateClickListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbViewModel?.let {
            dbViewModel.singleFeeling.observe(viewLifecycleOwner) { feeling ->
                feeling?.let {
                    binding.feelingFeature.feelingImage.setImageDrawable(viewModel.getDrawable(feeling.feeling.image))

                    val dua:Dua = feeling.duaList[0]
                    binding.feelingFeature.feeling.text = dua.title
                    binding.feelingFeature.ayat.text = dua.arabic
                    binding.feelingFeature.transliteration.text = dua.transliteration
                    binding.feelingFeature.urdu.text = dua.urdu
                    binding.feelingFeature.english.text = dua.english
                    binding.feelingFeature.ref.text = dua.reference
                }
            }
            dbViewModel.getSingleFeeling()
        }
    }

    fun showFeelingDialog() {
        val view: View = LayoutInflater.from(activity).inflate(R.layout.feeling_dialog, null)
        feelingDialog = activity?.let {
            AlertDialog.Builder(it)
                .setView(view)
                .create()
        }!!

        appDatabase?.dao?.getAllFeelings()?.observe(viewLifecycleOwner) {
            feelingAdapter = FeelingsAdapter(it, object : onFeelingSelected {
                override fun onSelected(id: Long, mood: String) {
                    binding.feelingFeature.feelingTxt.text = "[${mood}]"
                    dbViewModel.getDuaForSelectedFeeling(id)
                    feelingDialog.dismiss()
                }
            })

            val recyclerView = view.findViewById<RecyclerView>(R.id.feelingList)
            recyclerView.layoutManager = GridLayoutManager(activity, 3)
            recyclerView.addItemDecoration(GridDividerDecoration(activity))
            recyclerView.adapter = feelingAdapter
            feelingDialog.show()
        }

    }


}