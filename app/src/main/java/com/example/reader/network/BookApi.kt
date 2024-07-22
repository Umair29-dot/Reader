package com.example.reader.network

import com.example.reader.model.book.MBook
import com.example.reader.model.book.MBookItem
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {

	@GET("volumes")
	suspend fun getAllBooks(@Query("q") q: String): MBook

	@GET("volumes/{bookId}")
	suspend fun getBookWithId(@Path("bookId") bookId: String): MBookItem
}