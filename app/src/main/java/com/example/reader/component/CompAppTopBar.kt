package com.example.reader.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.reader.R
import com.example.reader.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompAppToBar(
	title: String,
	icon: ImageVector? = null,
	showProfile: Boolean = true,
	navController: NavController,
	onBackArrowPressed: () -> Unit = {}
) {
	TopAppBar(
		title = {
			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				if(showProfile) {
					Image(painter = painterResource(id = R.drawable.book),
						contentDescription = "Icon",
						modifier = Modifier
							.size(30.dp)
							.clip(RoundedCornerShape(10.dp))
					)
				}
				if (icon != null)  {
					Icon(imageVector = icon,
						contentDescription = "back arrow",
					tint = Color.Red.copy(alpha = 0.7f),
					modifier = Modifier.clickable { onBackArrowPressed() })
				}
				Text(text = title,
				color = Color.Red.copy(0.7f),
				modifier = Modifier
					.padding(start = 10.dp),
					style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp)
				)
			}//: Row
		},
		actions = {
			if (showProfile) {
				IconButton(onClick = {
					FirebaseAuth.getInstance().signOut().run {
						navController.navigate(AppScreens.LoginScreen.name) {
							popUpTo(AppScreens.SplashScreen.name){
								inclusive = true
							}
						}
					}
				}) {
					Icon(painter = painterResource(id = R.drawable.exit),
						contentDescription = "Singout",
						modifier = Modifier.size(30.dp))
				}
			}
		}
	)
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
	CompAppToBar(title = "Home", navController = rememberNavController())
}