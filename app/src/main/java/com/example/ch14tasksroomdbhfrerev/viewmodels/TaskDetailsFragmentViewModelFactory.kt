package com.example.ch14tasksroomdbhfrerev.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ch14tasksroomdbhfrerev.interfaces.TaskDao

class TaskDetailsFragmentViewModelFactory(private val dao: TaskDao):ViewModelProvider.Factory {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(TaskDetailsFragmentViewModel::class.java))
            return TaskDetailsFragmentViewModel(dao) as T

        throw IllegalArgumentException("Unknown ViewModel")

    }



}