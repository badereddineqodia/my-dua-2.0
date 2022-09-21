package com.districthut.islam.views

import com.mirfatif.noorulhuda.ui.base.BaseActivity
import android.os.Bundle
import android.view.WindowManager
import com.mirfatif.noorulhuda.ui.dialog.AlertDialogFragment
import com.mirfatif.noorulhuda.R
import android.content.Intent
import android.os.Handler
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.districthut.islam.views.SplashActivity
import com.mianasad.myislam.views.HomeActivity
import com.mirfatif.noorulhuda.databinding.ActivitySplashBinding
import com.mirfatif.noorulhuda.db.DbBuilder
import com.mirfatif.noorulhuda.prefs.MySettings
import com.mirfatif.noorulhuda.ui.dialog.MainAlertDialogFragment
import com.mirfatif.noorulhuda.util.Utils

class SplashActivity : BaseActivity() {
    var binding: ActivitySplashBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        supportActionBar!!.hide()

        if (MySettings.SETTINGS.isDbBuilt(DbBuilder.MAIN_DB)) {
            //Toast.makeText(this,"Ready", Toast.LENGTH_SHORT).show()
            startSplashTimer()
        } else {
            binding?.firstStart!!.visibility = View.VISIBLE
            buildDbAndRefreshUi()
        }


    }

    private fun buildDbAndRefreshUi() {
        //val dialog = showDbBuildDialog()
        //Utils.showThirdPartyCredits(this, false);

        Utils.runBg {
            if (DbBuilder.buildDb(DbBuilder.MAIN_DB)) {

                //refreshUi(RestorePosType.NONE)
            }
            Utils.runUi(
                this
            ) {
                binding?.progressBar!!.visibility = View.GONE
                binding?.status!!.visibility = View.GONE


                binding?.animation!!.visibility = View.VISIBLE
                binding?.animation!!.playAnimation()

                Handler().postDelayed({
                    var i: Intent? = null
                    i = Intent(this@SplashActivity, HomeActivity::class.java)
                    startActivity(i)
                    finishAffinity()
                }, SPLASH_TIME_OUT.toLong())

                //dialog.dismissIt()
            }
        }
    }

    private fun showDbBuildDialog(): MainAlertDialogFragment {
        val builder = AlertDialog.Builder(this).setTitle(R.string.creating_database)
            .setView(R.layout.dialog_progress)
        val dialog = MainAlertDialogFragment.show(this, builder.create(), "BUILD_DATABASE")
        dialog.isCancelable = false
        return dialog
    }

    private fun startSplashTimer() {
        Handler().postDelayed({
            var i: Intent? = null
            i = Intent(this@SplashActivity, HomeActivity::class.java)
            //                if(SharedPreferencesManager.getValue("selectedLanguage").equals("")) {
//                    i = new Intent(SplashActivity.this, LanguageSelectionActivity.class);
//                } else {
//                    i = new Intent(SplashActivity.this, MainActivity.class);
//                }
            startActivity(i)
            finishAffinity()
        }, SPLASH_TIME_OUT.toLong())
    }

    companion object {
        private const val SPLASH_TIME_OUT = 1500
    }
}