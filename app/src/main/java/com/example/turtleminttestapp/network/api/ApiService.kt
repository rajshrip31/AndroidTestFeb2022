package com.example.turtleminttestapp.network.api

import com.example.turtleminttestapp.data.CommentsModelItem
import com.example.turtleminttestapp.data.IssueModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface ApiService {

    @GET("square/okhttp/issues")
    suspend fun getIssues(): ArrayList<IssueModel>

    @GET("square/okhttp/issues/{id}/comments")
    suspend fun getComments(@Path("id") id: String): ArrayList<CommentsModelItem>

}