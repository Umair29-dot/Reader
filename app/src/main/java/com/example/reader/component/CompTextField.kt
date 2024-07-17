package com.example.reader.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompTextField(
	value: String,
	valueChange: MutableState<String>,
	singleLine: Boolean = true,
	title: String,
	onAction: () -> Unit = {}
) {
	OutlinedTextField(
		value = value,
		onValueChange = {
			valueChange.value = it
		},
		singleLine = singleLine,
		label = { Text(title) },
		keyboardActions = KeyboardActions {
			onAction()
		}
	)
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
	Column {
		CompTextField(
			value = "",
			valueChange = mutableStateOf(""),
			singleLine = true,
			title = "Email"
		)
	}
}