package ru.yurii.testingworkshopapp.di

class ComponentHolder<T> {

    @Volatile
    private var instance: T? = null
    private var componentProvider: () -> T = { error("${javaClass.simpleName} — component provider not found") }

    fun setComponentProvider(provider: () -> T) {
        componentProvider = provider
    }

    fun get(): T {
        return instance ?: synchronized(this) {
            instance ?: componentProvider().also { instance = it }
        }
    }

    fun reset() {
        instance = null
    }
}
