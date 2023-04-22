package ru.yurii.testingworkshopapp.data.api

import retrofit2.http.GET

interface TodoistApiService {

    @GET("v2/projects")
    suspend fun projects(): List<ProjectResponse>

    @GET("v2/tasks")
    suspend fun tasks(): List<TaskResponse>
}
