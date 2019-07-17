package com.keytotech.task.screens.comments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.keytotech.task.R
import com.keytotech.task.main.NavigationController
import com.keytotech.task.model.CommentsDataViewModel

import kotlinx.android.synthetic.main.fragment_comment_list.*


class CommentsFragment : Fragment() {


    companion object {
        private const val TAG = "CommentsFragment"
    }

    private  lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private lateinit var commentsDataViewModel: CommentsDataViewModel
    private lateinit var navigator : NavigationController

    val adapter = MyCommentRecyclerViewAdapter(
       ArrayList()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comment_list, container, false)

        commentsDataViewModel = activity?.run {
            ViewModelProviders.of(this).get(CommentsDataViewModel::class.java)
        } ?: throw Exception("Invalid Fragment")

        navigator = activity?.run {
            ViewModelProviders.of(this).get(NavigationController::class.java)
        } ?: throw Exception("Invalid Fragment")


        requireActivity().onBackPressedDispatcher.addCallback(this,  object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
               Log.i(TAG, "OnBackPressed")
                commentsDataViewModel.clear()
                navigator.navigate(R.id.action_commentsFragment_to_boundsChoosingFragment)
            }
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loading_layout.visibility =View.VISIBLE
        initCommentsList()
        commentsDataViewModel.getCommentsData().observe(this, Observer {
            adapter.appendItems(it.comments)
            loading_layout.visibility = View.GONE
        })
    }

    private fun initCommentsList(){
        val linearLayoutManager = LinearLayoutManager(context)

        comments_list.adapter = adapter
        comments_list.setLayoutManager(linearLayoutManager)
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                loading_layout.visibility =View.VISIBLE
                commentsDataViewModel.loadComments(page)
            }
        }
        comments_list.addOnScrollListener(scrollListener)
        commentsDataViewModel.loadComments(0)
    }
}
