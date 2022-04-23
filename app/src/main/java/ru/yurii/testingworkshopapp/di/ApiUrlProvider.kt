package ru.yurii.testingworkshopapp.di

/**
 * @author y.anisimov
 */
interface ApiUrlProvider {
    var url: String

    class Impl : ApiUrlProvider {
        override var url: String = "https://api.todoist.com/rest/"
    }
}
