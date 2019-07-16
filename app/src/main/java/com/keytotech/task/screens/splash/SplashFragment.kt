package com.keytotech.task.screens.splash


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.keytotech.task.R
import com.keytotech.task.main.NavigationController


class SplashFragment : Fragment() {

    private lateinit var navigator : NavigationController
    companion object {
        private const val DELAY = 1000L
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        navigator = activity?.run {
            ViewModelProviders.of(this).get(NavigationController::class.java)
        } ?: throw Exception("Invalid Fragment")

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.postDelayed({navigator.navigate(R.id.action_splashFragment_to_boundsChoosingFragment)}, DELAY)
    }
}
