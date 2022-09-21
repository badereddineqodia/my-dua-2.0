package com.mianasad.myislam.viewmodel

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mirfatif.noorulhuda.App


class MainViewModel : ViewModel() {

    val title =  MutableLiveData<String>()
    val locationUpdate = MutableLiveData<Boolean>()

    fun locationUpdated(isLocationUpdated: Boolean) {
        locationUpdate.value = isLocationUpdated
    }

    fun setTitle(text: String) {
        title.value = text
    }

    fun hideActionBar() {

    }


    fun getDrawable(name: String?): Drawable? {
        val context: Context = App.getCxt()
        val resourceId = context.resources.getIdentifier(
            name,
            "drawable",
            App.getCxt().packageName
        )
        return ActivityCompat.getDrawable(context,resourceId)
    }

}