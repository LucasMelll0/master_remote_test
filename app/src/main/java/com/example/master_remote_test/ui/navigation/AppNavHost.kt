package com.example.master_remote_test.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.master_remote_test.ui.auth.LoginScreen
import com.example.master_remote_test.ui.components.LoadingDialog
import com.example.master_remote_test.ui.dashboard.DashboardScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var isLoading: Boolean by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
    ) {
        if (isLoading) LoadingDialog()
    }
    AnimatedNavHost(
        navController = navController, startDestination = Login.route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { width -> width * 2 },
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { width -> -2 * width },
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { width -> -2 * width },
                animationSpec = tween(700)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { width -> 2 * width },
                animationSpec = tween(700)
            )
        },
        modifier = modifier
    ) {
        composable(Login.route) {
            LoginScreen(onClickLogin = { navController.navigateInclusive(Dashboard.route) })
        }

        composable(Dashboard.route) {
            DashboardScreen()
        }
    }
}

fun NavHostController.navigateInclusive(route: String) =
    this.navigate(route) {
        popUpTo(0)
    }