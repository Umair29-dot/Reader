package com.example.reader.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BookRating(score: Double) {
	Surface(
		modifier = Modifier
			.height(70.dp)
			.padding(5.dp),
		color = Color.White,
		shape = RoundedCornerShape(50.dp),
		shadowElevation = 5.dp
	) {
		Column(
			modifier = Modifier
				.padding(3.dp),
			verticalArrangement = Arrangement.Center
		) {
			Icon(imageVector = Icons.Default.Star, contentDescription = "Rating Star")
			Text(text = score.toString(), style = MaterialTheme.typography.bodyMedium)
		}
	}//: Surface
}