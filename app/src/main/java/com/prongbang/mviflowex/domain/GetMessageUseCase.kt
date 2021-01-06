package com.prongbang.mviflowex.domain

import com.prongbang.flow.FlowUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetMessageUseCase : FlowUseCase<Unit, String> {

	override suspend fun execute(params: Unit): String {
		return withContext(Dispatchers.Default) {
			"Hello MVI Flow"
		}
	}
}