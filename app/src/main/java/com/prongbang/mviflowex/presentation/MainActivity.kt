package com.prongbang.mviflowex.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.prongbang.flow.FlowViewRenderer
import com.prongbang.mviflowex.R
import com.prongbang.mviflowex.databinding.ActivityMainBinding
import com.prongbang.mviflowex.domain.MainEffect
import com.prongbang.mviflowex.domain.MainIntent
import com.prongbang.mviflowex.domain.MainState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), FlowViewRenderer<MainState, MainEffect> {

	private val mainViewModel: MainViewModel by viewModel()

	private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
		initObserve()
		initLoad()
	}

	override fun initObserve() {
		mainViewModel.subscribe(::render, ::renderEffect)
	}

	private fun initLoad() {
		mainViewModel.process(MainIntent.GetData)
	}

	override fun render(state: MainState) {
		when (state) {
			is MainState.Result -> {
				binding.messageText.text = state.data
			}
		}
	}

	override fun renderEffect(effects: MainEffect) {
		when (effects) {
			is MainEffect.Error -> {
				binding.effectText.text = effects.data
			}
		}
	}
}