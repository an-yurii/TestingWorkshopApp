package ru.yurii.testingworkshopapp.data

import ru.yurii.testingworkshopapp.data.api.TaskResponse

/**
 * @author y.anisimov
 */
object TaskMapper {
    fun responseToTask(taskResponse: TaskResponse): Task {
        return Task(
            id = taskResponse.id,
            projectId = taskResponse.projectId,
            title = taskResponse.content,
            order = taskResponse.order,
            priority = taskResponse.priority,
            colorRes = Palette.getColorByPriority(taskResponse.priority)
        )
    }
}
