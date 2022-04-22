package ru.yurii.testingworkshopapp.utils

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author y.anisimov
 */
abstract class ViewModelRx : ViewModel() {
    private val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    protected fun Disposable.disposeOnFinish(): Disposable {
        disposables.add(this)
        return this
    }
}
