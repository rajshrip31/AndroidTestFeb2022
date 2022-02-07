package com.example.turtleminttestapp.network.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getUsers() = apiService.getIssues()

    suspend fun getCommentsData(id:String) = apiService.getComments(id)

}