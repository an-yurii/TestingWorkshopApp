package ru.yurii.testingworkshopapp.data

import io.reactivex.Single
import ru.yurii.testingworkshopapp.data.api.ProjectResponse
import ru.yurii.testingworkshopapp.data.api.TaskResponse
import ru.yurii.testingworkshopapp.data.api.TodoistApiService

/**
 * @author y.anisimov
 */
interface TodoistRepository {
    fun projects(): Single<List<Project>>
    fun tasks(): Single<List<Task>>
}

class TodoistRepositoryImpl(
    private val api: TodoistApiService
) : TodoistRepository {
    override fun projects(): Single<List<Project>> {
        return api.projects().map { items ->
            items.map { it.toProject() }
        }
    }

    override fun tasks(): Single<List<Task>> {
        return api.tasks().map { items ->
            items.map { it.toTask() }
        }
    }

    private fun TaskResponse.toTask() = TaskMapper.responseToTask(this)
    private fun ProjectResponse.toProject() = ProjectMapper.responseToProject(this)
}
