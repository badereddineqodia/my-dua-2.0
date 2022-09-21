package com.districthut.islam.utils.beadscounter.views.counter

interface CounterViewActionsListener {
    fun onUpdateTotalBeads(number: Int)
    fun onUpdateDoneBeads(number: Int)
    fun onFinish()
}