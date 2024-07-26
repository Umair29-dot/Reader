package com.example.reader.screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.reader.R
import com.example.reader.component.CompAppToBar
import com.example.reader.component.CompTextField
import com.example.reader.model.book.MBookItem
import com.example.reader.navigation.AppScreens
import com.example.reader.util.Resource
import com.example.reader.viewmodel.BookSearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
	val context = LocalContext.current
	val viewModel = hiltViewModel<BookSearchViewModel>()

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
					viewModel.getAllBooks(it)
				}
			}

			Spacer(modifier = Modifier.height(20.dp))

			BooksContent(viewModel = viewModel, context, navController)
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
private fun BooksContent(viewModel: BookSearchViewModel, context: Context, navController: NavController) {
	var resource = viewModel.listOfBooks.observeAsState().value

	when(resource) {
		is Resource.Loading -> {
			Column(
				modifier = Modifier
					.fillMaxSize(),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				CircularProgressIndicator()
			}
		}

		is Resource.Success -> {
			resource.data?.items?.let { items ->
				LazyColumn(modifier = Modifier.fillMaxSize()) {
					items(items) { BookListCardItem(item = it, navController) }
				}
			} ?: run {
				Text(text = "No data found")
			}
		}

		is Resource.Error -> {
			Toast.makeText(context, resource.message.toString(), Toast.LENGTH_SHORT).show()
		}

		else -> {
			Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()
		}
	}
}

@Composable
private fun BookListCardItem(item: MBookItem, navController: NavController) {
	val imageUrl: String? = item.volumeInfo.imageLinks.thumbnail
	val painter = if (imageUrl != null) {
		rememberAsyncImagePainter(model = imageUrl)
	} else {
		painterResource(id = R.drawable.no_image)
	}

	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 10.dp, vertical = 5.dp)
			.clickable {
				navController.navigate(AppScreens.DetailScreen.name + "/${item.id}")
			},
		elevation = CardDefaults.cardElevation(5.dp),
		shape = RectangleShape
	) {
		Row(
			modifier = Modifier
				.padding(all = 5.dp)
		) {
			Image(painter = painter,
				  contentDescription = "A book",
			      modifier = Modifier
					  .width(100.dp)
					  .height(120.dp)
			)

			Column {
				Text(text = item.volumeInfo.title.toString(),
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
				    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
				)
				Spacer(modifier = Modifier.height(10.dp))
				Text(text = "Authors: ${item.volumeInfo.authors ?: ""}",
					maxLines = 1,
					overflow = TextOverflow.Clip,
					style = TextStyle(fontWeight = FontWeight.Thin)
				)
				Text(text = "Date: ${item.volumeInfo.publishedDate.toString()}",
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					style = TextStyle(fontWeight = FontWeight.Thin)
				)
				Text(text = "Category: ${item.volumeInfo.categories}",
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					style = TextStyle(fontWeight = FontWeight.Thin)
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