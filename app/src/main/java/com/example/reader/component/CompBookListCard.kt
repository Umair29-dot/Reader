package com.example.reader.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.reader.R
import com.example.reader.model.firebase.MBookFirebase

@Composable
fun CompBookListCard(book: MBookFirebase, onClick: (String) -> Unit = {}) {

	val context = LocalContext.current
	val resource = context.resources
	val displayMetrics = resource.displayMetrics
	val screenWidth = displayMetrics.widthPixels / displayMetrics.density  //Getting the application width, according to the devices//
	val spacing = 10.dp

	Card(
		modifier = Modifier
			.width(180.dp)
			.background(Color.White)
			.padding(all = 8.dp)
			.clickable {
				book.googleBookId?.let {
					onClick(it)
					return@clickable
				}
				Toast.makeText(context, R.string.oops_something_went_wrong, Toast.LENGTH_SHORT).show()
			},
		shape = RoundedCornerShape(30.dp)
	) {
		Column(
			modifier = Modifier.width(200.dp),
			horizontalAlignment = Alignment.Start
		) {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(10.dp),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Image(painter = rememberAsyncImagePainter(book.photo),
					contentDescription = "Book Image",
					modifier = Modifier
						.height(140.dp)
						.width(100.dp)
						.padding(3.dp)
				)

				Column(
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					Icon(imageVector = Icons.Rounded.FavoriteBorder, contentDescription = "Favourite Icon")

					BookRating(score = 3.5)
				}//: Column
			}//: Row

			Text(text = book.title ?: "",
				modifier = Modifier.padding(5.dp),
				fontWeight = FontWeight.Bold,
				maxLines = 2,
				overflow = TextOverflow.Ellipsis) //Ellipsis means, if the text is large it will put ...

			Text(text = book.authors ?: "",
				modifier = Modifier.padding(5.dp),
				maxLines = 1,
				overflow = TextOverflow.Ellipsis)

			Surface(
				shape = RoundedCornerShape(bottomEndPercent = 20, topStartPercent = 40),
				color = Color(0xFF92CBDF),
				modifier = Modifier.align(alignment = Alignment.End)
			) {
				Text("Reading",
					modifier = Modifier.padding(top = 8.dp, start = 10.dp, end = 20.dp, bottom = 8.dp),
					style = TextStyle(color = Color.White, fontSize = 13.sp)
				)
			}//: Surface
		}//: Column
	}//: Card
}