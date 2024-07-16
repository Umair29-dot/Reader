package com.example.reader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.reader.navigation.AppNavigation
import com.example.reader.ui.theme.ReaderTheme
import com.example.reader.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			ReaderTheme {
				ReaderApp()
			}
		}
	}
}

@Composable
private fun ReaderApp() {
	Surface(
		modifier = Modifier
			.fillMaxSize()
	) {
		AppNavigation()
	}
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
	ReaderTheme {
		ReaderApp()
	}
}