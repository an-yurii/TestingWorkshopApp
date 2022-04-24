package ru.yurii.testingworkshopapp.projectlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.yurii.testingworkshopapp.data.usecase.GetAllProjectsUseCase

@Suppress("UNCHECKED_CAST")
class ChooseProjectViewModelFactory(
    private val getAllProjectsUseCase: GetAllProjectsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChooseProjectViewModelImpl(getAllProjectsUseCase) as T
    }
}
