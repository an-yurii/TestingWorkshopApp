package ru.yurii.testingworkshopapp

import androidx.test.core.app.ActivityScenario
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import ru.yurii.testingworkshopapp.screen.TaskListScreen
import ru.yurii.testingworkshopapp.util.MockRequestDispatcher

/**
 * @author y.anisimov
 */
class TaskListIntegrationTest {

    @get:Rule
    val mockServer = MockWebServer()

    @Test
    fun showSplash_WhenTaskListIsEmpty() = run {
        ComponentProvider.appComponent().apiUrlProvider().url = mockServer.url("/").toString()
        mockServer.dispatcher = MockRequestDispatcher().apply {
            returnsForPath("/v1/projects") { setBody(loadFromAssets("projects_list.json")) }
            returnsForPath("/v1/tasks") { setBody("[]") }
        }

        ActivityScenario.launch(MainActivity::class.java)

        TaskListScreen.projectButton.hasText("Inbox (local)")

        TaskListScreen.splash {
            isVisible()
            hasDrawable(R.drawable.ic_all_done)
        }
        TaskListScreen.taskList.isNotDisplayed()
    }

}
