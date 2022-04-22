package ru.yurii.testingworkshopapp.data

import io.reactivex.Single
import ru.yurii.testingworkshopapp.data.api.TodoistApiService

/**
 * @author y.anisimov
 */
interface TodoistRepository {
    fun projects(): Single<List<Project>>
    fun tasks(): Single<List<Task>>
}

class TodoistRepositoryIml(
    private val api: TodoistApiService
) : TodoistRepository {
    override fun projects(): Single<List<Project>> {
        return api.projects().map { items ->
            items.map { Project(id = it.id, title = it.name, order = it.order) }
        }
    }

    override fun tasks(): Single<List<Task>> {
        return api.tasks().map { items ->
            items.map {
                Task(
                    id = it.id,
                    projectId = it.projectId,
                    title = it.content,
                    order = it.order
                )
            }
        }
    }
}
