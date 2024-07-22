package com.example.reader.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CompRoundedButton(
	title: String,
	color: Color,
	onClick: () -> Unit = {}
) {
	Button(onClick = {
		onClick()
	},
	shape = RoundedCornerShape(topStart = 13.dp, bottomEnd = 13.dp),
		colors = ButtonDefaults.buttonColors(color)
	) {
		Text(text = title)
	}
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
	CompRoundedButton(title = "Save", color = Color.Cyan)
}