package com.prongbang.flow

interface FlowViewRenderer<S : FlowState, E : FlowEffect> {
	fun render(state: S)
	fun renderEffect(effects: E)
}