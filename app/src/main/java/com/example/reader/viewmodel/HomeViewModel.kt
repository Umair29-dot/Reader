package com.example.reader.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val firestore: FirebaseFirestore, private val firebaseAuth: FirebaseAuth): ViewModel() {

	val currentUser: String?
		get() {
			return firebaseAuth.currentUser?.email?.split("@")?.get(0)
		}

}