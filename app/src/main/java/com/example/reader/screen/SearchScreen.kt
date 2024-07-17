package com.example.reader.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.reader.component.CompAppToBar
import com.example.reader.component.CompTextField
import com.example.reader.model.book.MBook

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
	val context = LocalContext.current

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
			modifier = Modifier
				.fillMaxSize()
				.padding(it)
		) {
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.Center
			) {
				SearchField(context) {
					Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
				}
			}

			Spacer(modifier = Modifier.height(20.dp))

			LazyColumn(modifier = Modifier
				.fillMaxSize()
			) {
				repeat(10) {
					item {
						BookListCardItem(book = MBook(id = "01", title = "Sunny", authors = "william", notes = null))
					}
				}
			}
		}
	}
}

@Composable
private fun SearchField(context: Context, onSearch: (String) -> Unit = {}) {
	val searchQuery = rememberSaveable {
		mutableStateOf("")
	}
	val keyboardController = LocalSoftwareKeyboardController.current
	val valid = remember(searchQuery.value) {
		searchQuery.value.trim().isNotEmpty()
	}

	CompTextField(value = searchQuery.value, valueChange = searchQuery, title = "Search") {
		if (!valid)  {
			Toast.makeText(context, "Search Field can't be empty", Toast.LENGTH_SHORT).show()
		} else {
			onSearch(searchQuery.value.trim())
			searchQuery.value = ""
		}
		keyboardController?.hide()
	}
}
@Composable
private fun BookListCardItem(book: MBook) {
	Card(
		modifier = Modifier.fillMaxWidth().padding(5.dp),
		colors = CardDefaults.cardColors(Color.White),
		elevation = CardDefaults.cardElevation(5.dp),
		shape = RectangleShape
	) {
		Row(
			modifier = Modifier
				.padding(all = 5.dp)
		) {
			Image(painter = rememberAsyncImagePainter("https://cdn-media-1.freecodecamp.org/images/4wFagVnyKAt-oHg6CtqahLwJqkNpOTvUXwZv"),
				  contentDescription = "A book",
			      modifier = Modifier
					  .width(100.dp)
					  .height(120.dp)
			)

			Column {
				Text(text = book.title.toString(),
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
				    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp)
				)
				Spacer(modifier = Modifier.height(10.dp))
				Text(text = "Author: ${book.authors.toString()}",
					maxLines = 1,
					overflow = TextOverflow.Clip
				)
				Text(text = "Date: 2020-9-2",
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
				Text(text = "[Computers]",
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
			}//: Column
		}//: Row
	}//: Card
}

@Preview(showBackground = true)
@Composable
private fun preview() {
	SearchScreen(navController = rememberNavController())
}