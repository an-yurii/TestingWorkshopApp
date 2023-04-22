package ru.yurii.testingworkshopapp.data.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.yurii.testingworkshopapp.data.Task
import ru.yurii.testingworkshopapp.data.TodoistRepository
import ru.yurii.testingworkshopapp.stub.TodoistRepositoryStub

@OptIn(ExperimentalCoroutinesApi::class)
class TasksForProjectUseCaseImplTest {

    @Test
    fun `usecase by default sorts items by order`() = runTest {
        val tasksFromRepository = listOf(
            createTask(id = 10L, order = 4, projectId = 1L),
            createTask(id = 20L, order = 2, projectId = 1L),
            createTask(id = 30L, order = 1, projectId = 1L),
            createTask(id = 40L, order = 3, projectId = 1L),
        )
        val repository = TodoistRepositoryStub().apply { tasksProvider = { tasksFromRepository } }
        val useCase = createTasksForProjectUseCase(repository = repository)

        val tasks = useCase(1L)

        assertEquals(listOf(30L, 20L, 40L, 10L), tasks.map { it.id })
    }

    @Test
    fun `usecase by default returns items for specified project`() = runTest {
        val tasksFromRepository = listOf(
            createTask(id = 10L, projectId = 1L),
            createTask(id = 20L, projectId = 2L),
            createTask(id = 30L, projectId = 1L),
            createTask(id = 40L, projectId = 2L),
        )
        val repository =
            TodoistRepositoryStub().apply { tasksProvider = { tasksFromRepository } }
        val useCase = createTasksForProjectUseCase(repository = repository)

        val tasks = useCase(2L)

        assertEquals(listOf(20L, 40L), tasks.map { it.id })
    }

    private fun createTasksForProjectUseCase(
        repository: TodoistRepository = TodoistRepositoryStub()
    ): TasksForProjectUseCase {
        return TasksForProjectUseCaseImpl(repository)
    }

    private fun createTask(
        id: Long = 0L,
        projectId: Long = 0L,
        title: String = "",
        order: Int = 0,
        priority: Int = 0,
        colorRes: Int = 0
    ): Task = Task(
        id = id,
        projectId = projectId,
        title = title,
        order = order,
        priority = priority,
        colorRes = colorRes
    )
}
