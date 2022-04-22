package ru.yurii.testingworkshopapp.tasklist.viewmodel

import androidx.lifecycle.LiveData
import ru.yurii.testingworkshopapp.utils.ViewModelRx

/**
 * @author y.anisimov
 */
internal abstract class TaskListViewModel : ViewModelRx() {

    abstract val tasksStateOutput: LiveData<TaskListViewState>
    abstract val projectName: LiveData<ProjectState>

    abstract fun load()
    abstract fun loadTasksForProject(projectId: Long, projectTitle: String)
}
