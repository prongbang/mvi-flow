package com.prongbang.mviflowex.domain

import com.prongbang.flow.FlowIntent

sealed class MainIntent : FlowIntent {
	object GetData : MainIntent()
	object GetBannerData : MainIntent()
}
