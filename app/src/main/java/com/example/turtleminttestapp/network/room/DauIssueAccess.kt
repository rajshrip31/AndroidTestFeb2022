package com.example.turtleminttestapp.network.room

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turtleminttestapp.data.IssueModel

@Dao
interface DauIssueAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIssues(issueModel: IssueModel)

    @Query("Select * from issue_model order by id DESC")
    fun gelAllIssues(): List<IssueModel>

    @Query("SELECT * FROM issue_model")
    fun getCursorAll(): Cursor

}