package com.example.turtleminttestapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.turtleminttestapp.adapter.CommentsAdapter
import com.example.turtleminttestapp.databinding.ActivityIssuesDetailsBinding
import com.example.turtleminttestapp.utils.BaseActivity
import com.example.turtleminttestapp.utils.Status
import com.example.turtleminttestapp.viewModel.CommentsDetailsViewModel

class CommentsDetailsActivity : BaseActivity() {

    private lateinit var dataBinding: ActivityIssuesDetailsBinding
    private lateinit var viewModel: CommentsDetailsViewModel
    private lateinit var adapter: CommentsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*setContentView(R.layout.activity_issues_details)*/
        dataBinding = ActivityIssuesDetailsBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        setUI()
    }

    private fun setUI(){
        (this as AppCompatActivity).supportActionBar!!.title = "Issues Details & Comments"
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        val title = intent.getStringExtra("TITLE")
        val desc = intent.getStringExtra("DESC")
        val comUrl = intent.getStringExtra("COMMENTS_URL")

        dataBinding.tvTitleCom.text = title
        dataBinding.tvDescCom.text = desc

        viewModel = ViewModelProvider(this).get(CommentsDetailsViewModel::class.java)
        attachObserver()

        var commentId:String = (comUrl.toString()).substringAfter("/okhttp/issues/", "/")

        var realId : String = commentId.replace("/comments","")
        Log.d("DATA", "REAL ID : $realId")

        if(isNetworkAvailable(this)){
            viewModel.getComments(realId)
        }
        else{
            dataBinding.tvError.visibility = View.VISIBLE
            dataBinding.tvError.text = "Internet Not Available"
        }

        dataBinding.recyclerViewCom.layoutManager = LinearLayoutManager(this)
        adapter = CommentsAdapter(this,arrayListOf())
        dataBinding.recyclerViewCom.addItemDecoration(
            DividerItemDecoration(
                dataBinding.recyclerViewCom.context,
                (dataBinding.recyclerViewCom.layoutManager as LinearLayoutManager).orientation
            )
        )

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun attachObserver() {
        viewModel.commentsLiveData.observe(this) {
            adapter = CommentsAdapter(this,it)
            dataBinding.recyclerViewCom.adapter = adapter
        }

        viewModel.statusLiveData.observe(this) {

            if (it == Status.LOADING) {
                dataBinding.progressBar.visibility = View.VISIBLE
                dataBinding.recyclerViewCom.visibility = View.GONE
                dataBinding.tvError.visibility = View.GONE
            }
            if (it == Status.SUCCESS) {
                dataBinding.progressBar.visibility = View.GONE
                dataBinding.recyclerViewCom.visibility = View.VISIBLE
                dataBinding.tvError.visibility = View.GONE
            }
            if (it == Status.ERROR) {
                dataBinding.progressBar.visibility = View.GONE
                dataBinding.recyclerViewCom.visibility = View.GONE
                dataBinding.tvError.visibility = View.VISIBLE
            }
        }

        viewModel.errorLiveData.observe(this){
            dataBinding.tvError.text = it
        }
    }

}