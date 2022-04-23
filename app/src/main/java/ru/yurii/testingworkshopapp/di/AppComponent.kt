package ru.yurii.testingworkshopapp.di

import ru.yurii.testingworkshopapp.data.TodoistRepository
import ru.yurii.testingworkshopapp.data.TodoistRepositoryImpl
import ru.yurii.testingworkshopapp.data.usecase.GetAllProjectsUseCase
import ru.yurii.testingworkshopapp.data.usecase.GetAllProjectsUseCaseImpl
import ru.yurii.testingworkshopapp.data.usecase.TasksForProjectUseCase
import ru.yurii.testingworkshopapp.data.usecase.TasksForProjectUseCaseImpl

/**
 * @author y.anisimov
 */
interface AppComponent {
    fun apiUrlProvider(): ApiUrlProvider
    fun provideGetAllProjectsUseCase(): GetAllProjectsUseCase
    fun provideTasksForProjectUseCase(): TasksForProjectUseCase
}

class AppModule : AppComponent {
    private val apiUrlProvider: ApiUrlProvider by lazy { ApiUrlProvider.Impl() }
    private val networkModule by lazy { NetworkModule(apiUrlProvider()) }
    private val todoistRepository: TodoistRepository by lazy { TodoistRepositoryImpl(networkModule.api) }

    override fun apiUrlProvider(): ApiUrlProvider = apiUrlProvider

    override fun provideGetAllProjectsUseCase(): GetAllProjectsUseCase {
        return GetAllProjectsUseCaseImpl(todoistRepository)
    }

    override fun provideTasksForProjectUseCase(): TasksForProjectUseCase {
        return TasksForProjectUseCaseImpl(todoistRepository)
    }
}
