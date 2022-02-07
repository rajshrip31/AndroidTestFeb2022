package com.example.turtleminttestapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.turtleminttestapp.adapter.IssuesViewAdapter
import com.example.turtleminttestapp.databinding.ActivityMainBinding
import com.example.turtleminttestapp.utils.BaseActivity
import com.example.turtleminttestapp.utils.Status
import com.example.turtleminttestapp.viewModel.IssueDetailsViewModel

class MainActivity : BaseActivity() {

    private lateinit var viewModel: IssueDetailsViewModel
    private lateinit var adapter: IssuesViewAdapter
    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        viewModel = ViewModelProvider(this).get(IssueDetailsViewModel::class.java)
        attachObserver()
        viewModel.getIssues()

        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = IssuesViewAdapter(this,arrayListOf())
        dataBinding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                dataBinding.recyclerView.context,
                (dataBinding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )

    }

    private fun attachObserver() {

        viewModel.issuesLiveData.observe(this) {
            adapter = IssuesViewAdapter(this,it)
            dataBinding.recyclerView.adapter = adapter
        }

        viewModel.statusLiveData.observe(this) {

            if (it == Status.LOADING) {
                dataBinding.progressBar.visibility = View.VISIBLE
                dataBinding.recyclerView.visibility = View.GONE
                dataBinding.tvError.visibility = View.GONE
            }
            if (it == Status.SUCCESS) {
                dataBinding.progressBar.visibility = View.GONE
                dataBinding.recyclerView.visibility = View.VISIBLE
                dataBinding.tvError.visibility = View.GONE
            }
            if (it == Status.ERROR) {
                dataBinding.progressBar.visibility = View.GONE
                dataBinding.recyclerView.visibility = View.GONE
                dataBinding.tvError.visibility = View.VISIBLE
            }
        }

        viewModel.errorLiveData.observe(this){
            dataBinding.tvError.text = it
        }
    }
}