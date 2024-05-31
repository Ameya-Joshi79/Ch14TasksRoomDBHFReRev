package com.example.ch14tasksroomdbhfrerev.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ch14tasksroomdbhfrerev.interfaces.TaskDao
import com.example.ch14tasksroomdbhfrerev.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase:RoomDatabase() {

    //abstract val taskDao:TaskDao

    abstract fun userDao():TaskDao

    companion object{

        @Volatile
        private var INSTANCE:TaskDatabase? = null

        fun getInstance(context:Context):TaskDatabase{

            synchronized(this){

                var instance = INSTANCE

                if (instance == null){

                    instance = Room.databaseBuilder(context.applicationContext, TaskDatabase::class.java, "tasks_database").build()

                    INSTANCE = instance

                }
                return instance
            }
        }


    }


}