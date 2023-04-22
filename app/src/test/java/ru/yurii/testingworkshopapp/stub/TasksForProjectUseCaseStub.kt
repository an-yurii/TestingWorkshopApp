package ru.yurii.testingworkshopapp.stub

import ru.yurii.testingworkshopapp.data.Task
import ru.yurii.testingworkshopapp.data.usecase.TasksForProjectUseCase

class TasksForProjectUseCaseStub : TasksForProjectUseCase {
    var resultProvider: () -> List<Task> = { emptyList() }

    override suspend fun invoke(projectId: Long): List<Task> = resultProvider()
}
