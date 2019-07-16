package com.keytotech.task.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.keytotech.task.R

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG =  "MainActivity"
    }
    private lateinit var navigator : NavigationController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "onCreate")
        navigator = ViewModelProviders.of(this).get(NavigationController::class.java)

        navigator.getNavigationActions().observe(this, Observer { actionId ->

            Log.i(TAG, "navigationActionObserver, navigation action id: $actionId")
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(actionId)
        })
    }
}
