package com.prongbang.mviflowex.presentation.state

import com.prongbang.flow.FlowIntent

sealed class MainIntent : FlowIntent {
	object GetData : MainIntent()
	object GetBannerData : MainIntent()
}
