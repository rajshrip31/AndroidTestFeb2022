package com.example.turtleminttestapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.turtleminttestapp.R
import com.example.turtleminttestapp.data.IssueModel
import kotlinx.android.synthetic.main.item_issue_data_layout.view.*
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import com.example.turtleminttestapp.view.CommentsDetailsActivity


class IssuesViewAdapter(private val context: Context, private val issueList: List<IssueModel>) : RecyclerView.Adapter<IssuesViewAdapter.DataViewHolder>() {


    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(issue: IssueModel) {
            Log.d("Bind User data", "USER : $issue")
            itemView.apply {
                tv_title.text = issue.title
                tv_desc.text= issue.body
                tv_user_name.text = issue.user.login

                tv_update_date.text = formatDate(issue.updated_at)

                Glide.with(iv_user_pic.context)
                    .load(issue.user.avatar_url)
                    .into(iv_user_pic)

                itemView.setOnClickListener {
                    val intent = Intent(context, CommentsDetailsActivity::class.java)
                    intent.putExtra("COMMENTS_URL",issue.comments_url)
                    intent.putExtra("TITLE",issue.title)
                    intent.putExtra("DESC",issue.body)
                    context.startActivity(intent)
                }
            }
        }

        private fun formatDate(date: String): CharSequence {
            val inputDate = date.substring(0,10)
            //val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())//2021-12-18T05:06:00Z
            val inputFormat = SimpleDateFormat("yyyy-MM-dd")//, Locale.getDefault())//2021-12-18T05:06:00Z
            val outputFormat = SimpleDateFormat("MM-dd-yyyy")
            //outputFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata");
            val parsedDate: Date = inputFormat.parse(inputDate)
            Log.d("DATA", "inputDate date : $inputDate")
            Log.d("DATA", "parsedDate date : $outputFormat.format(parsedDate)")
            return outputFormat.format(parsedDate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_issue_data_layout, parent, false))

    override fun getItemCount(): Int = issueList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(issueList[position])
    }


}