package com.keytotech.task.screens.comments

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.keytotech.task.R
import com.keytotech.task.data.Comment


import kotlinx.android.synthetic.main.comment_item.view.*


class MyCommentRecyclerViewAdapter(
    private val mValues: ArrayList<Comment>
    ) : RecyclerView.Adapter<MyCommentRecyclerViewAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.id.text = item.id.toString()
        holder.body.text = item.body
        holder.email.text = item.email
        holder.name.text = item.name
    }

    fun appendItems(items: ArrayList<Comment>){
        val pos = mValues.size
        mValues.addAll(items)
        mValues.sortBy { comment-> comment.id  }
        notifyItemInserted(pos)

    }
    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val id: TextView = mView.comment_id
        val name: TextView = mView.name
        val body: TextView = mView.body
        val email: TextView = mView.email
    }
}
