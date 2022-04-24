package ru.yurii.testingworkshopapp

import androidx.test.core.app.ActivityScenario
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import okhttp3.mockwebserver.MockWebServer
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
        ComponentProvider.appComponent().apiUrlProvider().url = mockServer.url("/").toString()
    }

    @Test
    fun showPlaceholder_WhenTaskListIsEmpty() = run {
        mockServer.dispatcher = MockRequestDispatcher().apply {
            returnsForPath("/v1/projects") { setBody(loadFromAssets("projects_list.json")) }
            returnsForPath("/v1/tasks") { setBody("[]") }
        }

        ActivityScenario.launch(MainActivity::class.java)

        step("Отображается название проекта") {
            TaskListScreen.projectButton.hasText("Inbox (local)")
        }
        step("Отображается сплеш") {
            TaskListScreen.placeholder {
                isVisible()
                hasDrawable(R.drawable.ic_all_done)
            }
            TaskListScreen.taskList.isNotDisplayed()
        }
    }

}
