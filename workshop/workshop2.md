# Workshop 2

В рамках воркшопа вы напишите пару тестов для асинхронного кода на примере `RxJava` и `ViewModel` + `LiveData`.

# Summary

В результате у вас должно получиться что-то похожее на:

```kotlin
package ru.yurii.testingworkshopapp.tasklist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import ru.yurii.testingworkshopapp.data.Project
import ru.yurii.testingworkshopapp.data.Task
import ru.yurii.testingworkshopapp.data.usecase.GetAllProjectsUseCase
import ru.yurii.testingworkshopapp.data.usecase.TasksForProjectUseCase
import ru.yurii.testingworkshopapp.stub.GetAllProjectsUseCaseStub
import ru.yurii.testingworkshopapp.stub.TasksForProjectUseCaseStub
import ru.yurii.testingworkshopapp.util.RxRule

class TaskListViewModelImplTest {

    @get:Rule
    val viewModelRule = InstantTaskExecutorRule()
    @get:Rule
    val rxRule = RxRule()

    @Test
    fun `load by default returns first project`() {
        val getAllProjectsUseCase = GetAllProjectsUseCaseStub().apply {
            resultProvider = {
                Single.just(
                    listOf(createProject(title = "First"), createProject(title = "Second"))
                )
            }
        }
        val viewModel = createViewModel(getAllProjectsUseCase = getAllProjectsUseCase)

        viewModel.load()

        val state = viewModel.projectName.value as ProjectState.Loaded
        assertEquals("First", state.project.title)
    }

    @Test
    fun `loadTasksForProject by default returns task list`() {
        val tasks = listOf(
            createTask(title = "First"),
            createTask(title = "Second"),
            createTask(title = "Third")
        )
        val useCase = TasksForProjectUseCaseStub().apply {
            resultProvider = { Single.just(tasks) }
        }
        val viewModel = createViewModel(tasksForProjectUseCase = useCase)

        viewModel.loadTasksForProject(createProject(id = 10L))

        val state = viewModel.tasksStateOutput.value as TaskListState.Loaded
        assertEquals(tasks, state.tasks)
    }

    private fun createViewModel(
        getAllProjectsUseCase: GetAllProjectsUseCase = GetAllProjectsUseCaseStub(),
        tasksForProjectUseCase: TasksForProjectUseCase = TasksForProjectUseCaseStub()
    ): TaskListViewModel {
        return TaskListViewModelImpl(getAllProjectsUseCase, tasksForProjectUseCase)
    }

    private fun createProject(
        id: Long = 1L,
        title: String = "",
        order: Int = 0,
    ): Project = Project(id = id, title = title, order = order)

    private fun createTask(
        id: Long = 0L,
        projectId: Long = 0L,
        title: String = "",
        order: Int = 0,
        priority: Int = 0,
        colorRes: Int = 0
    ): Task = Task(id = id, projectId = projectId, title = title, order = order, priority = priority, colorRes = colorRes)
}
```
