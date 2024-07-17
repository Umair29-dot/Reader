package com.example.reader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reader.screen.HomeScreen
import com.example.reader.screen.LoginScreen
import com.example.reader.screen.ProfileScreen
import com.example.reader.screen.SearchScreen
import com.example.reader.screen.SignupScreen
import com.example.reader.screen.SplashScreen


@Composable
fun AppNavigation() {
	
	val navController: NavHostController = rememberNavController()
	NavHost(navController = navController, startDestination = AppScreens.SplashScreen.name) {
		composable(route = AppScreens.SplashScreen.name) {
			SplashScreen(navController)
		}
		composable(route = AppScreens.LoginScreen.name) {
			LoginScreen(navController)
		}
		composable(route = AppScreens.SignupScreen.name) {
			SignupScreen(navController = navController)
		}
		composable(route = AppScreens.HomeScreen.name) {
			HomeScreen(navController = navController)
		}
		composable(route = AppScreens.ProfileScreen.name) {
			ProfileScreen()
		}
		composable(route = AppScreens.SearchScreen.name) {
			SearchScreen(navController = navController)
		}
	}

}