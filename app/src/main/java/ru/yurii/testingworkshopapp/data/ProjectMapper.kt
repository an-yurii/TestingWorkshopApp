package ru.yurii.testingworkshopapp.data

import ru.yurii.testingworkshopapp.data.api.ProjectResponse

object ProjectMapper {
    fun responseToProject(projectResponse: ProjectResponse): Project {
        return Project(
            id = projectResponse.id,
            title = projectResponse.name,
            order = projectResponse.order
        )
    }
}
