package com.prongbang.mviflowex

import com.prongbang.flow.FlowViewModel

class MainViewModel : FlowViewModel<MainIntent, MainState, MainEffect>() {

	override fun initState() = MainState.Idle
	override fun initEffect() = MainEffect.Idle

	override fun onProcessIntent(intent: MainIntent) {
		when (intent) {
			MainIntent.GetData -> processGetData()
		}
	}

	private fun processGetData() {
		setState(MainState.Result("Hello MVI Flow"))

		setEffect(MainEffect.Error("Mock Error"))
	}

}