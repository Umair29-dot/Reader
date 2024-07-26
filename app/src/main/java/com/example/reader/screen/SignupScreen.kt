package com.example.reader.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.reader.R
import com.example.reader.component.CompButton
import com.example.reader.component.CompReaderLogo
import com.example.reader.component.CompTextField
import com.example.reader.component.CompTextFieldPassword
import com.example.reader.navigation.AppScreens
import com.example.reader.util.Resource
import com.example.reader.viewmodel.SignupViewModel

@Composable
fun SignupScreen(navController: NavController) {

	val keyboardController = LocalSoftwareKeyboardController.current
	val signupViewModel: SignupViewModel = hiltViewModel()
	val context = LocalContext.current

	var email = rememberSaveable {
		mutableStateOf("")
	}

	var password = rememberSaveable {
		mutableStateOf("")
	}

	var againPassword = rememberSaveable {
		mutableStateOf("")
	}

	var passwordVisibility = remember {
		mutableStateOf(false)
	}

	Surface(
		modifier = Modifier
			.fillMaxSize()
	) {
		Column(
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier
				.padding(all = 20.dp)
		) {
			CompReaderLogo()

			Spacer(modifier = Modifier
				.height(30.dp)
			)

			CompTextField(value = email.value,
				valueChange = email,
				singleLine = true,
				title = stringResource(id = R.string.email)
			)

			Spacer(modifier = Modifier
				.height(15.dp)
			)

			CompTextFieldPassword(value = password.value,
				valueChange = password,
				singleLine = true,
				stringResource(id = R.string.password),
				passwordVisibility = passwordVisibility
			)

			Spacer(modifier = Modifier
				.height(15.dp)
			)

			CompTextFieldPassword(value = againPassword.value,
				valueChange = againPassword,
				singleLine = true,
				stringResource(R.string.again_password),
				passwordVisibility = passwordVisibility
			)

			Spacer(modifier = Modifier
				.height(25.dp)
			)

			CompButton(title = stringResource(R.string.signup)) {
				signupViewModel.signupWithEmailAndPassword(email = email.value.trim(), password = password.value.trim())
			}

			Spacer(modifier = Modifier
				.height(15.dp)
			)

			Row{
				Text(text = stringResource(R.string.already_have_an_account),
				modifier = Modifier.padding(end = 5.dp)
				)
				Text(text = stringResource(id = R.string.login),
					color = Color.Green,
					modifier = Modifier
						.clickable {
							navController.navigateUp()
						}
				)
			}//: Row

			Spacer(modifier = Modifier
				.height(50.dp)
			)
		}//: Column

		val data = signupViewModel.signupStatus.observeAsState().value
		when(data) {
			is Resource.Loading -> {
				Toast.makeText(context, stringResource(id = R.string.loading), Toast.LENGTH_SHORT).show()
			}
			is Resource.Success -> {
				Toast.makeText(context, stringResource(R.string.user_added_successfully), Toast.LENGTH_SHORT).show()
			}
			is Resource.Error -> {
				Toast.makeText(context, data.message.toString(), Toast.LENGTH_SHORT).show()
			}
			else -> {}
		}
	}//: Surface

}

@Preview
@Composable
private fun Preview() {
	SignupScreen(navController = rememberNavController())
}