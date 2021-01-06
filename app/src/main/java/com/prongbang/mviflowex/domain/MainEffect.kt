package com.prongbang.mviflowex.domain

import com.prongbang.flow.FlowEffect

sealed class MainEffect : FlowEffect {
	object Idle : MainEffect()
	data class Error(val data: String) : MainEffect()
}
