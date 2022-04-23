package ru.yurii.testingworkshopapp.projectlist.viewmodel

import androidx.lifecycle.LiveData
import ru.yurii.testingworkshopapp.utils.ViewModelRx

internal abstract class ChooseProjectViewModel : ViewModelRx() {

    abstract val projectsStateOutput: LiveData<ChooseProjectViewState>

    abstract fun load()
}
