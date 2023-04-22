package ru.yurii.testingworkshopapp.tasklist.viewmodel

import ru.yurii.testingworkshopapp.data.Project

internal sealed class ProjectState {
    object Loading : ProjectState()
    data class Loaded(val project: Project) : ProjectState()
    data class FailedToLoad(val exception: Throwable) : ProjectState()
}
