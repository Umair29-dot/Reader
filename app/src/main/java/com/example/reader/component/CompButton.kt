package com.example.reader.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CompButton(title: String, onClick: () -> Unit) {
	Column(modifier = Modifier
		.fillMaxWidth()
	) {
		Button(onClick = {
			onClick()
		},
			modifier = Modifier
				.width(170.dp)
				.height(50.dp)
				.align(Alignment.CenterHorizontally)
		) {
			Text(text = title)
		}
	}
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
	Column {
		CompButton(title = "Login") {

		}
	}
}