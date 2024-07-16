package com.example.reader.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompTextFieldPassword(
	value: String,
	valueChange: MutableState<String>,
	singleLine: Boolean = true,
	title: String,
	passwordVisibility: MutableState<Boolean>,
	imeAction: ImeAction = ImeAction.Done
) {
	val visualTransformation = if(passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()

	OutlinedTextField(
		value = value,
		onValueChange = {
			valueChange.value = it
		},
		singleLine = singleLine,
		label = { Text(title) },
		keyboardOptions = KeyboardOptions(
			keyboardType = KeyboardType.Password,
			imeAction = imeAction
		),
		visualTransformation = visualTransformation,
		trailingIcon = {
			IconButton(onClick = {
				passwordVisibility.value = !passwordVisibility.value
			}) {
				Icons.Default.Close
			}
		}
	)
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
	Column {
		CompTextFieldPassword(
			value = "",
			valueChange = mutableStateOf(""),
			singleLine = true,
			title = "Password",
			passwordVisibility = mutableStateOf(false)
		)
	}
}