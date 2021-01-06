package com.prongbang.mviflowex

import com.prongbang.mviflowex.domain.GetMessageUseCase
import com.prongbang.mviflowex.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var mainModule = module {
	factory { GetMessageUseCase() }
	viewModel { MainViewModel(get()) }
}