package ru.yurii.testingworkshopapp.stub

import ru.yurii.testingworkshopapp.data.Project
import ru.yurii.testingworkshopapp.data.usecase.GetAllProjectsUseCase

class GetAllProjectsUseCaseStub : GetAllProjectsUseCase {
    var resultProvider: () -> List<Project> = { emptyList() }

    override suspend fun invoke(): List<Project> = resultProvider()
}
