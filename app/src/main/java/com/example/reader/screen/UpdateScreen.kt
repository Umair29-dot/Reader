package com.example.reader.screen

import android.util.Log
import android.view.Surface
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.reader.component.CompAppToBar
import com.example.reader.model.book.MBookItem
import com.example.reader.util.Resource
import com.example.reader.viewmodel.HomeViewModel
import com.example.reader.viewmodel.UpdateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(navController: NavController, bookId: String, viewModel: UpdateViewModel) {
	Log.d("testing","inside11")

	Scaffold(
		topBar = {
			CompAppToBar(title = "Update Book", navController = navController, showProfile = false, icon = Icons.Default.ArrowBack) {
				navController.navigateUp()
			}
		}
	) {
		Column(
			modifier = Modifier
				.padding(it)
		) {
			LaunchedEffect(Unit) {
				viewModel.getBookById(bookId)
			}

			val resource = viewModel.book.observeAsState().value
			when(resource) {
				is Resource.Loading -> {
					CircularProgressIndicator()
				}
				is Resource.Success -> {
					resource.data?.let {
						mainContent(book = it)
					}
				}
				is Resource.Error -> {
					Toast.makeText(LocalContext.current, resource.message.toString(), Toast.LENGTH_SHORT).show()
				}
				else -> {}
			}
		}
	}
}

@Composable
fun mainContent(book: MBookItem) {
	Column(
		modifier = Modifier
			.fillMaxSize()
	) {
		Card(
			modifier = Modifier
				.padding(20.dp)
				.fillMaxWidth(),
			shape = RoundedCornerShape(30.dp),
			elevation = CardDefaults.cardElevation(5.dp)
		) {
			Row(
				modifier = Modifier
					.padding(10.dp)
					.fillMaxWidth(),
				horizontalArrangement = Arrangement.Center
			) {
				Image(painter = rememberAsyncImagePainter(model = book.volumeInfo.imageLinks.thumbnail ?: ""), contentDescription = "Book Image", modifier = Modifier.size(120.dp))

				Column {
					Text(text = book.volumeInfo.title ?: "",
					    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
						maxLines = 2,
						overflow = TextOverflow.Ellipsis
					)

					Text(text = book.volumeInfo.authors?.toString() ?: "",
					maxLines = 2,
					overflow = TextOverflow.Ellipsis)

					Text(text = book.volumeInfo.publishedDate ?: "")
				}//: Column
			}//: Row
		}//: Card
	}
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
	UpdateScreen(navController = rememberNavController(), bookId = "", viewModel = hiltViewModel())
}