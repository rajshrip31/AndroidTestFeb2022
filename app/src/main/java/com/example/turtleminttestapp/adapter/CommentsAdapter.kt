package com.example.turtleminttestapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.turtleminttestapp.R
import com.example.turtleminttestapp.data.CommentsModelItem
import kotlinx.android.synthetic.main.item_layout_comments.view.*

class CommentsAdapter(private val context: Context, private val commentList: List<CommentsModelItem>) : RecyclerView.Adapter<CommentsAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(comment: CommentsModelItem) {
            Log.d("Bind User data", "Comment : ${comment.toString()}")
            itemView.apply {
                tv_com_desc.text = comment.body
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout_comments, parent, false))

    override fun getItemCount(): Int = commentList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(commentList[position])
    }


}