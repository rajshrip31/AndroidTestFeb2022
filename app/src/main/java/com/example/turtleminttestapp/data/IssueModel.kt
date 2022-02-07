package com.example.turtleminttestapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "issue_model")
data class IssueModel(
    @ColumnInfo(name = "desc")
    var body: String?,
    @ColumnInfo(name = "comments_counts")
    val comments: Int,
    @ColumnInfo(name = "comments_url")
    val comments_url: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "update_date")
    val updated_at: String,
    @ColumnInfo(name = "user_data")
    var user: User
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}


data class User(
    val avatar_url: String,
    val gravatar_id: String,
    val id: Int,
    val login: String,

)
