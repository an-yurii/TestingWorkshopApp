package ru.yurii.testingworkshopapp.data

import android.view.View

data class Task(
    val id: Long,
    val projectId: Long,
    val title: String,
    val order: Int,
    val priority: Int,
    val colorRes: Int,
) {
    val bulletVisibility: Int = if (priority > 1) View.VISIBLE else View.INVISIBLE
}

