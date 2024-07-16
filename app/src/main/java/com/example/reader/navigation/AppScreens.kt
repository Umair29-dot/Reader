package com.example.reader.navigation

enum class AppScreens {
	LoginScreen,
	SignupScreen,
	HomeScreen,
	DetailScreen,
	SearchScreen,
	SplashScreen,
	UpdateScreen,
	ProfileScreen;

	companion object {
		fun fromRoute(route: String): AppScreens =
			when(route?.substringBefore("/")) {
				SplashScreen.name -> SplashScreen
				LoginScreen.name -> LoginScreen
				SignupScreen.name -> SignupScreen
				HomeScreen.name -> HomeScreen
				SearchScreen.name -> SearchScreen
				DetailScreen.name -> DetailScreen
				UpdateScreen.name -> UpdateScreen
				ProfileScreen.name -> ProfileScreen
				else -> throw IllegalArgumentException("Route $route is not recognized")
			}
	}
}