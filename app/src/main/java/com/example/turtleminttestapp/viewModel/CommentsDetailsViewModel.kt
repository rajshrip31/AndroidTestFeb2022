package com.example.turtleminttestapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.turtleminttestapp.data.CommentsModelItem
import com.example.turtleminttestapp.data.IssueModel
import com.example.turtleminttestapp.network.api.ApiHelper
import com.example.turtleminttestapp.network.api.RetrofitBuilder
import com.example.turtleminttestapp.network.repository.MainRepository
import com.example.turtleminttestapp.utils.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentsDetailsViewModel (app: Application) : AndroidViewModel(app) {

    private var mainRepository: MainRepository
    var commentsLiveData = MutableLiveData<List<CommentsModelItem>>()
    var statusLiveData = MutableLiveData<Status>()
    var errorLiveData = MutableLiveData<String>()
    private val scope = CoroutineScope(Dispatchers.IO)
    var appli: Application


    init {
        val apiHelper = ApiHelper(RetrofitBuilder.apiService)
        mainRepository = MainRepository(apiHelper)
        appli = app

    }

    fun getComments(id:String) {
        statusLiveData.value = Status.LOADING
        scope.launch {

            try {
                    val data = mainRepository.getDataComments(id)
                commentsLiveData.postValue(data)
                statusLiveData.postValue(Status.SUCCESS)
            } catch (exception: Exception) {
                statusLiveData.postValue(Status.ERROR)
                errorLiveData.postValue(exception.message ?: "Error Occurred!")
                Log.d("DATA", "MSG : " + exception.message)
            }
        }
    }


}