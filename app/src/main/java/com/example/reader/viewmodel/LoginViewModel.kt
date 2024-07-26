package com.example.reader.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reader.util.Constants
import com.example.reader.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val auth: FirebaseAuth): ViewModel() {

	private val _loginStatus: MutableLiveData<Resource<String>> = MutableLiveData()
	val loginStatus: LiveData<Resource<String>> = _loginStatus

	fun loginWithEmailAndPassword(email: String, password: String) {
		_loginStatus.value = Resource.Loading()
		viewModelScope.launch {
			try{
				auth.signInWithEmailAndPassword(email, password)
					.addOnSuccessListener {
						it.user?.let {
							_loginStatus.value = Resource.Success(data = it.toString())
						}
					}
					.addOnFailureListener {
						Log.d(Constants.APP_ERROR, "error: ${it.message.toString()}")
						_loginStatus.value = Resource.Error(message = it.message.toString())
					}
			}catch (e: Exception) {
				Log.d(Constants.APP_ERROR, e.message.toString())
				_loginStatus.value = Resource.Error(message = e.message.toString())
			}
		}
	}

}