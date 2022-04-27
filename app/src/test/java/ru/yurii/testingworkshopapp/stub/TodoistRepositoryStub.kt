package ru.yurii.testingworkshopapp.stub

import io.reactivex.Single
import ru.yurii.testingworkshopapp.data.Project
import ru.yurii.testingworkshopapp.data.Task
import ru.yurii.testingworkshopapp.data.TodoistRepository

/**
 * @author y.anisimov
 */
class TodoistRepositoryStub : TodoistRepository {

    var tasksProvider: () -> Single<List<Task>> = { Single.just(emptyList()) }

    override fun projects(): Single<List<Project>>  = Single.just(emptyList())

    override fun tasks(): Single<List<Task>> = tasksProvider()
}
