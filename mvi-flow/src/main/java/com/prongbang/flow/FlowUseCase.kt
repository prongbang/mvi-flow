package com.prongbang.flow

interface FlowUseCase<Param, Result> {
	suspend fun execute(params: Param): Result
}