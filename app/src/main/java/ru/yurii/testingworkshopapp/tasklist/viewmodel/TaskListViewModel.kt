package ru.yurii.testingworkshopapp.tasklist.viewmodel

import androidx.lifecycle.LiveData
import ru.yurii.testingworkshopapp.data.Project
import ru.yurii.testingworkshopapp.utils.ViewModelRx

internal abstract class TaskListViewModel : ViewModelRx() {

    abstract val tasksStateOutput: LiveData<TaskListState>
    abstract val projectName: LiveData<ProjectState>

    abstract fun load()
    abstract fun loadTasksForProject(project: Project)
}
