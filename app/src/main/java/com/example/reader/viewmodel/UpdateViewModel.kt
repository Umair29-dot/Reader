package com.example.reader.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reader.model.book.MBookItem
import com.example.reader.model.firebase.MBookFirebase
import com.example.reader.repository.BookRepository
import com.example.reader.util.Constants
import com.example.reader.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(private val firestore: FirebaseFirestore) : ViewModel() {

	private val _book = MutableLiveData<Resource<MBookFirebase>>()
	val book: LiveData<Resource<MBookFirebase>> = _book

	private val _updateStatus = MutableLiveData<Resource<String>>()
	val updateStatus: LiveData<Resource<String>> = _updateStatus

	fun getFirebaseBookById(id: String) {
		_book.value = Resource.Loading()
		viewModelScope.launch(Dispatchers.IO) {
			try {
				firestore.collection(Constants.COLLECTION_BOOKS).whereEqualTo("google_book_id", id)
					.get().addOnSuccessListener {
						it?.let {
							val book = it.documents.first().toObject(MBookFirebase::class.java)!!
							_book.value = Resource.Success(book)
						}
					}.addOnFailureListener {
						_book.value = Resource.Error(it.message.toString())
					}
			} catch (e: Exception) {
				withContext(Dispatchers.Main) {
					Log.d("delsa", e.message.toString())
					_book.value = Resource.Error(e.message.toString())
				}
			}
		}
	}

	fun updateFirebaseBook(value: Map<String, Any>, documentId: String) {
		viewModelScope.launch {
			firestore.collection(Constants.COLLECTION_BOOKS)
				.document(documentId)
				.update(value)
				.addOnSuccessListener {
					_updateStatus.value = Resource.Success("Successfully Updated")
				}
				.addOnFailureListener {
					_updateStatus.value = Resource.Error(it.message.toString())
				}
		}
	}

}