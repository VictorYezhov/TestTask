package com.keytotech.task.screens.bounds_choosing

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keytotech.task.util.SingleEventLiveData

class BoundsChoosingViewModel : ViewModel(){

    companion object {
        private const val TAG = "BoundsChoosingViewModel"
    }
    private val errorCheckerLiveData = SingleEventLiveData<Boolean>()


    fun checkingResult() = errorCheckerLiveData


    fun checkBounds(lower:String, upper:String) {
        Log.i(TAG, "checkBounds, lower: [$lower] upper: [$upper]")
        errorCheckerLiveData.value = !(!lower.isBlank()
                && !upper.isBlank()
                && lower.toIntOrNull() != null
                && upper.toIntOrNull() != null)
    }


}