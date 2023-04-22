package ru.yurii.testingworkshopapp

import androidx.test.core.app.ActivityScenario
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.yurii.testingworkshopapp.screen.TaskListScreen
import ru.yurii.testingworkshopapp.util.MockRequestDispatcher

class TaskListIntegrationTest : TestCase() {

    @get:Rule
    val mockServer = MockWebServer()

    @Before
    fun setUp() {
        val appComponent = ComponentProvider.appComponentHolder().get()
        appComponent.apiUrlProvider().url = mockServer.url("/").toString()
    }

    @After
    fun tearDown() {
        ComponentProvider.appComponentHolder().reset()
    }

    @Test
    fun showPlaceholder_WhenTaskListIsEmpty() = run {
        mockServer.dispatcher = MockRequestDispatcher().apply {
            returnsForPath("/v2/projects") { setBody(loadFromAssets("projects_list.json")) }
            returnsForPath("/v2/tasks") { setBody("[]") }
        }

        ActivityScenario.launch(MainActivity::class.java)
        val taskListScreen = TaskListScreen()

        step("Отображается плейс холдер") {
            taskListScreen.placeholder {
                isVisible()
                hasDrawable(R.drawable.ic_all_done)
            }
            taskListScreen.taskList.isNotDisplayed()
        }
    }

}
