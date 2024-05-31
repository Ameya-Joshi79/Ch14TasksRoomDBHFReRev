package com.example.ch14tasksroomdbhfrerev

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.ch14tasksroomdbhfrerev.databases.TaskDatabase
import com.example.ch14tasksroomdbhfrerev.databinding.FragmentTasksBinding
import com.example.ch14tasksroomdbhfrerev.viewmodels.TaskFragmentViewModel
import com.example.ch14tasksroomdbhfrerev.viewmodels.TaskFragmentViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TasksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TasksFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _fragmentTaskViewBinding:FragmentTasksBinding? = null

    private val fragmentTaskViewBinding:FragmentTasksBinding
        get() = _fragmentTaskViewBinding!!

    private lateinit var taskFragmentViewModel:TaskFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_tasks, container, false)

       _fragmentTaskViewBinding = FragmentTasksBinding.inflate(inflater, container, false)

        val view = fragmentTaskViewBinding.root

        val application = requireNotNull(this.activity).application

        val db = TaskDatabase.getInstance(application)

        val taskDao = db.userDao()

        val taskFragmentViewModelFactory = TaskFragmentViewModelFactory(taskDao)

        taskFragmentViewModel = ViewModelProvider(this, taskFragmentViewModelFactory).get(TaskFragmentViewModel::class.java)

        fragmentTaskViewBinding.addTaskBtn.setOnClickListener {

            val taskName = fragmentTaskViewBinding.taskEdtTxt.text.toString()

            fragmentTaskViewBinding.taskEdtTxt.text = null

            taskFragmentViewModel.taskName = taskName

            taskFragmentViewModel.addTask()

        }//addTaskBtn.setOnClickListener

        taskFragmentViewModel.taskListString.observe(viewLifecycleOwner){
            if (it.isEmpty()){
                fragmentTaskViewBinding.taskListTv.text = getString(R.string.no_tasks_to_show_txt)
            }else{
                Log.d("TASKFRAGMENT", it)
                fragmentTaskViewBinding.taskListTv.text = it
            }
        }


        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentTaskViewBinding = null
    }

}