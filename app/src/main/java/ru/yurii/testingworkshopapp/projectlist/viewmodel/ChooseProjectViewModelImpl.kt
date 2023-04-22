package ru.yurii.testingworkshopapp.projectlist.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.yurii.testingworkshopapp.data.usecase.GetAllProjectsUseCase
import ru.yurii.testingworkshopapp.utils.extensions.runCatchingNonCancellation

internal class ChooseProjectViewModelImpl(
    private val getAllProjectsUseCase: GetAllProjectsUseCase
) : ChooseProjectViewModel() {
    override val projectsStateOutput = MutableStateFlow<ChooseProjectViewState>(ChooseProjectViewState.Loading)

    override fun load() {
        viewModelScope.launch {
            runCatchingNonCancellation {
                projectsStateOutput.value = ChooseProjectViewState.Loaded(getAllProjectsUseCase())
            }.onFailure { throwable ->
                Log.w("Error", throwable)
                projectsStateOutput.value = ChooseProjectViewState.FailedToLoad(throwable)
            }
        }
    }
}
