package com.example.ch14tasksroomdbhfrerev.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ch14tasksroomdbhfrerev.databinding.ItemTaskCardBinding
import com.example.ch14tasksroomdbhfrerev.databinding.ItemTaskCardNewBinding
import com.example.ch14tasksroomdbhfrerev.interfaces.DeleteImgClickListener
import com.example.ch14tasksroomdbhfrerev.interfaces.TaskCheckBoxClickedListner
import com.example.ch14tasksroomdbhfrerev.interfaces.TaskClickListener
import com.example.ch14tasksroomdbhfrerev.model.Task

class TaskItemAdapterNew(private val context: Context, private val taskClickListener: TaskClickListener, private val taskCheckBoxClickedListner: TaskCheckBoxClickedListner, private val deleteImgClickListener: DeleteImgClickListener):ListAdapter<Task, TaskItemAdapterNew.TaskViewHolder>(TaskDiffItemCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        //return TaskViewHolder(ItemTaskCardBinding.inflate(LayoutInflater.from(context), parent, false))
        return TaskViewHolder(ItemTaskCardNewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        holder.bind(getItem(position), taskClickListener, taskCheckBoxClickedListner, deleteImgClickListener, position)

    }


//    class TaskViewHolder(val binding: ItemTaskCardBinding):RecyclerView.ViewHolder(binding.root){
    class TaskViewHolder(val binding: ItemTaskCardNewBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(currentTask: Task, onTaskClickListener:TaskClickListener, onTaskCheckBoxClickedListner: TaskCheckBoxClickedListner, onDeleteImgClickListener: DeleteImgClickListener,  itemPosition:Int){

            //binding.tasksNameTvCard.text = currentTask.taskName
            binding.taskNameNewTv.text = currentTask.taskName

            binding.taskDoneCheckboxNew.isChecked = currentTask.taskComplete

            binding.taskDoneCheckboxNew.setOnClickListener {

                Log.d("TASKITEMADAPTER", "Checkbox clicked")

                currentTask.taskComplete = (it as? CheckBox)?.isChecked ?: false

                onTaskCheckBoxClickedListner.onTaskCheckBoxClicked(task = currentTask, itemPosition)

            }

            binding.deleteButtonImgv.setOnClickListener {
                Log.d("TASKITEMADAPTER", "Delete Image Clicked")

                onDeleteImgClickListener.onDeleteImgClickListener(task = currentTask, itemPosition)

            }

            itemView.setOnClickListener {
                onTaskClickListener.onTaskClicked(task = currentTask, itemPosition)
            }

        }//bind()

    }//class TaskViewHolder
}//TaskItemAdapterNew