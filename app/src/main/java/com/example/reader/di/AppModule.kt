package com.example.reader.di

import com.example.reader.network.BookApi
import com.example.reader.repository.BookRepository
import com.example.reader.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Singleton
	@Provides
	fun provideFirebaseAuth() = FirebaseAuth.getInstance()

	@Singleton
	@Provides
	fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

	@Singleton
	@Provides
	fun provideRetrofit(): Retrofit = Retrofit.Builder()
		.baseUrl(Constants.BASE_URL)
		.addConverterFactory(GsonConverterFactory.create())
		.build()

	@Singleton
	@Provides
	fun provideBookApi(retrofit: Retrofit): BookApi = retrofit.create(BookApi::class.java)

	@Singleton
	@Provides
	fun provideBookRepository(bookApi: BookApi): BookRepository = BookRepository(bookApi)

}