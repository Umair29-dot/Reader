package com.example.reader.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reader.model.firebase.MBookFirebase
import com.example.reader.util.Constants
import com.example.reader.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val firestore: FirebaseFirestore, private val firebaseAuth: FirebaseAuth): ViewModel() {

	private var _books = MutableLiveData<Resource<List<MBookFirebase>>>()
	var books: LiveData<Resource<List<MBookFirebase>>> = _books

	init {
		getAllSavedBooks()
	}

	val currentUser: String?
		get() {
			return firebaseAuth.currentUser?.email?.split("@")?.get(0)
		}

	fun getAllSavedBooks() {
		_books.value = Resource.Loading()

		try {
			viewModelScope.launch(Dispatchers.IO) {
				firestore.collection(Constants.COLLECTION_BOOKS)
					.get()
					.addOnSuccessListener {
						if (!it.isEmpty) {
							val data = it.documents.map { documentSnapshot ->
								documentSnapshot.toObject(MBookFirebase::class.java)!!
							}
							_books.value = Resource.Success(data)
						} else {
							_books.value = Resource.Error("")
						}
					}
					.addOnFailureListener {
						_books.value = Resource.Error(it.message.toString())
					}
			}
		} catch (e: Exception) {
			Log.d(Constants.APP_ERROR, e.message.toString())
		}

	}

}