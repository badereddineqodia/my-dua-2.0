package com.districthut.islam.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.districthut.islam.adapters.NamesAdapter

import com.mianasad.myislam.models.database.HolyName
import com.mianasad.myislam.utils.database.AppDatabase
import com.mianasad.myislam.viewmodel.DatabaseViewModel
import com.mianasad.myislam.viewmodel.MainViewModel
import com.mirfatif.noorulhuda.databinding.ActivityNamesBinding

class NamesActivity : AppCompatActivity() {

    private lateinit var dbViewModel: DatabaseViewModel
    lateinit var binding: ActivityNamesBinding
    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNamesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        appDatabase = AppDatabase.getInstance(this)!!
        dbViewModel = DatabaseViewModel(appDatabase)

        val type: String? = intent.getStringExtra("type")

        supportActionBar!!.title = "$type Names"
        dbViewModel.getAllNames(type!!)
            .observe(this) { holyNames ->
                val adapter = NamesAdapter(holyNames)
                binding.recycler.adapter = adapter
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}