package com.example.reader.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reader.model.book.MBookItem
import com.example.reader.model.firebase.MBookFirebase
import com.example.reader.repository.BookRepository
import com.example.reader.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: BookRepository, private val firestore: FirebaseFirestore): ViewModel() {

	private var _data: MutableLiveData<Resource<MBookItem>> = MutableLiveData()
	var data: LiveData<Resource<MBookItem>> = _data

	private var _status: MutableLiveData<Resource<String>> = MutableLiveData()
	var status: LiveData<Resource<String>> = _status

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

	fun saveBookToFirebase(book: MBookFirebase) {
		viewModelScope.launch {
			firestore.collection("books")
				.add(book)
				.addOnSuccessListener {
					firestore.collection("books").document(it.id)
						.update(hashMapOf("documentId" to it.id) as Map<String, Any>)
						.addOnSuccessListener {
							_status.value = Resource.Success("Saved")
						}
						.addOnFailureListener {
							_status.value = Resource.Error(it.message.toString())
						}
				}
				.addOnFailureListener {
					_status.value = Resource.Error(it.message.toString())
				}
		}
	}

}