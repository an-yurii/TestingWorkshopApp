package ru.yurii.testingworkshopapp.data.usecase

import io.reactivex.Single
import ru.yurii.testingworkshopapp.data.Task
import ru.yurii.testingworkshopapp.data.TodoistRepository

interface TasksForProjectUseCase {
    operator fun invoke(projectId: Long): Single<List<Task>>
}

class TasksForProjectUseCaseImpl(
    private val repository: TodoistRepository
) : TasksForProjectUseCase {

    override fun invoke(projectId: Long): Single<List<Task>> {
        return repository.tasks().map { items ->
            items.filter { it.projectId == projectId }.sortedBy { it.order }
        }
    }
}
