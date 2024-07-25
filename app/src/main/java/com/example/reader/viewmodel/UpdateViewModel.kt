package com.example.reader.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reader.model.book.MBookItem
import com.example.reader.repository.BookRepository
import com.example.reader.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(private val repository: BookRepository): ViewModel() {

	private val _book = MutableLiveData<Resource<MBookItem>>()
	val book: LiveData<Resource<MBookItem>> = _book

	fun getBookById(id: String) {
		_book.value = Resource.Loading()
		viewModelScope.launch(Dispatchers.IO){
			try {
				val item = repository.getBookWithId(id)
				Log.d("delsa", item.toString())
				withContext(Dispatchers.Main) {
					_book.value = Resource.Success(item)
				}
			} catch (e: Exception) {
				withContext(Dispatchers.Main) {
					Log.d("delsa", e.message.toString())
					_book.value = Resource.Error(e.message.toString())
				}
			}
		}
	}

}