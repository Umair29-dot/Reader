package com.example.reader.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reader.model.user.MUser
import com.example.reader.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val auth: FirebaseAuth, private val firebaseFirestore: FirebaseFirestore): ViewModel() {

	private val _signupStatus: MutableLiveData<Resource<String>> = MutableLiveData()
	val signupStatus: LiveData<Resource<String>> = _signupStatus

	fun signupWithEmailAndPassword(email: String, password: String) {
		_signupStatus.value = Resource.Loading()
		viewModelScope.launch {
			try {
				auth.createUserWithEmailAndPassword(email, password)
					.addOnSuccessListener {
						it.user?.let {
							addUserToDatabase(it.email!!, it.uid)
						}
					}
					.addOnFailureListener {
						_signupStatus.value = Resource.Error(message = it.message.toString())
					}
			} catch (e: Exception) {
				_signupStatus.value = Resource.Error(message = e.message.toString())
			}
		}
	}

	private fun addUserToDatabase(name: String, uid: String) {
		try {
			val user = MUser(
				userId = uid,
				displayName = name.split('@').get(0), //umair@gmail.com  -> umair//
			    quote = "Life is Great",
				profession = "Android Developer"
			)

			firebaseFirestore.collection("users")
				.add(user)
				.addOnSuccessListener {
					_signupStatus.value = Resource.Success(data = it.toString())
				}
				.addOnFailureListener {
					_signupStatus.value = Resource.Error(message = it.message.toString())
				}
		} catch (e: Exception) {
			_signupStatus.value = Resource.Error(message = e.message.toString())
		}
	}

}