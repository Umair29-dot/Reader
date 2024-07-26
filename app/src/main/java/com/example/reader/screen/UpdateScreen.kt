package com.example.reader.screen

import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import com.example.reader.component.CompRoundedButton
import com.example.reader.model.book.MBookItem
import com.example.reader.model.firebase.MBookFirebase
import com.example.reader.util.Resource
import com.example.reader.viewmodel.UpdateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(navController: NavController, bookId: String, viewModel: UpdateViewModel) {
	val context = LocalContext.current

	Scaffold(
		topBar = {
			CompAppToBar(title = "Update Book", navController = navController, showProfile = false, icon = Icons.Default.ArrowBack) {
				navController.navigateUp()
			}
		}
	) {
		Surface(
			modifier = Modifier
				.fillMaxSize()
				.padding(it)
		) {
			LaunchedEffect(Unit) {
				viewModel.getFirebaseBookById(bookId)
			}

			val resource = viewModel.book.observeAsState().value
			when(resource) {
				is Resource.Loading -> {
					CircularProgressIndicator()
				}
				is Resource.Success -> {
					resource.data?.let {
						mainContent(book = it, viewModel)
					}
				}
				is Resource.Error -> {
					Toast.makeText(context, resource.message.toString(), Toast.LENGTH_SHORT).show()
				}
				else -> {}
			}

			val resourceStatus = viewModel.updateStatus.observeAsState().value
			when(resourceStatus) {
				is Resource.Loading -> {
					CircularProgressIndicator()
				}
				is Resource.Success -> {
					resourceStatus.data?.let {
						Toast.makeText(context, resourceStatus.data.toString(), Toast.LENGTH_SHORT).show()
					}
				}
				is Resource.Error -> {
					Toast.makeText(context, resourceStatus.message.toString(), Toast.LENGTH_SHORT).show()
				}
				else -> {}
			}
		}
	}
}

@Composable
fun mainContent(book: MBookFirebase, viewModel: UpdateViewModel) {
	val notesText = remember {
		mutableStateOf("")
	}
	val isReadingStarted = remember {
		mutableStateOf(false)
	}
	val isReadingFinished = remember {
		mutableStateOf(false)
	}
	val ratingValue = remember {
		mutableStateOf(0)
	}

	Column {
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
				Image(painter = rememberAsyncImagePainter(model = book.photo ?: ""), contentDescription = "Book Image", modifier = Modifier.size(120.dp))

				Column {
					Text(
						text = book.title ?: "",
					    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
						maxLines = 2,
						overflow = TextOverflow.Ellipsis
					)

					Text(
						text = book.authors ?: "",
						maxLines = 2,
					    overflow = TextOverflow.Ellipsis
					)

					Text(text = book.publishedDate?: "")
				}//: Column
			}//: Row
		}//: Card

		Spacer(modifier = Modifier.height(10.dp))


		NoteView(defaultValue = if(book.notes.isNullOrEmpty()) "No thoughts available :(" else book.notes.toString()) {
			notesText.value = it
		}

		Spacer(modifier = Modifier.height(10.dp))

		Row(
			modifier = Modifier
				.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			TextButton(
				onClick = {
					isReadingStarted.value = !isReadingStarted.value
			    },
			    enabled = book.startReading == null
			) {
				if(isReadingStarted.value) {
					Text(text = "Reading Started",
					color = Color.Red.copy(alpha = 0.5f),
					fontWeight = FontWeight.Bold,
					fontSize = 18.sp)
				} else {
					Text(text = "Start Reading",
						fontWeight = FontWeight.Bold,
						fontSize = 18.sp)
				}
			}

			TextButton(
				onClick = {
				isReadingFinished.value = !isReadingFinished.value
			    },
				enabled = book.finishReading == null
			) {
				if(isReadingFinished.value) {
					Text(text = "Finished Reading",
						color = Color.Red.copy(alpha = 0.5f),
						fontWeight = FontWeight.Bold,
						fontSize = 18.sp)
				} else {
					Text(text = "Mark as Read",
						fontWeight = FontWeight.Bold,
						fontSize = 18.sp)
				}
			}
		}//: Row

		Spacer(modifier = Modifier.height(10.dp))

		RatingView(rating = book.rating?.toInt() ?: 0) {
			ratingValue.value = it
		}

		Spacer(modifier = Modifier.height(10.dp))

		Row(
			modifier = Modifier
				.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			CompRoundedButton(title = "Update", color = MaterialTheme.colorScheme.primary) {
				val updated = hashMapOf<String, Any>(
					"rating" to ratingValue.value,
					"notes" to notesText.value
				).toMap()
				viewModel.updateFirebaseBook(value = updated, documentId = book.documentId ?: "" )
			}

			CompRoundedButton(title = "Delete", color = MaterialTheme.colorScheme.primary)
		}//: Row
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteView(defaultValue: String, onSearch: (String) -> Unit) {
	val textFieldValue = rememberSaveable {
		mutableStateOf(defaultValue)
	}
	val keyboardController = LocalSoftwareKeyboardController.current
	val valid = remember(textFieldValue.value) {
		textFieldValue.value.trim().isNotEmpty()
	}

	OutlinedTextField(
		modifier = Modifier
			.fillMaxWidth()
			.height(100.dp)
			.padding(horizontal = 20.dp),
		value = textFieldValue.value,
		onValueChange = {
			textFieldValue.value = it
		},
		label = { Text(text = "Enter your thoughts") },
		enabled = true,
		keyboardActions = KeyboardActions {
			if(!valid) return@KeyboardActions
			onSearch(textFieldValue.value)
			keyboardController?.hide()
		}
	)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun RatingView(rating: Int, onSelect: (Int) -> Unit) {
	var ratingState = remember {
		mutableStateOf(rating)
	}
	var selected = remember {
		mutableStateOf(false)
	}
	val size by animateDpAsState(
		targetValue = if(selected.value) 42.dp else 34.dp,
	spring(Spring.DampingRatioMediumBouncy))  // animation

	Row(
		modifier = Modifier
			.fillMaxWidth(),
		horizontalArrangement = Arrangement.Center
	) {
		for (i in 1..5) {
			Icon(painter = painterResource(id = R.drawable.baseline_star_24),
				contentDescription = "Star",
			modifier = Modifier
				.size(size)
				.pointerInteropFilter {
					when (it.action) {
						MotionEvent.ACTION_DOWN -> {
							selected.value = true
							onSelect(i)
							ratingState.value = i
						}

						MotionEvent.ACTION_UP -> {
							selected.value = false
						}
					}
					true
				},
				tint = if(i <= ratingState.value) Color(0xFFFFD700) else Color(0xFFA2ADB1)
			)
		}
	}//: Row

}

@Preview(showBackground = true)
@Composable
private fun Preview() {
	UpdateScreen(navController = rememberNavController(), bookId = "", viewModel = hiltViewModel())
}