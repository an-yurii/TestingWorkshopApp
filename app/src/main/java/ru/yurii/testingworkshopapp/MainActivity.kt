package ru.yurii.testingworkshopapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.yurii.testingworkshopapp.data.TodoistRepository
import ru.yurii.testingworkshopapp.data.TodoistRepositoryIml
import ru.yurii.testingworkshopapp.data.usecase.GetAllProjectsUseCase
import ru.yurii.testingworkshopapp.data.usecase.GetAllProjectsUseCaseImpl
import ru.yurii.testingworkshopapp.data.usecase.TasksForProjectUseCase
import ru.yurii.testingworkshopapp.data.usecase.TasksForProjectUseCaseImpl
import ru.yurii.testingworkshopapp.di.Component
import ru.yurii.testingworkshopapp.di.NetworkModule
import ru.yurii.testingworkshopapp.tasklist.TaskListFragment

class MainActivity : AppCompatActivity(), Component {

    private val networkModule by lazy { NetworkModule() }
    private val todoistRepository: TodoistRepository by lazy { TodoistRepositoryIml(networkModule.api) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            routeToTaskList()
        }
    }

    private fun routeToTaskList() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                TaskListFragment(),
                TaskListFragment::class.simpleName
            )
            .commit()
    }

    override fun provideTodoistRepository(): TodoistRepository = todoistRepository
    override fun provideGetAllProjectsUseCase(): GetAllProjectsUseCase {
        return GetAllProjectsUseCaseImpl(todoistRepository)
    }

    override fun provideTasksForProjectUseCase(): TasksForProjectUseCase {
        return TasksForProjectUseCaseImpl(todoistRepository)
    }
}
