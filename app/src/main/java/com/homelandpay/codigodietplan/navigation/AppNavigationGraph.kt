package com.homelandpay.codigodietplan.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.homelandpay.codigodietplan.shareViewmodel.ShareViewModel
import com.homelandpay.codigodietplan.ui.screen.OnboardingScreen
import com.homelandpay.codigodietplan.ui.screen.OutputScreen
import com.homelandpay.codigodietplan.ui.screen.allergiesInputScreen.AllergiesInputScreen
import com.homelandpay.codigodietplan.ui.screen.diet_selection_screen.DietSelectionScreen
import com.homelandpay.codigodietplan.ui.screen.home.HomeScreen
import com.homelandpay.codigodietplan.ui.screen.summary_screen.SummaryScreen

@Composable
fun AppNavigationGraph(
    shareViewModel: ShareViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.ONBOARDING_SCREEN) {
        composable(Routes.ONBOARDING_SCREEN) {
            OnboardingScreen(navController = navController,shareViewModel = shareViewModel)
        }
        composable(Routes.HOME_SCREEN) {
            HomeScreen(navController = navController,shareViewModel = shareViewModel)
        }
        composable(Routes.DIET_SELECTION_SCREEN) {
            DietSelectionScreen(navController = navController,shareViewModel = shareViewModel)
        }

        composable(Routes.SUMMARY_SCREEN) {
            SummaryScreen(navController = navController,shareViewModel = shareViewModel)
        }
        composable(Routes.ALLERGIES_INPUT_SCREEN){
            AllergiesInputScreen(navController = navController,shareViewModel = shareViewModel)
        }
        composable(Routes.OUTPUT_SCREEN){
            OutputScreen(navController = navController)
        }
    }
}