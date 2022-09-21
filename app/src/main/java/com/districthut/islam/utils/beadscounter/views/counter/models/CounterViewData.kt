package com.districthut.islam.utils.beadscounter.views.counter.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CounterViewData(
    val beadsCount: Int,
    val beadsList: List<Bead>,
    val pointList: List<Point>,
) : Parcelable