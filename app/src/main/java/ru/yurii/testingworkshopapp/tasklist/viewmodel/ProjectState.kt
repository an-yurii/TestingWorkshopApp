package ru.yurii.testingworkshopapp.tasklist.viewmodel

import ru.yurii.testingworkshopapp.data.Project

/**
 * @author y.anisimov
 */
internal sealed class ProjectState {
    data class Loaded(val project: Project) : ProjectState()
    data class FailedToLoad(val exception: Throwable) : ProjectState()
}
