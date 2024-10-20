package com.shadows.tigers.fury

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "loading") {
        composable(route = "loading") {
            LoadingScreen {
                navController.navigateSingleTopTo("home", false)
            }
        }
        composable(route = "home") {
            MenuScreen(
                onPlay = {
                    navController.navigateSingleTopTo("play")
                },
                onSettings = {
                    navController.navigateSingleTopTo("settings")
                },
                onStats = {
                    navController.navigateSingleTopTo("stats")
                },
                onExit = {
                    navController.navigateSingleTopTo("exit")
                }
            )
        }
        composable(route = "play") {
            LevelsScreen(
                onBack = navController::popBackStackWithSound,
                onLevel = {
                    navController.navigateSingleTopTo("play/$it")
                }
            )
        }
        composable(
            route = "play/{level}",
            arguments = listOf(navArgument("level") { type = NavType.IntType })
        ) {
            val level = it.arguments?.getInt("level") ?: 1
            GameScreen(
                level = level,
                onBack = navController::popBackStackWithSound,
                onLevel = { lvl ->
                    navController.navigateSingleTopTo("play/$lvl") {
                        popUpTo("play/$level") { inclusive = true }
                    }
                }
            )
        }
        composable("settings") {
            SettingsScreen(navController::popBackStackWithSound)
        }
        composable("exit") {
            ExitScreen(navController::popBackStackWithSound)
        }
        composable("stats") {
            StatsScreen(navController::popBackStackWithSound)
        }
    }
}

fun NavHostController.navigateSingleTopTo(
    route: String,
    withSound: Boolean = true,
    builder: NavOptionsBuilder.() -> Unit = {}
) =
    this.navigate(route) {
        launchSingleTop = true
        builder()
    }.also {
        if (withSound) {
            SoundManager.playSound()
        }
    }

fun NavHostController.popBackStackWithSound() {
    this.popBackStack()
    SoundManager.playSound()
}