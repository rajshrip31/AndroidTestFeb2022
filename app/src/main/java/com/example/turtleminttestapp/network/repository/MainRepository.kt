package com.example.turtleminttestapp.network.repository

import com.example.turtleminttestapp.network.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getIssues() = apiHelper.getUsers()

    suspend fun getDataComments(id:String) = apiHelper.getCommentsData(id)
}