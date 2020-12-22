package com.prongbang.mviflowex

import com.prongbang.flow.FlowIntent

sealed class MainIntent : FlowIntent {
	object GetData : MainIntent()
}
