package ru.yurii.testingworkshopapp.projectlist.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.yurii.testingworkshopapp.data.usecase.GetAllProjectsUseCase

internal class ChooseProjectViewModelImpl(
    private val getAllProjectsUseCase: GetAllProjectsUseCase
) : ChooseProjectViewModel() {
    override val projectsStateOutput = MutableLiveData<ChooseProjectViewState>()

    override fun load() {
        getAllProjectsUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { items -> projectsStateOutput.value = ChooseProjectViewState.Loaded(items) },
                { throwable ->
                    Log.w("Error", throwable)
                    projectsStateOutput.value = ChooseProjectViewState.FailedToLoad(throwable)
                }
            )
            .disposeOnFinish()
    }
}
