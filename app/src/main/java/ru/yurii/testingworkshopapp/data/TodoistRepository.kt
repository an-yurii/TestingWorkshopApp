package ru.yurii.testingworkshopapp.data

import ru.yurii.testingworkshopapp.data.api.ProjectResponse
import ru.yurii.testingworkshopapp.data.api.TaskResponse
import ru.yurii.testingworkshopapp.data.api.TodoistApiService

interface TodoistRepository {
    suspend fun projects(): List<Project>
    suspend fun tasks(): List<Task>
}

class TodoistRepositoryImpl(
    private val api: TodoistApiService
) : TodoistRepository {
    override suspend fun projects(): List<Project> = api.projects().map { it.toProject() }

    override suspend fun tasks(): List<Task> = api.tasks().map { it.toTask() }

    private fun TaskResponse.toTask() = TaskMapper.responseToTask(this)
    private fun ProjectResponse.toProject() = ProjectMapper.responseToProject(this)
}
