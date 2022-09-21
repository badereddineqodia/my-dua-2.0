package com.districthut.islam.utils.beadscounter

import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.districthut.islam.utils.AppManager
import com.districthut.islam.utils.beadscounter.views.counter.CounterViewActionsListener
import com.districthut.islam.utils.beadscounter.views.counter.models.CounterViewData
import com.mirfatif.noorulhuda.R
import com.mirfatif.noorulhuda.databinding.ActivityBeadsBinding

private const val EXTRA_COUNTER_VIEW = "com.districthut.islam.utils.beadscounter.counter_view_data"

class BeadsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBeadsBinding
    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val defaultPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val isDarkMode = defaultPreferences.getBoolean("dark_mode", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.darkTheme)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityBeadsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupCounterViewListener()
        setupEditBeadsButton()

        try {
        binding.loopCounter.text = "Loop " + Integer.parseInt(AppManager.getValue("tasbeeh_loops"))
        } catch (ex: Exception) {}

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        restoreUIState(savedInstanceState)
    }

    private fun setupCounterViewListener() {

        with(binding) {
            counterView.actionsListener = object : CounterViewActionsListener {

                override fun onUpdateTotalBeads(number: Int) {
                    totalBeadsTv.text = getString(R.string.total_beads_count, number)
                }

                override fun onUpdateDoneBeads(number: Int) {
                    doneBeadsTv.text = number.toString()
                }

                override fun onFinish() {
                    var currentLoops = 1
                    if(!AppManager.getValue("tasbeeh_loops").equals(""))
                         currentLoops = Integer.parseInt(AppManager.getValue("tasbeeh_loops"))
                    currentLoops += 1
                    AppManager.saveValue("tasbeeh_loops",(currentLoops).toString())
                    binding.loopCounter.text = "Loop $currentLoops"
                    binding.counterView.reset()
//                    displayRestartDialog { binding.counterView.reset() }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
    }

    private fun setupEditBeadsButton() {
        binding.editIcon.setOnClickListener {
            displayBeadsCountDialog { count ->
                binding.counterView.reset(count)
            }
        }
    }

    private fun restoreUIState(bundle: Bundle?) {
        bundle?.getParcelable<CounterViewData>(EXTRA_COUNTER_VIEW)?.let {
            binding.counterView.setInitialData(it)
        }
    }

    private fun displayBeadsCountDialog(action: (Int) -> Unit) {
        dialog?.dismiss()
        dialog = AlertDialog.Builder(this).apply {
            val view = NumberPicker(this@BeadsActivity).also {
                it.maxValue = 99
                it.minValue = 1
            }
            setView(view)
            setCancelable(true)
            setTitle(R.string.dialog_bead_count_title)
            setPositiveButton(android.R.string.ok) { d, _ ->
                action.invoke(view.value)
                d.dismiss()
            }
            setNegativeButton(android.R.string.cancel) { d, _ ->
                d.dismiss()
            }
        }.show()
    }

    private fun displayRestartDialog(action: () -> Unit) {
        action.invoke()
//        dialog?.dismiss()
//        dialog = AlertDialog.Builder(this).apply {
//            setCancelable(false)
//            setTitle(R.string.dialog_restart_title)
//            setMessage(R.string.dialog_restart_message)
//            setPositiveButton(android.R.string.ok) { d, _ ->
//                action.invoke()
//                d.dismiss()
//            }
//            setNegativeButton(android.R.string.cancel) { d, _ ->
//                d.dismiss()
//            }
//        }.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(EXTRA_COUNTER_VIEW, binding.counterView.getData())
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog?.dismiss()
        binding.counterView.clear()
    }

}