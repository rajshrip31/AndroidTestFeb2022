package com.example.turtleminttestapp.viewModel

import android.app.Application
import android.database.DatabaseUtils
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.turtleminttestapp.data.IssueModel
import com.example.turtleminttestapp.network.api.ApiHelper
import com.example.turtleminttestapp.network.api.RetrofitBuilder
import com.example.turtleminttestapp.network.repository.MainRepository
import com.example.turtleminttestapp.network.room.AppDataBase
import com.example.turtleminttestapp.utils.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class IssueDetailsViewModel(app: Application) : AndroidViewModel(app) {

    private var mainRepository: MainRepository
    var issuesLiveData = MutableLiveData<List<IssueModel>>()
    var statusLiveData = MutableLiveData<Status>()
    var errorLiveData = MutableLiveData<String>()
    private val scope = CoroutineScope(Dispatchers.IO)
    var appli: Application
    var mdAppDatabase: AppDataBase? = null


    init {
        val apiHelper = ApiHelper(RetrofitBuilder.apiService)
        mainRepository = MainRepository(apiHelper)
        appli = app
        mdAppDatabase = AppDataBase.getDbClient(appli)

    }

    fun getIssues() {
        statusLiveData.value = Status.LOADING
        scope.launch {
            try {

             val cursor =   mdAppDatabase?.getIssueModelDao()?.getCursorAll()
                DatabaseUtils.dumpCursor(cursor)

                val list = mdAppDatabase?.getIssueModelDao()?.gelAllIssues()
                if (list?.isNotEmpty() == true) {
                    issuesLiveData.postValue(list)
                } else {
                    val data = mainRepository.getIssues()
                    for (item in data) {
                        mdAppDatabase?.getIssueModelDao()?.insertIssues(item)
                    }
                    issuesLiveData.postValue(data)
                }
                statusLiveData.postValue(Status.SUCCESS)
            } catch (exception: Exception) {
                statusLiveData.postValue(Status.ERROR)
                errorLiveData.postValue(exception.message ?: "Error Occurred!")
                Log.d("DATA", "MSG : " + exception.message)
            }
        }
    }


}