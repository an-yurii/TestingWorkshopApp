package ru.yurii.testingworkshopapp.di

import ru.yurii.testingworkshopapp.data.TodoistRepository
import ru.yurii.testingworkshopapp.data.usecase.GetAllProjectsUseCase
import ru.yurii.testingworkshopapp.data.usecase.TasksForProjectUseCase

/**
 * @author y.anisimov
 */
interface Component {
    fun provideTodoistRepository(): TodoistRepository
    fun provideGetAllProjectsUseCase(): GetAllProjectsUseCase
    fun provideTasksForProjectUseCase(): TasksForProjectUseCase
}
