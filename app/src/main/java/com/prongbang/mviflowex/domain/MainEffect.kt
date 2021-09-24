package com.prongbang.mviflowex.domain

import com.prongbang.flow.FlowEffect

sealed class MainEffect : FlowEffect {
	data class Error(val data: String) : MainEffect()
}
