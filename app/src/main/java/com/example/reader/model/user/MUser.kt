package com.example.reader.model.user

data class MUser(
	val id: String? = null,
	val userId: String,
	val displayName: String,
	val quote: String,
	val profession: String
) {
	constructor():this("","","","", "")
}