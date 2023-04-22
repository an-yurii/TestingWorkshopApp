package ru.yurii.testingworkshopapp.projectlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.yurii.testingworkshopapp.appComponent
import ru.yurii.testingworkshopapp.databinding.ChooseProjectDialogFragmentBinding
import ru.yurii.testingworkshopapp.projectlist.viewmodel.ChooseProjectViewModel
import ru.yurii.testingworkshopapp.projectlist.viewmodel.ChooseProjectViewModelFactory
import ru.yurii.testingworkshopapp.projectlist.viewmodel.ChooseProjectViewState
import ru.yurii.testingworkshopapp.utils.extensions.exhaustive

const val PROJECT_SELECTION_KEY = "PROJECT_SELECTION_KEY"

class ChooseProjectDialogFragment : BottomSheetDialogFragment() {

    private var _binding: ChooseProjectDialogFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChooseProjectViewModel by viewModels {
        val component = requireContext().appComponent()
        ChooseProjectViewModelFactory(component.provideGetAllProjectsUseCase())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChooseProjectDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
             val projectListAdapter = ProjectListAdapter { project ->
                setFragmentResult(PROJECT_SELECTION_KEY, bundleOf(PROJECT_SELECTION_KEY to project))
                dismiss()
            }
            adapter = projectListAdapter
            loadDataToAdapter(projectListAdapter)
        }

        viewModel.load()
    }

    private fun loadDataToAdapter(adapter: ProjectListAdapter) {
        viewModel.projectsStateOutput
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                when (state) {
                    ChooseProjectViewState.Loading -> Unit
                    is ChooseProjectViewState.Loaded -> adapter.submitList(state.projects)
                    is ChooseProjectViewState.FailedToLoad -> Toast.makeText(
                        requireContext(),
                        state.exception.message!!,
                        Toast.LENGTH_SHORT
                    ).show()
                }.exhaustive
            }
            .launchIn(lifecycleScope)
    }
}
