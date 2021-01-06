package com.prongbang.flow

/**
 * How to use:
 * class NamedUseCase : FlowUseCase<Unit, String> {
 *      override suspend fun execute(params: Unit): String {
 *          return withContext(Dispatchers.Default) {
 *              "Hello MVI Flow"
 *          }
 *      }
 * }
 */
interface FlowUseCase<Param, Result> {
	suspend fun execute(params: Param): Result
}