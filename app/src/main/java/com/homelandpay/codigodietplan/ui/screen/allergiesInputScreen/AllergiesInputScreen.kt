package com.homelandpay.codigodietplan.ui.screen.allergiesInputScreen


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.homelandpay.codigodietplan.components.CustomTextButton
import com.homelandpay.codigodietplan.components.GradientButton
import com.homelandpay.codigodietplan.components.SimpleAlertDialog
import com.homelandpay.codigodietplan.data.PreferencesManager
import com.homelandpay.codigodietplan.navigation.Routes
import com.homelandpay.codigodietplan.shareViewmodel.ShareViewModel

import com.homelandpay.codigodietplan.ui.screen.diet_selection_screen.DietSelectionScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllergiesInputScreen(
    navController: NavController,
    shareViewModel: ShareViewModel
) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val diets = preferencesManager.getDietItem()
    Log.d("@selectedDiets", diets.toString())
    var textFieldValue by remember { mutableStateOf("") }
    var selectedtextFieldValue = remember {
        mutableStateListOf<String>()
    }
    var shouldShouldErrorDialog by remember {
        mutableStateOf(false)
    }
    val shareValue = shareViewModel.progressStatus.value ?: 0

    var progressStatus by remember {
        mutableIntStateOf(shareValue)
    }

    var showDialog by remember { mutableStateOf(false) }
    val items = listOf("Milk", "Wheat", "Nasacort", "Nasalide", "Nasonex")
    var filteredItems by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(textFieldValue) {
        filteredItems = if (textFieldValue.isNotEmpty()) {
            items.filter { it.contains(textFieldValue, ignoreCase = true) }
        } else {
            items
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0F7FA)),
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "Write any specifice allergies or sensitivity toward specific things. (Optional )",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp)
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(70.dp)
                            .padding(start = 8.dp)
                            .border(
                                BorderStroke(
                                    2.dp,
                                    Color.Gray
                                ),
                                RoundedCornerShape(4.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (selectedtextFieldValue.toList().isNotEmpty()) {
                            Text(
                                text = selectedtextFieldValue.toList().toString(),

                                )
                        }

                    }

                    Spacer(modifier = Modifier.width(10.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {
                            textFieldValue = it
                            showDialog = true
                        },
                        placeholder = {
                            Text(text = "Sensitivity (optional)", fontStyle = FontStyle.Italic)
                        },

                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                    )
                }


            }

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomTextButton(text = "Back") {
                            navController.popBackStack()
                        }

                        GradientButton(percent = 20, text = "Next") {
                            val selectedAllergies = selectedtextFieldValue.toList()
                            if (selectedAllergies.isNotEmpty()) {
                                preferencesManager.saveOptionalAlgeries(selectedAllergies)
                                shareViewModel.progressStatus.value = 100
                                navController.navigate(Routes.SUMMARY_SCREEN)
                                Log.d("@selectedAllergies", selectedAllergies.toString())
                            } else {
                                shouldShouldErrorDialog = true
                            }

                        }

                    }

                    val progressFloat =
                        progressStatus / 100f


                    LinearProgressIndicator(
                        progress = progressFloat,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        color = Color.Red
                    )
                }
            }

        }
        if (shouldShouldErrorDialog) {
            SimpleAlertDialog(
                description = "Please Check Input Fields",
                onDismiss = {
                    shouldShouldErrorDialog = false
                },

                )
        }


        val isShowFilterDialog = remember {
            derivedStateOf {
                showDialog
            }
        }
        if (isShowFilterDialog.value) {
            AlertDialog(onDismissRequest = {
                showDialog = false
            }) {
                Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        filteredItems.forEach { item ->
                            Text(
                                text = item,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        textFieldValue = item
                                        selectedtextFieldValue.add(item)
                                        showDialog = false
                                    }
                                    .padding(16.dp)
                            )
                        }
                    }
                }

            }

        }
    }

}

@Preview
@Composable
fun PreviewAllergiesInputScreen() {
    val navController = rememberNavController()
     AllergiesInputScreen(navController =navController, shareViewModel = hiltViewModel() )
}


