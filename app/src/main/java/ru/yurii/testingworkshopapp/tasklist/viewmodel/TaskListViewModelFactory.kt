package ru.yurii.testingworkshopapp.tasklist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.yurii.testingworkshopapp.data.usecase.GetAllProjectsUseCase
import ru.yurii.testingworkshopapp.data.usecase.TasksForProjectUseCase

/**
 * @author y.anisimov
 */
@Suppress("UNCHECKED_CAST")
class TaskListViewModelFactory(
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val tasksForProjectUseCase: TasksForProjectUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskListViewModelImpl(getAllProjectsUseCase, tasksForProjectUseCase) as T
    }
}
