package ru.yurii.testingworkshopapp.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.yurii.testingworkshopapp.databinding.TaskListFragmentBinding
import ru.yurii.testingworkshopapp.di.Component
import ru.yurii.testingworkshopapp.projectlist.ChooseProjectDialogFragment
import ru.yurii.testingworkshopapp.projectlist.PROJECT_ID_KEY
import ru.yurii.testingworkshopapp.projectlist.PROJECT_SELECTION_KEY
import ru.yurii.testingworkshopapp.projectlist.PROJECT_TITLE_KEY
import ru.yurii.testingworkshopapp.tasklist.viewmodel.ProjectState
import ru.yurii.testingworkshopapp.tasklist.viewmodel.TaskListViewModel
import ru.yurii.testingworkshopapp.tasklist.viewmodel.TaskListViewModelFactory
import ru.yurii.testingworkshopapp.tasklist.viewmodel.TaskListViewState
import ru.yurii.testingworkshopapp.utils.extensions.exhaustive

class TaskListFragment : Fragment() {

    private var _binding: TaskListFragmentBinding? = null
    private val binding: TaskListFragmentBinding get() = _binding!!

    private val viewModel: TaskListViewModel by viewModels {
        val component = requireActivity() as Component
        TaskListViewModelFactory(
            component.provideGetAllProjectsUseCase(),
            component.provideTasksForProjectUseCase()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TaskListFragmentBinding.inflate(inflater, container, false)

        setFragmentResultListener(PROJECT_SELECTION_KEY) { key, bundle ->
            val id = bundle.getLong(PROJECT_ID_KEY)!!
            val title = bundle.getString(PROJECT_TITLE_KEY, "Nop!")
            viewModel.loadTasksForProject(id, title)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.currentProject.setOnClickListener {
            val chooseProjectDialog = ChooseProjectDialogFragment()
            chooseProjectDialog.show(parentFragmentManager, "dialog")
        }

        with(binding.taskList) {
            layoutManager = LinearLayoutManager(context)
            val taskListAdapter = TaskListAdapter()
            adapter = taskListAdapter
            loadDataToAdapter(taskListAdapter)
        }

        viewModel.projectName.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ProjectState.Loaded -> binding.currentProject.text = state.project.title
                is ProjectState.FailedToLoad -> showError(state.exception)
            }
        }

        viewModel.load()
    }

    private fun showError(throwable: Throwable) {
        Toast.makeText(
            requireContext(),
            throwable.message!!,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun loadDataToAdapter(adapter: TaskListAdapter) {
        viewModel.tasksStateOutput.observe(viewLifecycleOwner) { state ->
            when (state) {
                is TaskListViewState.Loaded -> adapter.submitList(state.tasks)
                is TaskListViewState.FailedToLoad -> showError(state.exception)
            }.exhaustive
        }
    }

}
