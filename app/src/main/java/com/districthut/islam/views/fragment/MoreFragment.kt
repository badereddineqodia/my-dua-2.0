package com.districthut.islam.views.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.GridLayoutManager

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.Fragment
import com.districthut.islam.Activities.*
import com.districthut.islam.Activities.calendar.CalendarActivity
import com.districthut.islam.Activities.qibla.QiblaActivity
import com.districthut.islam.adapters.MenuAdapter
import com.districthut.islam.models.MoreModel
import com.districthut.islam.utils.OnRecyclerViewItemClickListener
import com.districthut.islam.views.NamesActivity
import com.mianasad.myislam.views.HomeActivity
import com.mirfatif.noorulhuda.R
import com.mirfatif.noorulhuda.databinding.FragmentMoreBinding


import java.util.ArrayList

class MoreFragment : Fragment(), OnRecyclerViewItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    var binding: FragmentMoreBinding? = null
    private lateinit var moreModels:ArrayList<MoreModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreBinding.inflate(inflater, container, false)
        moreModels = ArrayList<MoreModel>()
        moreModels.add(MoreModel(R.drawable.dua_icon_small, "Duas", "duas"))
        moreModels.add(MoreModel(R.drawable.ic_beads, "Tasbeeh", "tasbeeh"))
        moreModels.add(MoreModel(R.drawable.ic_qibla, "Qibla", "qibla"))
        moreModels.add(MoreModel(R.drawable.allahname_front, "99 Names", "allah_names"))
        moreModels.add(MoreModel(R.drawable.muhammadname_front, "Names", "muhammad_names"))
        moreModels.add(MoreModel(R.drawable.ic_6, "6 Kalimas", "6_kalimas"))
        moreModels.add(MoreModel(R.drawable.ic_muslim, "Shahadat", "shahdat"))
        moreModels.add(MoreModel(R.drawable.calender, "Calendar", "calendar"))
        moreModels.add(MoreModel(R.drawable.ic_search_rounded, "Search", "search"))
        moreModels.add(MoreModel(R.drawable.ic_bookmark_round, "Bookmarks", "bookmarks"))
        moreModels.add(MoreModel(R.drawable.ic_favorite, "Rate Us", "rate_us"))
        moreModels.add(MoreModel(R.drawable.ic_share_grey, "Share App", "share_app"))
        moreModels.add(MoreModel(R.drawable.ic_settings_colored, "Settings", "settings"))
        moreModels.add(MoreModel(R.drawable.ic_info, "About", "about"))
        moreModels.add(MoreModel(R.drawable.dua_icon_small, "Community", "community"))


        val adapter = MenuAdapter(context, moreModels)
        adapter.setOnItemClickListener(this)
        binding!!.recyclerView.layoutManager = GridLayoutManager(context, 3)
        binding!!.recyclerView.adapter = adapter


        return binding!!.root
    }

    override fun onRecyclerViewItemClicked(position: Int, id: Int) {
        when (moreModels.get(position).code) {
            "duas" -> startActivity(Intent(activity, DuaActivity::class.java))
            "tasbeeh" -> startActivity(Intent(activity, TasbeehActivity::class.java))
            "qibla" ->                // Snackbar.make(binding.getRoot(),"Qibla Locator will be available in next update. JazakAllah", Snackbar.LENGTH_LONG).show();
                startActivity(Intent(activity, QiblaActivity::class.java))
            "allah_names" -> startActivity(Intent(activity, NamesActivity::class.java).putExtra("type","Allah"))
            "muhammad_names" -> startActivity(Intent(activity, NamesActivity::class.java).putExtra("type","Muhammad"))
            "6_kalimas" -> startActivity(Intent(activity, KalimaActivity::class.java))
            "shahdat" -> startActivity(Intent(activity, ShahadaActivity::class.java))
            "search" -> {
            }
            "bookmarks" -> startActivity(Intent(activity, BookmarkActivity::class.java))
            "rate_us" -> try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + requireActivity().packageName)
                    )
                )
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + requireActivity().packageName)
                    )
                )
            }
            "share_app" -> shareApp()
            "settings" -> startActivity(Intent(activity, SettingsActivity::class.java))
            "calendar" -> startActivity(Intent(activity, CalendarActivity::class.java))
//            "about" -> startActivity(Intent(activity, About::class.java))
        }
    }

    private fun shareApp() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody = """
               There\'s an app where you just select your emotions and current feelings (e.g ANGRY,CONFIDENT,INSECURE etc. and it gives you an Ayat or Surah (in ARABIC, URDU &amp; ENGLISH) that correlates with it.
               (Available on Playstore)
               https://play.google.com/store/apps/details?id=com.mianasad.myislam
               
               Pass it to everyone you know. It\s Awesome!
               """.trimIndent()
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }
}