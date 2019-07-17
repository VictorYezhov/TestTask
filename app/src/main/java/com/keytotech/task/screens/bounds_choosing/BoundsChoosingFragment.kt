package com.keytotech.task.screens.bounds_choosing

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.keytotech.task.R
import com.keytotech.task.main.NavigationController
import com.keytotech.task.model.CommentsDataViewModel
import kotlinx.android.synthetic.main.fragment_bounds_choosing.*
import android.widget.Toast





class BoundsChoosingFragment : Fragment() {

    companion object {
        private const val TAG = "BoundsChoosingFragment"
    }

    private lateinit var boundChoosingViewModel : BoundsChoosingViewModel
    private lateinit var commentsDataViewModel: CommentsDataViewModel
    private lateinit var navigator: NavigationController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        boundChoosingViewModel = ViewModelProviders.of(this).get(BoundsChoosingViewModel::class.java)

        navigator = activity?.run {
            ViewModelProviders.of(this).get(NavigationController::class.java)
        } ?: throw Exception("Invalid Fragment")

        commentsDataViewModel = activity?.run {
            ViewModelProviders.of(this).get(CommentsDataViewModel::class.java)
        } ?: throw Exception("Invalid Fragment")

        return inflater.inflate(R.layout.fragment_bounds_choosing, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
        setObservers()
    }

    private fun setListeners(){
        submit_button.setOnClickListener {
            val upperBound=  upper_bound.text.toString()
            val lowerBound = lower_bound.text.toString()
            Log.i(TAG, "lower Bound: [$lowerBound]")
            Log.i(TAG, "upper Bound: [$upperBound]")
            boundChoosingViewModel.checkBounds(lowerBound, upperBound)
        }
    }
    private fun setObservers(){
        boundChoosingViewModel.checkingResult().observe(this, Observer { hasError ->
            Log.i(TAG, "checkingResult, hasError:  [$hasError]")
            if(hasError){
                showErrorMessage()
            }else{
                commentsDataViewModel.setBounds(lower_bound.text.toString(), upper_bound.text.toString())
                navigator.navigate(R.id.action_boundsChoosingFragment_to_commentsFragment)
            }

        })
    }

    override fun onStop() {
        super.onStop()
    }

    private fun showErrorMessage(){
        Log.i(TAG, "showErrorMessage")
        val toast = Toast.makeText(
            context,
            resources.getString(R.string.error_text), Toast.LENGTH_SHORT
        )
        toast.show()
    }
}
