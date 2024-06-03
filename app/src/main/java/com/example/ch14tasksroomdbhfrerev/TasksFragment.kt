package com.example.ch14tasksroomdbhfrerev

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.ch14tasksroomdbhfrerev.adapters.TaskItemAdapter
import com.example.ch14tasksroomdbhfrerev.adapters.TaskItemAdapterNew
import com.example.ch14tasksroomdbhfrerev.databases.TaskDatabase
import com.example.ch14tasksroomdbhfrerev.databinding.FragmentTasks2Binding
import com.example.ch14tasksroomdbhfrerev.databinding.FragmentTasksBinding
import com.example.ch14tasksroomdbhfrerev.interfaces.TaskCheckBoxClickedListner
import com.example.ch14tasksroomdbhfrerev.interfaces.TaskClickListener
import com.example.ch14tasksroomdbhfrerev.model.Task
import com.example.ch14tasksroomdbhfrerev.viewmodels.TaskFragmentViewModel
import com.example.ch14tasksroomdbhfrerev.viewmodels.TaskFragmentViewModelFactory
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TasksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TasksFragment : Fragment(), TaskClickListener, TaskCheckBoxClickedListner {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //private var _fragmentTaskViewBinding:FragmentTasksBinding? = null

    private var _fragmentTaskViewBinding:FragmentTasks2Binding? = null

//    private val fragmentTaskViewBinding:FragmentTasksBinding
//        get() = _fragmentTaskViewBinding!!

    private val fragmentTaskViewBinding:FragmentTasks2Binding
        get() = _fragmentTaskViewBinding!!

    private lateinit var taskFragmentViewModel:TaskFragmentViewModel

    private lateinit var taskItemAdapter: TaskItemAdapter

    private lateinit var taskItemAdapterNew: TaskItemAdapterNew

    private var taskList:MutableList<Task> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_tasks, container, false)

       //_fragmentTaskViewBinding = FragmentTasksBinding.inflate(inflater, container, false)

        _fragmentTaskViewBinding = FragmentTasks2Binding.inflate(inflater, container, false)

        val view = fragmentTaskViewBinding.root

        val application = requireNotNull(this.activity).application

        val db = TaskDatabase.getInstance(application)

        val taskDao = db.userDao()

        val taskFragmentViewModelFactory = TaskFragmentViewModelFactory(taskDao)

        taskFragmentViewModel = ViewModelProvider(this, taskFragmentViewModelFactory).get(TaskFragmentViewModel::class.java)

        //taskItemAdapter = TaskItemAdapter(application,taskList, this, this)

        taskItemAdapterNew = TaskItemAdapterNew(application, this, this)

//        fragmentTaskViewBinding.taskRecyclerView.adapter = taskItemAdapter

        fragmentTaskViewBinding.taskRecyclerView.adapter = taskItemAdapterNew

        fragmentTaskViewBinding.taskRecyclerView.layoutManager = GridLayoutManager(application, 2)

        fragmentTaskViewBinding.addTaskBtn2.setOnClickListener {

            val taskName = fragmentTaskViewBinding.taskEdtTxt2.text.toString()

            fragmentTaskViewBinding.taskEdtTxt2.text = null

            taskFragmentViewModel.taskName = taskName

            taskFragmentViewModel.addTask()

        }//addTaskBtn.setOnClickListener





//        taskFragmentViewModel.taskListString.observe(viewLifecycleOwner){
//            if (it.isEmpty()){
//                fragmentTaskViewBinding.taskListTv.text = getString(R.string.no_tasks_to_show_txt)
//            }else{
//                Log.d("TASKFRAGMENT", it)
//                fragmentTaskViewBinding.taskListTv.text = it
//            }
//        }

//                taskFragmentViewModel.taskListString.observe(viewLifecycleOwner){
//            if (it.isEmpty()){
//                fragmentTaskViewBinding.taskListTv.text = getString(R.string.no_tasks_to_show_txt)
//            }else{
//                Log.d("TASKFRAGMENT", it)
//                fragmentTaskViewBinding.taskListTv.text = it
//            }
//        }


        return view
    }


    override fun onStart() {
        super.onStart()

        this.taskList.clear()

        taskFragmentViewModel.tasks.observe(viewLifecycleOwner){

//            if (it.isNotEmpty()){
//                taskList.clear()
//                lifecycleScope.launch {
//                    it.forEach {task:Task ->
//                        taskList.add(task)
//                    }
//                }
//
//            }
//
//            taskItemAdapter.notifyDataSetChanged()

            it?.let {
                taskItemAdapterNew.submitList(it)
            }

        }

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentTaskViewBinding = null
    }

    override fun onTaskClicked(task: Task, itemPosition:Int) {
      Log.d("TASKFRAGMENT", "${task.taskName} clicked at Position: $itemPosition.")
    }

    override fun onTaskCheckBoxClicked(task: Task, itemPosition: Int) {
        Log.d("TASKFRAGMENT", "${task.taskName} clicked at Position: $itemPosition. Task Done? = ${task.taskComplete}")

        taskFragmentViewModel.updateTask(task)

        Log.d("TASKFRAGMENT", "Task at position $itemPosition updated")

    }

}