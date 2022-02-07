package com.example.turtleminttestapp.network.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.turtleminttestapp.data.IssueModel

@Database(entities = [IssueModel::class],
    version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getIssueModelDao() : DauIssueAccess

    companion object {

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDbClient(context: Context) : AppDataBase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, AppDataBase::class.java, "AppDatabase")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }
    }

}