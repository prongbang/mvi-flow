package com.prongbang.mviflowex.presentation.state

import com.prongbang.flow.FlowEffect

sealed class MainEffect : FlowEffect {
	data class Error(val data: String) : MainEffect()
}
