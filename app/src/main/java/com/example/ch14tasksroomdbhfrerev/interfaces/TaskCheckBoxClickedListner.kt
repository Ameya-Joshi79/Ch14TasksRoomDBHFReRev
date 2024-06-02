package com.example.ch14tasksroomdbhfrerev.interfaces

import com.example.ch14tasksroomdbhfrerev.model.Task

interface TaskCheckBoxClickedListner {

    fun onTaskCheckBoxClicked(task:Task, itemPosition:Int)

}