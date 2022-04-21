package com.aroman.gitandroid.utils

import java.util.*

class ViewModelStore {
    private val storage: MutableMap<String, BaseViewModel> = WeakHashMap()

    fun saveViewModel(viewModel: BaseViewModel) {
        storage[viewModel.id] = viewModel
    }

    fun getViewModel(id: String): BaseViewModel? {
        return storage[id]
    }
}

interface BaseViewModel {
    val id: String
}