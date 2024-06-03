package com.example.ch14tasksroomdbhfrerev.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.ch14tasksroomdbhfrerev.model.Task

class TaskDiffItemCallBack:DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.taskId == newItem.taskId
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {

        return oldItem == newItem

    }


}