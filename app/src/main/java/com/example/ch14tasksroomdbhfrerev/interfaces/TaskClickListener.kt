package com.example.ch14tasksroomdbhfrerev.interfaces

import com.example.ch14tasksroomdbhfrerev.model.Task

interface TaskClickListener {

    fun onTaskClicked(task: Task, itemPosition:Int)

}