package ru.yurii.testingworkshopapp.util

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class RxRule(
    val scheduler: Scheduler = Schedulers.trampoline()
) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler }
        RxAndroidPlugins.setMainThreadSchedulerHandler { scheduler }
        RxJavaPlugins.setComputationSchedulerHandler { scheduler }
        RxJavaPlugins.setIoSchedulerHandler { scheduler }
        RxJavaPlugins.setNewThreadSchedulerHandler { scheduler }
    }

    override fun finished(description: Description?) {
        super.finished(description)

        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

}
