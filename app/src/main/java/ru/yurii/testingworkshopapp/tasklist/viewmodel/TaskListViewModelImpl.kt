package ru.yurii.testingworkshopapp.tasklist.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.yurii.testingworkshopapp.data.usecase.GetAllProjectsUseCase
import ru.yurii.testingworkshopapp.data.usecase.TasksForProjectUseCase

/**
 * @author y.anisimov
 */
internal class TaskListViewModelImpl(
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val tasksForProjectUseCase: TasksForProjectUseCase
) : TaskListViewModel() {
    override val tasksStateOutput = MutableLiveData<TaskListViewState>()
    override val projectName = MutableLiveData<ProjectState>()

    override fun load() {
        loadFirstProject()
    }

    override fun loadTasksForProject(projectId: Long, projectTitle: String) {
        getAllProjectsUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { projects ->
                    val project = projects.find { it.id == projectId }
                    if (project != null) {
                        projectName.value = ProjectState.Loaded(project)
                        loadTasks(project.id)
                    } else {
                        projectName.value = ProjectState.FailedToLoad(
                            IllegalStateException("Project is not found by id $projectId")
                        )
                    }
                },
                { throwable ->
                    projectName.value = ProjectState.FailedToLoad(throwable)
                    Log.w("Error", throwable)
                }
            )
            .disposeOnFinish()
    }

    private fun loadFirstProject() {
        getAllProjectsUseCase()
            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
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
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { items -> tasksStateOutput.value = TaskListViewState.Loaded(items) },
                { throwable ->
                    Log.w("Error", throwable)
                    tasksStateOutput.value = TaskListViewState.FailedToLoad(throwable)
                }
            )
            .disposeOnFinish()
    }
}
