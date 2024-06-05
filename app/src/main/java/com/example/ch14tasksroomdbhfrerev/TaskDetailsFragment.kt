package com.example.ch14tasksroomdbhfrerev

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ch14tasksroomdbhfrerev.databases.TaskDatabase
import com.example.ch14tasksroomdbhfrerev.databinding.FragmentTaskDetailsBinding
import com.example.ch14tasksroomdbhfrerev.model.Task
import com.example.ch14tasksroomdbhfrerev.viewmodels.TaskDetailsFragmentViewModel
import com.example.ch14tasksroomdbhfrerev.viewmodels.TaskDetailsFragmentViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

fun String.toEditable() = Editable.Factory.getInstance().newEditable(this)

/**
 * A simple [Fragment] subclass.
 * Use the [TaskDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding:FragmentTaskDetailsBinding? = null

    private val binding:FragmentTaskDetailsBinding
        get() = _binding!!

    lateinit var taskDetailsFragmentViewModel: TaskDetailsFragmentViewModel

    val args:TaskDetailsFragmentArgs by navArgs()

    private var task:Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_task_details, container, false)

        _binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)

        val view = binding.root

        Log.d("TASKDETAILSFRAGMENT", "Data Received from TasksFragment, ${args.task?.taskName ?: "Unknown Task Name"}")

        this.task = args.task

        val application = requireNotNull(this.activity).application

        val db = TaskDatabase.getInstance(application)

        val taskDao = db.userDao()

        val taskDetailsFragmentViewModelFactory = TaskDetailsFragmentViewModelFactory(taskDao)

        taskDetailsFragmentViewModel = ViewModelProvider(this, taskDetailsFragmentViewModelFactory).get(TaskDetailsFragmentViewModel::class.java)

        binding.updateTaskBtn.setOnClickListener {

           task?.let {
               it.taskName = binding.detailsTaskEdtTxt.text.toString()

               it.taskComplete = binding.detailsTaskDoneCb.isChecked

               taskDetailsFragmentViewModel.updateTask(it)

           }
        }

        binding.deleteTaskBtn.setOnClickListener {

            task?.let {
                taskDetailsFragmentViewModel.deleteTask(it)
            }

        }

        taskDetailsFragmentViewModel.navigateHome.observe(viewLifecycleOwner){
            it?.let {
                if (it){
                    val action = TaskDetailsFragmentDirections.actionTaskDetailsFragmentToTasksFragment()

                    this.findNavController().navigate(action)
                }
            }
        }


        return view
    }

    override fun onStart() {
        super.onStart()

        val taskNameEditable = (args.task?.taskName ?: "Unknown Task Name").toEditable()

        binding.detailsTaskEdtTxt.text = taskNameEditable

        binding.detailsTaskDoneCb.isChecked = args.task?.taskComplete ?: false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}