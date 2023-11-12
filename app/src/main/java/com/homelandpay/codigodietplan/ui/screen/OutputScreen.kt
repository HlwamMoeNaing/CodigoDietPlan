package com.homelandpay.codigodietplan.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.homelandpay.codigodietplan.data.PreferencesManager

@Composable
fun OutputScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val outputString = preferencesManager.getCombineJson()
    Box (
        modifier = Modifier.fillMaxSize().background(Color(0xFFE0F7FA))
    ){
        Text(text = outputString)

    }

}