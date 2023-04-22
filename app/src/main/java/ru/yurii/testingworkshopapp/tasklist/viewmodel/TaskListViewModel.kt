package ru.yurii.testingworkshopapp.tasklist.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import ru.yurii.testingworkshopapp.data.Project

internal abstract class TaskListViewModel : ViewModel() {

    abstract val tasksStateOutput: StateFlow<TaskListState>
    abstract val projectName: StateFlow<ProjectState>

    abstract fun load()
    abstract fun loadTasksForProject(project: Project)
}
