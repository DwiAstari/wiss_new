package com.dwiastari.wiss.model

data class SendNotificationResponse(
	val canonicalIds: Int? = null,
	val success: Int? = null,
	val failure: Int? = null,
	val results: List<ResultsItem?>? = null,
	val multicastId: Long? = null
)

data class ResultsItem(
	val error: String? = null
)

