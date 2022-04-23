package ru.yurii.testingworkshopapp

import androidx.test.core.app.ActivityScenario
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Test
import ru.yurii.testingworkshopapp.screen.ChooseProjectScreen
import ru.yurii.testingworkshopapp.screen.TaskListScreen

/**
 * @author y.anisimov
 */
class TaskListTest : TestCase() {

    @Test
    fun showTasksForFirstProject_ByDefault() = run {
        ActivityScenario.launch(MainActivity::class.java)

        step("Отображается название проекта") {
            TaskListScreen.projectButton.hasText("Inbox")
        }
        step("Отображается список задач") {
            TaskListScreen.taskList.hasSize(12)
            TaskListScreen.taskList.childAt<TaskListScreen.TaskItem>(7) {
                title.hasText("TickTick for embedded calendars and timers.")
                bullet {
                    isDisplayed()
                    hasDrawableWithTint(R.drawable.marker, R.color.olive_green)
                }
            }
        }
    }

    @Test
    fun tasksAreReplaced_AfterProjectIsChosen() = run {
        ActivityScenario.launch(MainActivity::class.java)

        step("Выбор проекта") {
            TaskListScreen.projectButton.click()
            ChooseProjectScreen.list.childAt<ChooseProjectScreen.ProjectItem>(4) { click() }
        }
        step("Отображаются задачи выбранного проекта") {
            TaskListScreen.projectButton.hasText("Try Boards")
            TaskListScreen.taskList.hasSize(3)
        }
    }
}
