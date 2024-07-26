package com.example.reader.screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.reader.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import javax.inject.Inject

@Composable
fun SplashScreen(navController: NavController) {

	val scale = remember {
		Animatable(0f)
	}

	LaunchedEffect(key1 = true, block = {
		scale.animateTo(targetValue = 0.9f,
			animationSpec = tween(durationMillis = 800, easing = {
				OvershootInterpolator(6f)
					.getInterpolation(it)
			}))
		delay(2000L)
		navController.popBackStack()
		if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
			navController.navigate(AppScreens.LoginScreen.name)
		} else {
			navController.navigate(AppScreens.HomeScreen.name)
		}
	})

	Surface(
		modifier = Modifier
			.fillMaxSize()
	) {
		Column(
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Surface(
				modifier = Modifier
					.size(300.dp)
					.scale(scale = scale.value),
				color = Color.White,
				shape = CircleShape,
				shadowElevation = 10.dp
			) {
				Column(
					verticalArrangement = Arrangement.Center,
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					Text(text = "Today's Reader",
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Bold,
					fontSize = 22.sp)

					Text(text = "Tomorrow's Leader",
						style = MaterialTheme.typography.titleLarge,
					color = Color.Red.copy(alpha = 0.5f),
						fontWeight = FontWeight.Bold,
						fontSize = 25.sp)
				}//: Column
			}//: Surface
		}//: Column
	}//: Surface
}

@Preview()
@Composable
private fun Preview() {
	SplashScreen(navController = rememberNavController())
}