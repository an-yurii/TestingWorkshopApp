package ru.yurii.testingworkshopapp.projectlist.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

internal abstract class ChooseProjectViewModel : ViewModel() {

    abstract val projectsStateOutput: StateFlow<ChooseProjectViewState>

    abstract fun load()
}
