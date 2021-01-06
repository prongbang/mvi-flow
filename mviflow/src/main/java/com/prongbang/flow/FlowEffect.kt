package com.prongbang.flow

/**
 * How to use:
 * sealed class FeatureEffect : FlowEffect {
 *      object Idle : MainEffect()
 *      data class Error(val data: String) : MainEffect()
 * }
 */
interface FlowEffect