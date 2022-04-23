package ru.yurii.testingworkshopapp.stub

import io.reactivex.Single
import ru.yurii.testingworkshopapp.data.Project
import ru.yurii.testingworkshopapp.data.usecase.GetAllProjectsUseCase

/**
 * @author y.anisimov
 */
class GetAllProjectsUseCaseStub : GetAllProjectsUseCase {
    var resultProvider: () -> Single<List<Project>> = { Single.just(emptyList()) }

    override fun invoke(): Single<List<Project>> = resultProvider()
}
