package ru.yurii.testingworkshopapp.data.usecase

import ru.yurii.testingworkshopapp.data.Project
import ru.yurii.testingworkshopapp.data.TodoistRepository

interface GetAllProjectsUseCase {
    suspend operator fun invoke(): List<Project>
}

class GetAllProjectsUseCaseImpl(
    private val repository: TodoistRepository
) : GetAllProjectsUseCase {
    override suspend fun invoke(): List<Project> = repository.projects().sortedBy { it.order }
}
