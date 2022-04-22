package ru.yurii.testingworkshopapp.projectlist.viewmodel

import ru.yurii.testingworkshopapp.data.Project

/**
 * @author y.anisimov
 */
internal sealed class ChooseProjectViewState {
    data class Loaded(val projects: List<Project>) : ChooseProjectViewState()
    data class FailedToLoad(val exception: Throwable) : ChooseProjectViewState()
}
