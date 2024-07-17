package com.example.reader.model.book

data class MBook(
	val items: List<MBookItem>
)

data class MBookItem(
	val id: String?,
	val selfLink: String?,
	val volumeInfo: MVolumeInfo
)

data class MVolumeInfo(
	val title: String?,
	val publishedDate: String?,
	val pageCount: Int?,
	val imageLinks: MImageLink
)

data class MImageLink(
	val smallThumbnail: String?,
	val thumbnail: String?
)
