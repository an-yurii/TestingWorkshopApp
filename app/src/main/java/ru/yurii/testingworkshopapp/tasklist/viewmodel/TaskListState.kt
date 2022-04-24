package ru.yurii.testingworkshopapp.tasklist.viewmodel

import ru.yurii.testingworkshopapp.data.Task

internal sealed class TaskListState {
    data class Loaded(val tasks: List<Task>) : TaskListState()
    data class FailedToLoad(val exception: Throwable) : TaskListState()
}
