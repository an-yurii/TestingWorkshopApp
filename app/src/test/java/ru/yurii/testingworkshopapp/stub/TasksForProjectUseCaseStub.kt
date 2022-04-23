package ru.yurii.testingworkshopapp.stub

import io.reactivex.Single
import ru.yurii.testingworkshopapp.data.Task
import ru.yurii.testingworkshopapp.data.usecase.TasksForProjectUseCase

class TasksForProjectUseCaseStub : TasksForProjectUseCase {
    var resultProvider: () -> Single<List<Task>> = { Single.just(emptyList()) }

    override fun invoke(projectId: Long): Single<List<Task>> = resultProvider()
}
