package com.example.reader.model.firebase

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName

data class MBookFirebase(
	@Exclude //Don't include id while saving data into firebase
	var documentId: String? = null,
	var title: String? = null,
	var notes: String? = null,
	var photo: String? = null,
	var authors: String? = null,
	@get:PropertyName("published_date") //get field, with this name from the firebase
	@set:PropertyName("published_date") //set field, with this name in the firebase
	var publishedDate: String? = null,
	@get:PropertyName("page_count")
	@set:PropertyName("page_count")
	var pageCount: Int? = null,
	var description: String? = null,
	var categories: String? = null,
	var rating: Double? = null,
	@get:PropertyName("start_reading")
	@set:PropertyName("start_reading")
	var startReading: Timestamp? = null,
	@get:PropertyName("finish_reading")
	@set:PropertyName("finish_reading")
	var finishReading: Timestamp? = null,
	var userId: String? = null,
	@get:PropertyName("google_book_id")
	@set:PropertyName("google_book_id")
	var googleBookId: String? = null
)
