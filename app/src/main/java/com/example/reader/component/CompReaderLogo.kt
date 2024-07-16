package com.example.reader.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun CompReaderLogo() {
	Text(text = "Reader",
	fontSize = 35.sp,
	color = Color.Red.copy(alpha = 0.5f),
	style = MaterialTheme.typography.titleLarge,
	fontWeight = FontWeight.Bold)
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
	Column {
		CompReaderLogo()
	}
}