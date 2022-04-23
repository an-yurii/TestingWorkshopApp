package ru.yurii.testingworkshopapp.util

import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.rules.RuleChain


fun viewModelTestingRules(): RuleChain =
    RuleChain
        .outerRule(InstantTaskExecutorRule())
        .around(RxRule())
        .around(MainCoroutineDispatcherRule())
