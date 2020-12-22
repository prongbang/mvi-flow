package com.prongbang.mviflowex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.prongbang.flow.FlowViewRenderer

class MainActivity : AppCompatActivity(), FlowViewRenderer<MainState, MainEffect> {

	private val mainViewModel: MainViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initObserve()
		initLoad()
	}

	private fun initObserve() {
		mainViewModel.subscribe(::render, ::renderEffect)
	}

	private fun initLoad() {
		mainViewModel.process(MainIntent.GetData)
	}

	override fun render(state: MainState) {
		when (state) {
			is MainState.Result -> {
				Log.i(MainActivity::class.java.simpleName, state.data)
			}
		}
	}

	override fun renderEffect(effects: MainEffect) {
		when (effects) {
			is MainEffect.Error -> {
				Log.i(MainActivity::class.java.simpleName, effects.data)
			}
		}
	}
}