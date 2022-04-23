package ru.yurii.testingworkshopapp.util

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * @author y.anisimov
 */
class RxRule(
    val scheduler: Scheduler = Schedulers.trampoline()
) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        before()
    }

    override fun finished(description: Description?) {
        super.finished(description)
        after()
    }

    //    override fun apply(base: Statement?, description: Description?): Statement {
//        return object : Statement() {
//            override fun evaluate() {
//                before()
//                try {
//                    base?.evaluate()
//                }
//                finally {
//                    after()
//                }
//            }
//        }
//    }

    private fun before() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler }
        RxAndroidPlugins.setMainThreadSchedulerHandler { scheduler }
        RxJavaPlugins.setComputationSchedulerHandler { scheduler }
        RxJavaPlugins.setIoSchedulerHandler { scheduler }
        RxJavaPlugins.setNewThreadSchedulerHandler { scheduler }
    }

    private fun after() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }
}
