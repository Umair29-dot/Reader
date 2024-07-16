package com.example.reader.model

data class MUser(
	val id: String? = null,
	val userId: String,
	val displayName: String,
	val quote: String,
	val profession: String
) {
	constructor():this("","","","", "")
}