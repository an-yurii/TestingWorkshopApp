package ru.yurii.testingworkshopapp

import androidx.test.platform.app.InstrumentationRegistry
import ru.yurii.testingworkshopapp.di.AppComponent

object ComponentProvider {
    fun appComponent(): AppComponent {
        return InstrumentationRegistry.getInstrumentation().targetContext.appComponent()
    }
}
