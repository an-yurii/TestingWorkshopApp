package ru.yurii.testingworkshopapp.tasklist.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import ru.yurii.testingworkshopapp.data.Project
import ru.yurii.testingworkshopapp.data.usecase.GetAllProjectsUseCase
import ru.yurii.testingworkshopapp.data.usecase.TasksForProjectUseCase

/**
 * @author y.anisimov
 */
internal class TaskListViewModelImpl(
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val tasksForProjectUseCase: TasksForProjectUseCase
) : TaskListViewModel() {
    override val tasksStateOutput = MutableLiveData<TaskListState>()
    override val projectName = MutableLiveData<ProjectState>()

    override fun load() {
        loadFirstProject()
    }

    override fun loadTasksForProject(project: Project) {
        projectName.postValue(ProjectState.Loaded(project))
        loadTasks(project.id)
    }

    private fun loadFirstProject() {
        getAllProjectsUseCase()
            .subscribeOn(Schedulers.io())
            .subscribe(
                { projects ->
                    val firstProject = projects.first()
                    projectName.postValue(ProjectState.Loaded(firstProject))
                    loadTasks(firstProject.id)
                },
                { throwable -> Log.w("Error", throwable) }
            )
            .disposeOnFinish()
    }

    private fun loadTasks(projectId: Long) {
        tasksForProjectUseCase(projectId)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { items ->
                    tasksStateOutput.postValue(TaskListState.Loaded(items))
                },
                { throwable ->
                    Log.w("Error", throwable)
                    tasksStateOutput.postValue(TaskListState.FailedToLoad(throwable))
                }
            )
            .disposeOnFinish()
    }
}
