package com.prongbang.mviflowex.presentation

import androidx.lifecycle.viewModelScope
import com.prongbang.flow.FlowViewModel
import com.prongbang.mviflowex.domain.GetMessageUseCase
import com.prongbang.mviflowex.domain.MainEffect
import com.prongbang.mviflowex.domain.MainIntent
import com.prongbang.mviflowex.domain.MainState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
		private val getMessageUseCase: GetMessageUseCase
) : FlowViewModel<MainIntent, MainState, MainEffect>() {

	override fun initState() = MainState.Idle
	override fun initEffect() = MainEffect.Idle

	override fun onProcessIntent(intent: MainIntent) {
		when (intent) {
			MainIntent.GetData -> processGetData()
		}
	}

	private fun processGetData() = viewModelScope.launch(Dispatchers.IO) {
		val result = getMessageUseCase.execute(Unit)
		setState(MainState.Result(result))
		setEffect(MainEffect.Error("Hello Effect"))
	}

}