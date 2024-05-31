package com.example.ch14tasksroomdbhfrerev.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ch14tasksroomdbhfrerev.interfaces.TaskDao

class TaskFragmentViewModelFactory(private val taskDao: TaskDao):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(TaskFragmentViewModel::class.java)){
            return TaskFragmentViewModel(taskDao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")

    }



}