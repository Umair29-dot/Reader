package com.example.reader.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun CompTitleSection(title: String) {
	Text(text = title,
	style = MaterialTheme.typography.headlineMedium,
	fontWeight = FontWeight.Bold,
		fontSize = 20.sp
	)
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
	CompTitleSection(title = "You are reading")
}