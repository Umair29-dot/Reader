package com.example.reader.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.reader.component.CompAppToBar
import com.example.reader.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, bookId: String) {

	val viewModel: DetailViewModel = hiltViewModel()

	Scaffold(
		topBar = {
			CompAppToBar(title = "Book Detail", icon = Icons.Default.ArrowBack , showProfile = false, navController = navController, onBackArrowPressed = {
				navController.popBackStack()
			})
		}
	) {
		Surface(
			modifier = Modifier
				.padding(it)
		) {
			Column(
				modifier = Modifier
					.padding(all = 10.dp),
				verticalArrangement = Arrangement.Top,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Text(bookId)
			}//: Column
		}//: Surface
	}//: Scaffold

}

@Preview
@Composable
private fun Preview() {
	DetailScreen(navController = rememberNavController(), bookId = "")
}