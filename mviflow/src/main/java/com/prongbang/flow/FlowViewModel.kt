package com.prongbang.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

/**
 * How to use:
 * class NamedViewModel : FlowViewModel<FeatureIntent, FeatureState, FeatureEffect>() {
 *      override fun onProcessIntent(intent: FeatureIntent) {
 *          when (intent) {
 *              FeatureIntent.GetData -> processGetData()
 *          }
 *      }
 * }
 */
abstract class FlowViewModel<Intent : FlowIntent, State : FlowState, Effect : FlowEffect>(
    private val displayCoroutineDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val processCoroutineDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : ViewModel() {

    private val state = MutableSharedFlow<State>()
    private val effect = MutableSharedFlow<Effect>()
    private val intents = Channel<Intent>(Channel.UNLIMITED)
    open val states: SharedFlow<State> get() = state
    open val effects: SharedFlow<Effect> get() = effect

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch(processCoroutineDispatcher) {
            intents.consumeAsFlow()
                .collect {
                    onProcessIntent(it)
                }
        }
    }

    fun process(intent: Intent) {
        viewModelScope.launch(processCoroutineDispatcher) {
            intents.send(intent)
        }
    }

    fun subscribe(onState: (State) -> Unit, onEffect: (Effect) -> Unit) {
        stateSubscribe(onState)
        effectSubscribe(onEffect)
    }

    protected open fun stateSubscribe(onState: (State) -> Unit) {
        viewModelScope.launch(displayCoroutineDispatcher) {
            states.collect { onState.invoke(it) }
        }
    }

    protected open fun effectSubscribe(onEffect: (Effect) -> Unit) {
        viewModelScope.launch(displayCoroutineDispatcher) {
            effects.collect { onEffect.invoke(it) }
        }
    }

    protected open fun setState(s: State) {
        viewModelScope.launch(processCoroutineDispatcher) {
            state.emit(s)
        }
    }

    protected open fun setEffect(e: Effect) {
        viewModelScope.launch(processCoroutineDispatcher) {
            effect.emit(e)
        }
    }

    protected abstract fun onProcessIntent(intent: Intent)
}