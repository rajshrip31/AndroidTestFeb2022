package com.example.turtleminttestapp.network.room

import androidx.room.TypeConverter
import com.example.turtleminttestapp.data.IssueModel
import com.example.turtleminttestapp.data.User
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun JsonToString(value: User): String = Gson().toJson(value)

    @TypeConverter
    fun stringToJson(value: String) = Gson().fromJson(value, User::class.java)

    @TypeConverter
    fun JsonToIString(value: IssueModel): String = Gson().toJson(value)

    @TypeConverter
    fun stringToIJson(value: String) = Gson().fromJson(value, IssueModel::class.java)

    @TypeConverter
    fun listToJsonString(value: List<IssueModel>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<IssueModel>::class.java).toList()

    @TypeConverter
    fun fromString(stringListString: String): List<String> {
        return stringListString.split(",").map { it }
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(separator = ",")
    }




}