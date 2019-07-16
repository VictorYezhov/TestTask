package com.keytotech.task.screens.bounds_choosing

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.keytotech.task.R
import kotlinx.android.synthetic.main.fragment_bounds_choosing.*


class BoundsChoosingFragment : Fragment() {

    companion object {
        private const val TAG = "BoundsChoosingFragment"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bounds_choosing, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        submit_button.setOnClickListener {
            val upperBound=  upper_bound.text.toString()
            val lowerBound = lower_bound.text.toString()
            Log.i(TAG, "lower Bound: $upperBound")
            Log.i(TAG, "upper Bound: $lowerBound")
        }


    }
}
