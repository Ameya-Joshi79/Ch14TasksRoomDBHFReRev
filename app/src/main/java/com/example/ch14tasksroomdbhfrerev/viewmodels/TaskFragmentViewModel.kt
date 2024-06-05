package com.example.ch14tasksroomdbhfrerev.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.ch14tasksroomdbhfrerev.interfaces.TaskDao
import com.example.ch14tasksroomdbhfrerev.model.Task
import kotlinx.coroutines.launch


class TaskFragmentViewModel(val taskDao: TaskDao):ViewModel() {

    var taskName:String = ""

    val tasks:LiveData<List<Task>>
        get() = taskDao.getAllTasks()


    private val _navigateTask:MutableLiveData<Task?> = MutableLiveData()

    val navigateTask:LiveData<Task?>
        get() = _navigateTask

    val taskListString:LiveData<String>
        get() = tasks.map {
            //formatTask(it)

            if (it.isNotEmpty()){
                it.map { task:Task ->
                    "TaskID: ${task.taskId} \n TaskName: ${task.taskName} \n TaskComplete: ${if (task.taskComplete) "YES" else "NO"} \n"
                }
                    .reduce { acc, s ->
                          acc + "\n" + s
                    }
            }else{
                ""
            }

        }

    fun addTask(){

        viewModelScope.launch {

            val taskToAdd = Task()

            taskToAdd.taskName = taskName

            taskDao.insert(taskToAdd)

        }

    }//addTask


    fun updateTask(task:Task){
        viewModelScope.launch {
            taskDao.update(task = task)
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch {
            taskDao.delete(task)
        }
    }

    fun setNavigateTask(task:Task){
        _navigateTask.value = task
    }


    fun onTaskNavigated(){
        _navigateTask.value = null
    }


    fun formatTask(tasks:List<Task>):String{

        var tasksString:String = ""

        if (tasks.isNotEmpty()) {

            tasksString = tasks.map {
                "TaskID: ${it.taskId} \n TaskName: ${it.taskName} \n TaskComplete: ${if (it.taskComplete) "YES" else "NO"}"
            }.reduce { acc, next ->
                return acc + "\n" + next
            }
        }

        return tasksString
    }


}