package com.example.reader.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.reader.screen.DetailScreen
import com.example.reader.screen.HomeScreen
import com.example.reader.screen.LoginScreen
import com.example.reader.screen.ProfileScreen
import com.example.reader.screen.SearchScreen
import com.example.reader.screen.SignupScreen
import com.example.reader.screen.SplashScreen
import com.example.reader.screen.UpdateScreen
import com.example.reader.viewmodel.HomeViewModel
import com.example.reader.viewmodel.UpdateViewModel

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
			val viewModel = hiltViewModel<HomeViewModel>()
			HomeScreen(navController = navController, viewModel)
		}
		composable(route = AppScreens.ProfileScreen.name) {
			ProfileScreen()
		}
		composable(route = AppScreens.SearchScreen.name) {
			SearchScreen(navController = navController)
		}
		composable(route = "${AppScreens.DetailScreen.name}/{bookId}", arguments = listOf(
			navArgument("bookId") {
				type = NavType.StringType
			}
		)) {
			it.arguments?.getString("bookId")?.let {
				DetailScreen(navController = navController, bookId = it)
			}
		}
		composable(route = "${AppScreens.UpdateScreen.name}/{bookId}", arguments = listOf(
			navArgument("bookId") {
				type = NavType.StringType
			}
		)) {
			it.arguments?.getString("bookId")?.let {
				val viewModel: UpdateViewModel = hiltViewModel()
				UpdateScreen(navController = navController, bookId = it, viewModel = viewModel)
			}
		}
	}

}