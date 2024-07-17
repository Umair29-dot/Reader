package com.example.reader.repository

import com.example.reader.network.BookApi
import javax.inject.Inject


class BookRepository @Inject constructor(private val bookApi: BookApi) {

	suspend fun getAllBooks(query: String) = bookApi.getAllBooks(query)
	suspend fun getBookWithId(id: String) = bookApi.getBookWithId(id)

}