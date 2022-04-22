package ru.yurii.testingworkshopapp.data

/**
 * @author y.anisimov
 */
data class Task(
    val id: Long,
    val projectId: Long,
    val title: String,
    val order: Int
)
