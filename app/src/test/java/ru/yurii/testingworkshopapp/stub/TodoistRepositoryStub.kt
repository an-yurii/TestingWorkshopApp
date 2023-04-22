package ru.yurii.testingworkshopapp.stub

import ru.yurii.testingworkshopapp.data.Project
import ru.yurii.testingworkshopapp.data.Task
import ru.yurii.testingworkshopapp.data.TodoistRepository

/**
 * @author y.anisimov
 */
class TodoistRepositoryStub : TodoistRepository {

    var tasksProvider: () -> List<Task> = { emptyList() }

    override suspend fun projects(): List<Project>  = emptyList()

    override suspend fun tasks(): List<Task> = tasksProvider()
}
