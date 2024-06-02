package com.example.ch14tasksroomdbhfrerev.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.ch14tasksroomdbhfrerev.databinding.ItemTaskBinding
import com.example.ch14tasksroomdbhfrerev.databinding.ItemTaskCardBinding
import com.example.ch14tasksroomdbhfrerev.interfaces.TaskCheckBoxClickedListner
import com.example.ch14tasksroomdbhfrerev.interfaces.TaskClickListener
import com.example.ch14tasksroomdbhfrerev.model.Task

class TaskItemAdapter(private val context: Context, var taskList:MutableList<Task>, private val taskClickListener: TaskClickListener, private val taskCheckBoxClickedListner: TaskCheckBoxClickedListner):RecyclerView.Adapter<TaskItemAdapter.TaskItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {

//        return TaskItemViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(context), parent, false))

        return TaskItemViewHolder(ItemTaskCardBinding.inflate(LayoutInflater.from(context), parent, false))

    }



    override fun getItemCount(): Int {

        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {

        holder.bind(taskList[position], taskClickListener, taskCheckBoxClickedListner, position)

    }

    class TaskItemViewHolder(val itemTaskCardBinding:ItemTaskCardBinding):RecyclerView.ViewHolder(itemTaskCardBinding.root){

        fun bind(currentTask: Task, onTaskClickListener:TaskClickListener, onTaskCheckBoxClickedListner: TaskCheckBoxClickedListner, itemPosition:Int){

            itemTaskCardBinding.tasksNameTvCard.text = currentTask.taskName

            itemTaskCardBinding.taskDoneCheckbox.isChecked = currentTask.taskComplete

            itemTaskCardBinding.taskDoneCheckbox.setOnClickListener {

                Log.d("TASKITEMADAPTER", "Checkbox clicked")

                currentTask.taskComplete = (it as? CheckBox)?.isChecked ?: false

               onTaskCheckBoxClickedListner.onTaskCheckBoxClicked(task = currentTask, itemPosition)

            }

            itemView.setOnClickListener {

                onTaskClickListener.onTaskClicked(task = currentTask, itemPosition)

            }

        }



    }//class TaskItemViewHolder

}