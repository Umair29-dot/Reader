package com.example.reader.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reader.model.book.MBook
import com.example.reader.repository.BookRepository
import com.example.reader.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(private val repository: BookRepository): ViewModel() {

	private var _listOfBooks = MutableLiveData<Resource<MBook>>()
	var listOfBooks: LiveData<Resource<MBook>> = _listOfBooks

	init {
		getAllBooks("success")
	}

	fun getAllBooks(searchQuery: String) {
		_listOfBooks.value = Resource.Loading()
		viewModelScope.launch(Dispatchers.IO) {
			try {
				val books = repository.getAllBooks(searchQuery)
				withContext(Dispatchers.Main) {
					_listOfBooks.value = Resource.Success(books)
				}
			} catch (e: Exception) {
				withContext(Dispatchers.Main) {
					_listOfBooks.value = Resource.Error(e.message.toString())
				}
			}
		}
	}

}