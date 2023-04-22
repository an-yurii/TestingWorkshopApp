package ru.yurii.testingworkshopapp.data.usecase

import ru.yurii.testingworkshopapp.data.Task
import ru.yurii.testingworkshopapp.data.TodoistRepository

interface TasksForProjectUseCase {
    suspend operator fun invoke(projectId: Long): List<Task>
}

class TasksForProjectUseCaseImpl(
    private val repository: TodoistRepository
) : TasksForProjectUseCase {

    override suspend fun invoke(projectId: Long): List<Task> {
        return repository.tasks().filter { it.projectId == projectId }.sortedBy { it.order }
    }
}
