package ru.yurii.testingworkshopapp.di

interface ApiUrlProvider {
    var url: String

    class Impl : ApiUrlProvider {
        override var url: String = "https://api.todoist.com/rest/"
    }
}
