package ru.yurii.testingworkshopapp

import androidx.test.platform.app.InstrumentationRegistry
import ru.yurii.testingworkshopapp.di.AppComponent
import ru.yurii.testingworkshopapp.di.ComponentHolder

object ComponentProvider {
    fun appComponentHolder(): ComponentHolder<AppComponent> {
        return InstrumentationRegistry.getInstrumentation().targetContext.asApp().componentHolder
    }
}
