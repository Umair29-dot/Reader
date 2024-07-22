package com.example.reader.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.reader.R
import com.example.reader.component.CompAppToBar
import com.example.reader.component.CompRoundedButton
import com.example.reader.model.book.MBookItem
import com.example.reader.model.firebase.MBookFirebase
import com.example.reader.util.Resource
import com.example.reader.viewmodel.DetailViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, bookId: String) {

	val snackBarScope = rememberCoroutineScope()
	val snackbarHostState = remember {
		SnackbarHostState()
	}
	val currentUser: String =  FirebaseAuth.getInstance().currentUser?.uid.toString()
	val viewModel: DetailViewModel = hiltViewModel()

	viewModel.getBookDetail(bookId)

	Scaffold(
		topBar = {
			CompAppToBar(title = "Book Detail", icon = Icons.Default.ArrowBack , showProfile = false, navController = navController, onBackArrowPressed = {
				navController.popBackStack()
			})
		},
		snackbarHost = {
			snackbarHostState
		}
	) {
		Surface(
			modifier = Modifier
				.fillMaxSize()
				.padding(it)
		) {
				val resource = viewModel.data.observeAsState().value
				when(resource) {
					is Resource.Loading -> {
						Column(
							verticalArrangement = Arrangement.Center,
							horizontalAlignment = Alignment.CenterHorizontally
						) {
							CircularProgressIndicator()
						}
					}
					is Resource.Success -> {
						resource.data?.let {
							BookDetailContent(currentBook = it, navController, viewModel, currentUser)
						}
					}
					is Resource.Error -> {
						Toast.makeText(LocalContext.current, resource.message.toString(), Toast.LENGTH_SHORT).show()
					}
					else -> {
						Log.d("dataa", "Nothing")
					}
				}

			val status = viewModel.status.observeAsState().value
			when(status) {
				is Resource.Success -> {
					/*snackBarScope.launch {
						snackbarHostState.showSnackbar(message = status.data.toString(), duration = SnackbarDuration.Short)
					}*/
					Toast.makeText(LocalContext.current, status.data.toString(), Toast.LENGTH_SHORT).show()
					navController.navigateUp()
				}
				is Resource.Error -> {
					snackBarScope.launch {
						snackbarHostState.showSnackbar(message = status.message.toString())
					}
				}
				else -> {
					Log.d("dataa", "Nothing")
				}
			}
		}//: Surface
	}//: Scaffold

}

@Composable
private fun BookDetailContent(currentBook: MBookItem, navController: NavController, viewModel: DetailViewModel, currentUser: String) {

	val imageUrl: String? = currentBook.volumeInfo.imageLinks.smallThumbnail
	val painter = if (imageUrl != null) {
		rememberAsyncImagePainter(model = imageUrl)
	} else {
		painterResource(id = R.drawable.no_image)
	}
	
	Column(
		modifier = Modifier
			.padding(all = 10.dp),
		verticalArrangement = Arrangement.Top,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Card(
			shape = CircleShape
		) {
			Image(painter = painter, contentDescription = "Book Image", modifier = Modifier.size(80.dp))
		}

		Spacer(modifier = Modifier.height(10.dp))

		Text(currentBook.volumeInfo.title ?: "",
			maxLines = 2,
			overflow = TextOverflow.Ellipsis,
			style = MaterialTheme.typography.titleLarge,
			modifier = Modifier.width(200.dp)
		)

		Spacer(modifier = Modifier.height(10.dp))

		Text("Authors: ${currentBook.volumeInfo.authors ?: ""}")
		Text("Page Count: ${currentBook.volumeInfo.pageCount ?: ""}")
		Text("Categories: ${currentBook.volumeInfo.categories ?: ""}", maxLines = 4, overflow = TextOverflow.Ellipsis)
		Text("Published: ${currentBook.volumeInfo.publishedDate ?: ""}")

		Spacer(modifier = Modifier
			.height(10.dp))

		val description = HtmlCompat.fromHtml(currentBook.volumeInfo.description ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
		Surface(
			modifier = Modifier
				.width(350.dp)
				.height(250.dp)
				.padding(5.dp),
			shape = RectangleShape,
			border = BorderStroke(1.dp, Color.Gray)
		) {
			LazyColumn {
				item {
					Text(text = description)
				}
			}
		}//: Surface

		Spacer(modifier = Modifier.padding(10.dp))

		Row(
			modifier = Modifier
				.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			CompRoundedButton(title = "Save", color = Color.Cyan) {

				val book = MBookFirebase(
					title = currentBook.volumeInfo.title,
					notes = "",
					userId = currentUser,
					photo = currentBook.volumeInfo.imageLinks.thumbnail,
					authors = currentBook.volumeInfo.authors.toString(),
					pageCount = currentBook.volumeInfo.pageCount,
					description = currentBook.volumeInfo.description.toString(),
					categories = currentBook.volumeInfo.categories.toString(),
					rating = 0.0,
					googleBookId = currentBook.id.toString()
				)

				viewModel.saveBookToFirebase(book)
			}

			CompRoundedButton(title = "Cancel", color = Color.Cyan) {
				navController.popBackStack()
			}
		}//: Row
	}
}

@Preview
@Composable
private fun Preview() {
	DetailScreen(navController = rememberNavController(), bookId = "")
}