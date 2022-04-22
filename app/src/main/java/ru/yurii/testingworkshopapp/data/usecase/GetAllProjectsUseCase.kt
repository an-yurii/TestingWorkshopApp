package ru.yurii.testingworkshopapp.data.usecase

import io.reactivex.Single
import ru.yurii.testingworkshopapp.data.Project
import ru.yurii.testingworkshopapp.data.TodoistRepository

/**
 * @author y.anisimov
 */
interface GetAllProjectsUseCase {
    operator fun invoke(): Single<List<Project>>
}

class GetAllProjectsUseCaseImpl(
    private val repository: TodoistRepository
) : GetAllProjectsUseCase {
    override fun invoke(): Single<List<Project>> {
        return repository.projects().map { items ->
            items.sortedBy { it.order }
        }
    }
}
