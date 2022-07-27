package com.dwiastari.wiss.model

data class GraphKonselingResponse(
	val payload: List<PayloadKonseling>? = null,
	val message: String
)

data class PayloadKonseling(
	val jumlah: String,
	val month: String,
	val jenis: String
)

