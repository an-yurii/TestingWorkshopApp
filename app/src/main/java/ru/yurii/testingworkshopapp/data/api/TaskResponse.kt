package ru.yurii.testingworkshopapp.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskResponse(
    val id: Long,
    @SerialName("project_id")
    val projectId: Long,
    val content: String,
    val order: Int,
    val priority: Int
)
