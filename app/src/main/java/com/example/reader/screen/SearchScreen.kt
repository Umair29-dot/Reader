package com.example.reader.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.reader.component.CompAppToBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
	Scaffold(
		topBar = {
			CompAppToBar(title = "Search Books",
				icon = Icons.Default.ArrowBack,
				navController = navController,
			showProfile = false) {
				navController.popBackStack()
			}
		}
	) {
		Column(
			modifier = Modifier.padding(it)
		) {

		}
	}
}

@Preview(showBackground = true)
@Composable
private fun preview() {
	SearchScreen(navController = rememberNavController())
}