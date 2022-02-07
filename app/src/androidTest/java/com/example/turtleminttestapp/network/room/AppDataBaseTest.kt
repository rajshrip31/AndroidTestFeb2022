package com.example.turtleminttestapp.network.room


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.turtleminttestapp.data.IssueModel
import com.example.turtleminttestapp.data.User
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDataBaseTest : TestCase() {

    private lateinit var dataBase: AppDataBase
    private lateinit var dao: DauIssueAccess

    @Before
    public override fun setUp() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        dataBase = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        dao = dataBase.getIssueModelDao()
    }

    @After
    public override fun tearDown() {
    }

    @Test
    fun getCloseLock() {
        dataBase.close()
    }

    @Test
    fun writeAndReadIssues() = runBlocking{
        val user = User("","",1001,"")
        val model = IssueModel("Test body",2,"https:/test.com",
            "Testing","2022-01-08T16:17:11Z", user)
        dao.insertIssues(model)
        val modelDataList = dao.gelAllIssues()
       // assertThat(modelDataList.contains(model).)
    }
}