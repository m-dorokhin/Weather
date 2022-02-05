package com.example.weather.common.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

class ViewModelsFactory(
    private val viewModelProviders: Map<Class<out ViewModel>, Provider<ViewModel>>,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        viewModelProviders[modelClass]?.get() as? T
            ?: throw ClassNotFoundException("Зависимость ${modelClass.simpleName} не зарегистрирована.")
}