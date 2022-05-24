package com.prongbang.mviflowex.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.prongbang.flow.FlowViewModel
import com.prongbang.mviflowex.domain.GetMessageUseCase
import com.prongbang.mviflowex.presentation.state.MainEffect
import com.prongbang.mviflowex.presentation.state.MainIntent
import com.prongbang.mviflowex.presentation.state.MainState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
		private val getMessageUseCase: GetMessageUseCase
) : FlowViewModel<MainIntent, MainState, MainEffect>() {

	override fun onProcessIntent(intent: MainIntent) {
		when (intent) {
			MainIntent.GetData -> processGetData()
			MainIntent.GetBannerData -> processGetBannerData()
		}
	}

	private fun processGetBannerData() {
		Log.i("2", "GetBannerData")
	}

	private fun processGetData() = viewModelScope.launch(Dispatchers.IO) {
		Log.i("1", "GetData")
		val result = getMessageUseCase.execute(Unit)
		setState(MainState.ShowLoading)
		setState(MainState.Result(result))
		setEffect(MainEffect.Error("Hello Effect"))
		setState(MainState.HideLoading)
	}

}