package ru.yurii.testingworkshopapp.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.yurii.testingworkshopapp.appComponent
import ru.yurii.testingworkshopapp.data.Project
import ru.yurii.testingworkshopapp.databinding.TaskListFragmentBinding
import ru.yurii.testingworkshopapp.projectlist.ChooseProjectDialogFragment
import ru.yurii.testingworkshopapp.projectlist.PROJECT_SELECTION_KEY
import ru.yurii.testingworkshopapp.tasklist.viewmodel.ProjectState
import ru.yurii.testingworkshopapp.tasklist.viewmodel.TaskListState
import ru.yurii.testingworkshopapp.tasklist.viewmodel.TaskListViewModel
import ru.yurii.testingworkshopapp.tasklist.viewmodel.TaskListViewModelFactory
import ru.yurii.testingworkshopapp.utils.extensions.exhaustive

class TaskListFragment : Fragment() {

    private var _binding: TaskListFragmentBinding? = null
    private val binding: TaskListFragmentBinding get() = _binding!!

    private val viewModel: TaskListViewModel by viewModels {
        val component = requireContext().appComponent()
        TaskListViewModelFactory(
            component.provideGetAllProjectsUseCase(),
            component.provideTasksForProjectUseCase()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TaskListFragmentBinding.inflate(inflater, container, false)

        setFragmentResultListener(PROJECT_SELECTION_KEY) { key, bundle ->
            val project = bundle.getParcelable<Project>(PROJECT_SELECTION_KEY)!!
            viewModel.loadTasksForProject(project)
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

        viewModel.projectName
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                when (state) {
                    is ProjectState.Loaded -> {
                        binding.currentProject.visibility = View.VISIBLE
                        binding.currentProject.text = state.project.title
                    }
                    is ProjectState.FailedToLoad -> showError(state.exception)
                    ProjectState.Loading -> Unit
                }
            }
            .launchIn(lifecycleScope)
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
        viewModel.tasksStateOutput
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                when (state) {
                    is TaskListState.Loaded -> {
                        adapter.submitList(state.tasks)
                        binding.taskList.visibility = if (state.tasks.isEmpty()) View.GONE else View.VISIBLE
                        binding.placeholder.visibility = if (state.tasks.isEmpty()) View.VISIBLE else View.GONE
                    }
                    is TaskListState.FailedToLoad -> showError(state.exception)
                    TaskListState.Loading -> Unit
                }.exhaustive
            }
            .launchIn(lifecycleScope)
    }

}
