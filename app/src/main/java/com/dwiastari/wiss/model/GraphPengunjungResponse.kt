package com.dwiastari.wiss.model

data class GraphPengunjungResponse(
	val payload: List<GraphPayload>? = null,
	val message: String? = null
)

data class GraphPayload(
	val total: String,
	val month: String
)

