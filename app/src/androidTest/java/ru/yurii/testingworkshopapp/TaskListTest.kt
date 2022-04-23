package ru.yurii.testingworkshopapp

import androidx.test.core.app.ActivityScenario
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Test

/**
 * @author y.anisimov
 */
class TaskListTest : TestCase() {

    @Test
    fun firstProjectAndItsTasksAreShowByDefault() = run {
        ActivityScenario.launch(MainActivity::class.java)

        TaskListScreen.projectButton.hasText("Inbox")

        TaskListScreen.taskList.hasSize(12)
        TaskListScreen.taskList.childAt<TaskListScreen.TaskItem>(7) {
            title.hasText("TickTick for embedded calendars and timers.")
            bullet {
                isDisplayed()
                hasDrawableWithTint(R.drawable.marker, R.color.olive_green)
            }
        }
    }

    @Test
    fun tasksAreReplacedAfterProjectChoosing() {
        ActivityScenario.launch(MainActivity::class.java)

        TaskListScreen.projectButton.click()
        ChooseProjectScreen.list.childAt<ChooseProjectScreen.ProjectItem>(2) { click() }

        TaskListScreen.projectButton.hasText("Late")
        TaskListScreen.splash {
            isVisible()
            hasDrawable(R.drawable.ic_all_done)
        }
        TaskListScreen.taskList.isNotDisplayed()
    }
}
