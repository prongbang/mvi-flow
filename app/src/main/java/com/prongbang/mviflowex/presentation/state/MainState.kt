package com.prongbang.mviflowex.presentation.state

import com.prongbang.flow.FlowState

sealed class MainState : FlowState {
	object ShowLoading : MainState()
	object HideLoading : MainState()
	data class Result(val data: String) : MainState()
}
