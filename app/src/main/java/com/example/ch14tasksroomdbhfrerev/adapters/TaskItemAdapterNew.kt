package com.example.ch14tasksroomdbhfrerev.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ch14tasksroomdbhfrerev.databinding.ItemTaskCardBinding
import com.example.ch14tasksroomdbhfrerev.interfaces.TaskCheckBoxClickedListner
import com.example.ch14tasksroomdbhfrerev.interfaces.TaskClickListener
import com.example.ch14tasksroomdbhfrerev.model.Task

class TaskItemAdapterNew(private val context: Context, private val taskClickListener: TaskClickListener, private val taskCheckBoxClickedListner: TaskCheckBoxClickedListner):ListAdapter<Task, TaskItemAdapterNew.TaskViewHolder>(TaskDiffItemCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(ItemTaskCardBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        holder.bind(getItem(position), taskClickListener, taskCheckBoxClickedListner, position)

    }


    class TaskViewHolder(val binding: ItemTaskCardBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(currentTask: Task, onTaskClickListener:TaskClickListener, onTaskCheckBoxClickedListner: TaskCheckBoxClickedListner, itemPosition:Int){

            binding.tasksNameTvCard.text = currentTask.taskName

            binding.taskDoneCheckbox.isChecked = currentTask.taskComplete

            binding.taskDoneCheckbox.setOnClickListener {

                Log.d("TASKITEMADAPTER", "Checkbox clicked")

                currentTask.taskComplete = (it as? CheckBox)?.isChecked ?: false

                onTaskCheckBoxClickedListner.onTaskCheckBoxClicked(task = currentTask, itemPosition)

            }

            itemView.setOnClickListener {
                onTaskClickListener.onTaskClicked(task = currentTask, itemPosition)
            }

        }//bind()

    }//class TaskViewHolder
}//TaskItemAdapterNew