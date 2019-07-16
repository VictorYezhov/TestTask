package com.keytotech.task.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NavigationController : ViewModel(){

    private val navigationActionsLiveData = MutableLiveData<Int>()

    fun getNavigationActions () = navigationActionsLiveData

    fun navigate(actionId:Int){
        navigationActionsLiveData.value = actionId
    }
}