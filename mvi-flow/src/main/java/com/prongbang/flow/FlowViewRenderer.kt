package com.prongbang.flow

/**
 * How to use:
 * class FeatureActivity : AppCompatActivity(), FlowViewRenderer<FeatureState, FeatureEffect> {
 *      override fun initObserve() {
 *
 *      }
 *
 *      override fun render(state: FeatureState) {
 *
 *      }
 *
 *      override fun renderEffect(effects: FeatureEffect) {
 *
 *      }
 * }
 */
interface FlowViewRenderer<S : FlowState, E : FlowEffect> {
	fun initObserve()
	fun render(state: S)
	fun renderEffect(effects: E)
}