package ru.yurii.testingworkshopapp.data.api

import io.reactivex.Single
import retrofit2.http.GET

interface TodoistApiService {

    @GET("v1/projects")
    fun projects(): Single<List<ProjectResponse>>

    @GET("v1/tasks")
    fun tasks(): Single<List<TaskResponse>>
}
