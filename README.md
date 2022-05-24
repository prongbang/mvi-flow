# mvi-flow

![](https://jitpack.io/v/prongbang/mvi-flow.svg)](https://jitpack.io/#prongbang/mvi-flow)

## Setup

- `build.gradle`

```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

- `app/build.gradle`

```gradle
implementation 'com.github.prongbang:mvi-flow:1.0.5'
```

## How to use

- MainIntent.kt

```kotlin
import com.prongbang.flow.FlowIntent

sealed class MainIntent : FlowIntent {
	object GetData : MainIntent()
}
```

- MainSate.kt

```kotlin
import com.prongbang.flow.FlowState

sealed class MainState : FlowState {
	data class Result(val data: String) : MainState()
}
```

- MainEffect.kt

```kotlin
import com.prongbang.flow.FlowEffect

sealed class MainEffect : FlowEffect {
	data class Error(val data: String) : MainEffect()
}
```

- GetMessageUseCase.kt

```kotlin
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
```

- MainViewModel.kt

```kotlin
import com.prongbang.flow.FlowViewModel
import com.prongbang.mviflowex.domain.MainEffect
import com.prongbang.mviflowex.domain.MainIntent
import com.prongbang.mviflowex.domain.MainState

class MainViewModel(
		private val getMessageUseCase: GetMessageUseCase
) : FlowViewModel<MainIntent, MainState, MainEffect>() {
    
	override fun onProcessIntent(intent: MainIntent) {
		when (intent) {
			MainIntent.GetData -> processGetData()
		}
	}

	private fun processGetData() = viewModelScope.launch(Dispatchers.IO) {
		val result = getMessageUseCase.execute(Unit)
		setState(MainState.Result(result))
		setEffect(MainEffect.Error("Hello Effect"))
	}
}
```

- MainActivity.kt

```kotlin
import com.prongbang.flow.FlowViewRenderer

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
```
