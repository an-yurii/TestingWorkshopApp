package ru.yurii.testingworkshopapp

import android.app.Application
import android.content.Context
import ru.yurii.testingworkshopapp.di.AppComponent
import ru.yurii.testingworkshopapp.di.AppModule

/**
 * @author y.anisimov
 */
class App : Application() {
    val component: AppComponent by lazy { AppModule() }
}

fun Context.appComponent(): AppComponent {
    return (this.applicationContext as App).component
}
