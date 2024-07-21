package com.example.reader.viewmodel

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
class DetailViewModel @Inject constructor(private val repository: BookRepository): ViewModel() {

	private var _data: MutableLiveData<Resource<MBookItem>> = MutableLiveData()
	var data: LiveData<Resource<MBookItem>> = _data

	fun getBookDetail(id: String) {
		_data.value = Resource.Loading()
		viewModelScope.launch(Dispatchers.IO) {
			try {
				val book = repository.getBookWithId(id)
				withContext(Dispatchers.Main) {
					_data.value = Resource.Success(book)
				}
			} catch (e: Exception) {
				withContext(Dispatchers.Main) {
					_data.value = Resource.Error(e.message.toString())
				}
			}
		}
	}

}