package ru.yurii.testingworkshopapp.tasklist.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.yurii.testingworkshopapp.data.Project
import ru.yurii.testingworkshopapp.data.usecase.GetAllProjectsUseCase
import ru.yurii.testingworkshopapp.data.usecase.TasksForProjectUseCase
import ru.yurii.testingworkshopapp.utils.extensions.runCatchingNonCancellation

internal class TaskListViewModelImpl(
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val tasksForProjectUseCase: TasksForProjectUseCase
) : TaskListViewModel() {
    override val tasksStateOutput = MutableStateFlow<TaskListState>(TaskListState.Loading)
    override val projectName = MutableStateFlow<ProjectState>(ProjectState.Loading)

    override fun load() {
        loadFirstProject()
    }

    override fun loadTasksForProject(project: Project) {
        projectName.update { ProjectState.Loaded(project) }
        loadTasks(project.id)
    }

    private fun loadFirstProject() {
        viewModelScope.launch {
            runCatchingNonCancellation {
                val projects = getAllProjectsUseCase()
                loadTasksForProject(projects.first())
            }.onFailure { throwable ->
                Log.w("Error", throwable)
            }
        }
    }

    private fun loadTasks(projectId: Long) {
        viewModelScope.launch {
            runCatchingNonCancellation {
                val tasks = tasksForProjectUseCase(projectId)
                tasksStateOutput.emit(TaskListState.Loaded(tasks))
            }.onFailure { throwable ->
                Log.w("Error", throwable)
                tasksStateOutput.emit(TaskListState.FailedToLoad(throwable))
            }
        }
    }
}
