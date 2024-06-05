package com.example.ch14tasksroomdbhfrerev.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ch14tasksroomdbhfrerev.interfaces.TaskDao
import com.example.ch14tasksroomdbhfrerev.model.Task
import kotlinx.coroutines.launch

class TaskDetailsFragmentViewModel(val taskDao: TaskDao):ViewModel() {

    private val _navigateHome:MutableLiveData<Boolean> = MutableLiveData()

    val navigateHome:LiveData<Boolean>
        get() = _navigateHome

    fun updateTask(task:Task){

        viewModelScope.launch {
         taskDao.update(task)
        }.invokeOnCompletion {

            if (it == null){

                Log.d("TASKDETAILSFRAGMENTVIEWMODEL", "Task Updated in the Database")
                _navigateHome.value = true
            }else{
                Log.d("TASKDETAILSFRAGMENTVIEWMODEL", "Error: ${it.message}")
            }

        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch {
            taskDao.delete(task)
        }.invokeOnCompletion {
            Log.d("TASKDETAILSFRAGMENTVIEWMODEL", "Task deleted from the Database")
            _navigateHome.value = true
        }
    }


}