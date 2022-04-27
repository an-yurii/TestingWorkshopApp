package ru.yurii.testingworkshopapp.data.usecase

import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import ru.yurii.testingworkshopapp.data.Task
import ru.yurii.testingworkshopapp.data.TodoistRepository
import ru.yurii.testingworkshopapp.stub.TodoistRepositoryStub
import ru.yurii.testingworkshopapp.util.RxRule

class TasksForProjectUseCaseImplTest {

    @get:Rule
    val rxRule = RxRule()

    @Test
    fun `usecase by default sorts items by order`() {
        val tasksFromRepository = listOf(
            createTask(id = 10L, order = 4, projectId = 1L),
            createTask(id = 20L, order = 2, projectId = 1L),
            createTask(id = 30L, order = 1, projectId = 1L),
            createTask(id = 40L, order = 3, projectId = 1L),
        )
        val repository = TodoistRepositoryStub().apply { tasksProvider = { Single.just(tasksFromRepository) } }
        val useCase = createTasksForProjectUseCase(repository = repository)

        val testObserver = useCase(1L).test()

        testObserver.assertValue { tasks ->
            assertEquals(listOf(30L, 20L, 40L, 10L), tasks.map { it.id })
            true
        }
    }

    @Test
    fun `usecase by default returns items for specified project`() {
        val tasksFromRepository = listOf(
            createTask(id = 10L, projectId = 1L),
            createTask(id = 20L, projectId = 2L),
            createTask(id = 30L, projectId = 1L),
            createTask(id = 40L, projectId = 2L),
        )
        val repository = TodoistRepositoryStub().apply { tasksProvider = { Single.just(tasksFromRepository) } }
        val useCase = createTasksForProjectUseCase(repository = repository)

        val testObserver = useCase(2L).test()

        testObserver.assertValue { tasks ->
            assertEquals(listOf(20L, 40L), tasks.map { it.id })
            true
        }
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
