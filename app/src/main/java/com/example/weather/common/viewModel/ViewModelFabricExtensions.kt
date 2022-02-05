package com.example.weather.common.viewModel

import androidx.fragment.app.Fragment
import com.example.weather.common.activity.getActivityComponent

fun Fragment.getViewModelFactory() = requireActivity().getActivityComponent().getViewModelsFactory()
