package com.prongbang.mviflowex

import com.prongbang.flow.FlowState

sealed class MainState : FlowState {
	object Idle : MainState()
	data class Result(val data: String) : MainState()
}
