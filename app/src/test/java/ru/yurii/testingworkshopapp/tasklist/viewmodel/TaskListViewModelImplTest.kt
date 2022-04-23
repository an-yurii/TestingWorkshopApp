package ru.yurii.testingworkshopapp.tasklist.viewmodel

import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import ru.yurii.testingworkshopapp.data.Project
import ru.yurii.testingworkshopapp.data.usecase.GetAllProjectsUseCase
import ru.yurii.testingworkshopapp.data.usecase.TasksForProjectUseCase
import ru.yurii.testingworkshopapp.stub.GetAllProjectsUseCaseStub
import ru.yurii.testingworkshopapp.stub.TasksForProjectUseCaseStub
import ru.yurii.testingworkshopapp.util.viewModelTestingRules

/**
 * @author y.anisimov
 */
class TaskListViewModelImplTest {

    @get:Rule
//    val viewModelRule = InstantTaskExecutorRule()
    val viewModelRule = viewModelTestingRules()

    @Test
    fun `load by default returns first project`() {
        val getAllProjectsUseCase = GetAllProjectsUseCaseStub().apply {
            resultProvider = {
                Single.just(
                    listOf(createProject(title = "Inbox1"), createProject(title = "Inbox2"))
                )
            }
        }
        val viewModel = createViewModel(getAllProjectsUseCase = getAllProjectsUseCase)

        viewModel.load()

        val state = viewModel.projectName.value as ProjectState.Loaded
        assertEquals("Inbox", state.project.title)
    }

    private fun createViewModel(
        getAllProjectsUseCase: GetAllProjectsUseCase = GetAllProjectsUseCaseStub(),
        tasksForProjectUseCase: TasksForProjectUseCase = TasksForProjectUseCaseStub()
    ): TaskListViewModel {
        return TaskListViewModelImpl(getAllProjectsUseCase, tasksForProjectUseCase)
    }

    private fun createProject(
        id: Long = 1L,
        title: String,
        order: Int = 0,
    ): Project = Project(id = id, title = title, order = order)
}
