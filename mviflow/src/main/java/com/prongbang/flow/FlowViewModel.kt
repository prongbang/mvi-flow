package com.prongbang.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

/**
 * How to use:
 * class NamedViewModel : FlowViewModel<FeatureIntent, FeatureState, FeatureEffect>() {
 *      override fun initState() = FeatureState.Idle
 *      override fun initEffect() = FeatureEffect.Idle
 *
 *      override fun onProcessIntent(intent: FeatureIntent) {
 *          when (intent) {
 *              FeatureIntent.GetData -> processGetData()
 *          }
 *      }
 * }
 */
abstract class FlowViewModel<I : FlowIntent, S : FlowState, E : FlowEffect> : ViewModel() {

	private val state = MutableStateFlow(this.initState())
	private val effect = MutableStateFlow(this.initEffect())
	private val intents = Channel<I>(Channel.UNLIMITED)
	val states: StateFlow<S> get() = state
	val effects: StateFlow<E> get() = effect

	init {
		handleIntent()
	}

	private fun handleIntent() {
		viewModelScope.launch {
			intents.consumeAsFlow()
					.collect {
						onProcessIntent(it)
					}
		}
	}

	fun process(intent: I) {
		viewModelScope.launch(Dispatchers.Default) {
			intents.send(intent)
		}
	}

	fun subscribe(onState: (S) -> Unit, onEffect: (E) -> Unit) {
		stateSubscribe(onState)
		effectSubscribe(onEffect)
	}

	protected fun stateSubscribe(onState: (S) -> Unit) {
		viewModelScope.launch((Dispatchers.Default)) {
			states.collect { onState.invoke(it) }
		}
	}

	protected fun effectSubscribe(onEffect: (E) -> Unit) {
		viewModelScope.launch((Dispatchers.Default)) {
			effects.collect { onEffect.invoke(it) }
		}
	}

	protected fun setState(state: S) {
		this.state.value = state
	}

	protected fun setEffect(effect: E) {
		this.effect.value = effect
	}

	protected abstract fun initState(): S
	protected abstract fun initEffect(): E
	protected abstract fun onProcessIntent(intent: I)
}