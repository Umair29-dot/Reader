package com.example.reader.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.reader.R
import com.example.reader.component.CompAppToBar
import com.example.reader.component.CompBookListCard
import com.example.reader.component.CompTitleSection
import com.example.reader.model.book.MBook
import com.example.reader.navigation.AppScreens
import com.example.reader.viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

	val viewModel = hiltViewModel<HomeViewModel>()

	Scaffold(
		topBar = {
				 CompAppToBar(title = "Reader", showProfile = true, navController = navController)
		},
		floatingActionButton = {
			FABContent {
				navController.navigate(AppScreens.SearchScreen.name)
			}
		}
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(it)
		) {
			HomeContent(navController = navController, viewModel = viewModel)
		}//: Column
	}//: Scaffold
}

@Composable
private fun HomeContent(navController: NavController, viewModel: HomeViewModel) {

	val booksList = listOf(
		MBook(id = "11", "Awaz e Dost", authors = "Sunny", "A Great Book"),
		MBook(id = "12", "The hunters", authors = "Robert William", "A Great Book"),
		MBook(id = "13", "Darbar", authors = "Mirza Ghalib", "A Great Book"),
		MBook(id = "14", "Android KMP", authors = "Umair Nazim", "A Great Book")
	)

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(10.dp)
			.verticalScroll(rememberScrollState()),
		verticalArrangement = Arrangement.Top
	) {

		Spacer(modifier = Modifier.height(20.dp))

		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			CompTitleSection(title = "Your reading \n activity right now...")

			Column(
				modifier = Modifier.width(70.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Icon(
					imageVector = Icons.Filled.AccountCircle,
					contentDescription = "Profile",
				    modifier = Modifier
						.size(40.dp)
						.clickable {
							navController.navigate(AppScreens.ProfileScreen.name)
						},
				    tint = colorResource(id = R.color.dark_green)
				)

				Text(
					text = viewModel.currentUser?.uppercase() ?: "N/A",
					modifier = Modifier.padding(3.dp),
					style = MaterialTheme.typography.titleMedium,
					maxLines = 1,
					overflow = TextOverflow.Clip
				)

				Divider()
			}//: Column
		}//: Row

		Spacer(modifier = Modifier.height(20.dp))

		ReadingRightNowArea(books = listOf(), navController = navController)

		Spacer(modifier = Modifier.height(20.dp))

		CompTitleSection(title = "Reading List")

		Spacer(modifier = Modifier.height(10.dp))

		BookListArea(list = booksList, navController = navController)
	}//: Column
}

@Composable
private fun ReadingRightNowArea(books: List<MBook>, navController: NavController) {
	CompBookListCard(book = MBook(
		id = "1",
		title = "Behind the enemy lines",
		authors = "Umair",
		notes = "A happy life"
	)
	)
}

@Composable
private fun BookListArea(list: List<MBook>, navController: NavController) {
	val scrollState = rememberScrollState()

	Row(modifier = Modifier
		.fillMaxWidth()
		.height(280.dp)
		.horizontalScroll(scrollState)) {
		for(book in list) {
			CompBookListCard(book = book) {
				//Todo: Navigate to detail Screen
			}
		}
	}

}


@Composable
private fun FABContent(onTap: () -> Unit) {
	FloatingActionButton(onClick = {
		onTap()
	}) {
		Icon(imageVector = Icons.Default.Add,
			contentDescription = "Add a Book",
		    tint = Color.White)
	}
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
	HomeScreen(navController = rememberNavController())
}