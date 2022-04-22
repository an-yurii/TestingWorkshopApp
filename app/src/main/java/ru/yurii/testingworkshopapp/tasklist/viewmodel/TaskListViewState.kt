package ru.yurii.testingworkshopapp.tasklist.viewmodel

import ru.yurii.testingworkshopapp.data.Task

/**
 * @author y.anisimov
 */
internal sealed class TaskListViewState {
    data class Loaded(val tasks: List<Task>) : TaskListViewState()
    data class FailedToLoad(val exception: Throwable) : TaskListViewState()
}
