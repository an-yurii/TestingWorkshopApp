package ru.yurii.testingworkshopapp.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectResponse(
    val id: Long,
    val name: String,
    val order: Int = 0,
    @SerialName("inbox_project")
    val inboxProject: Boolean = false
)
