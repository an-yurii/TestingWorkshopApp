package ru.yurii.testingworkshopapp.projectlist.viewmodel

import ru.yurii.testingworkshopapp.data.Project

internal sealed class ChooseProjectViewState {
    object Loading : ChooseProjectViewState()
    data class Loaded(val projects: List<Project>) : ChooseProjectViewState()
    data class FailedToLoad(val exception: Throwable) : ChooseProjectViewState()
}
