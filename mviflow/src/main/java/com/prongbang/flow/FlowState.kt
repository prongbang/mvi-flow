package com.prongbang.flow

/**
 * How to use:
 * sealed class FeatureState : FlowState {
 *      object Idle : FeatureState()
 *      data class Result(val data: String) : FeatureState()
 * }
 */
interface FlowState