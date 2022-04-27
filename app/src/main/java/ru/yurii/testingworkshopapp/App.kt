package ru.yurii.testingworkshopapp

import android.app.Application
import android.content.Context
import ru.yurii.testingworkshopapp.di.AppComponent
import ru.yurii.testingworkshopapp.di.AppModule
import ru.yurii.testingworkshopapp.di.ComponentHolder

class App : Application() {
    val componentHolder: ComponentHolder<AppComponent> = ComponentHolder<AppComponent>().apply {
        setComponentProvider { AppModule() }
    }
}

fun Context.appComponent(): AppComponent {
    return (this.applicationContext as App).componentHolder.get()
}

fun Context.asApp(): App = this.applicationContext as App
