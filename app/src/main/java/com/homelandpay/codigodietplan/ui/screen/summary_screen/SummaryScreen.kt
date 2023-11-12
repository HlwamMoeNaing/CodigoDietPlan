package com.homelandpay.codigodietplan.ui.screen.summary_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.homelandpay.codigodietplan.components.CustomTextButton
import com.homelandpay.codigodietplan.components.GradientButton
import com.homelandpay.codigodietplan.components.SimpleAlertDialog
import com.homelandpay.codigodietplan.data.PreferencesManager
import com.homelandpay.codigodietplan.data.modal.AllergiesEntity
import com.homelandpay.codigodietplan.data.modal.Data
import com.homelandpay.codigodietplan.data.modal.DietEntity
import com.homelandpay.codigodietplan.data.modal.HealthConcernEntity
import com.homelandpay.codigodietplan.navigation.Routes
import com.homelandpay.codigodietplan.shareViewmodel.ShareViewModel

@Composable
fun SummaryScreen(
    navController: NavController,
    shareViewModel: ShareViewModel
) {

    val shareValue = shareViewModel.progressStatus.value ?: 0

    var progressStatus by remember {
        mutableIntStateOf(shareValue)
    }
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val optionalDiet = preferencesManager.getOptionalAlgeries() ?: emptyList()
    val dietItem = preferencesManager.getDietItem() ?: emptyList()
    val healthConcern = preferencesManager.getHealthConcernItem() ?: emptyList()

    Log.d("@getOptionalAlgeries", optionalDiet.toString())



    var sunExposure by remember { mutableStateOf("") }
    var smokeStatus by remember { mutableStateOf("") }
    var alcoholIntake by remember { mutableStateOf("") }
    var shouldShouldErrorDialog by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA))
            .padding(horizontal = 16.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0F7FA)),
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            // Question 1
            Text("Is your daily exposure to sun limited?")
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = sunExposure == "Yes",
                    onClick = { sunExposure = "Yes" }
                )
                Text("Yes")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = sunExposure == "No",
                    onClick = { sunExposure = "No" }
                )
                Text("No")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Question 2
            Text("Do you current smoke (tobacco or marijuana)?")
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = smokeStatus == "Yes",
                    onClick = { smokeStatus = "Yes" }
                )
                Text("Yes")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = smokeStatus == "No",
                    onClick = { smokeStatus = "No" }
                )
                Text("No")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Question 3
            Text("On average, how many alcoholic beverages do you have in a week?")
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = alcoholIntake == "0 - 1",
                    onClick = { alcoholIntake = "0 - 1" }
                )
                Text("0 - 1")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = alcoholIntake == "2 - 5",
                    onClick = { alcoholIntake = "2 - 5" }
                )
                Text("2 - 5")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = alcoholIntake == "5+",
                    onClick = { alcoholIntake = "5+" }
                )
                Text("5+")
            }

            Spacer(modifier = Modifier.height(24.dp))


            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(

                ) {
                    GradientButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        percent = 20, text = "Get my personalized vitamin"
                    ) {


                        if (sunExposure == "" || smokeStatus == "" ||alcoholIntake == ""){
                            shouldShouldErrorDialog = true
                        }else{
                            val jsonFormat = combineJson(
                                optionalDiet,
                                dietItem,
                                healthConcern,
                                sunExposure,
                                smokeStatus,
                                alcoholIntake
                            )
                            preferencesManager.saveCombineJson(jsonFormat)
                            navController.navigate(Routes.OUTPUT_SCREEN)
                            Log.d("@Printjson","js -> $jsonFormat")
                            println("Sun Exposure: $sunExposure")
                            println("Smoke Status: $smokeStatus")
                            println("Alcohol Intake: $alcoholIntake")
                        }

                    }

                    val progressFloat =
                        progressStatus / 100f

                    // Divider(modifier = Modifier.height(8.dp).background(Color.Blue))
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
    }

    if (shouldShouldErrorDialog){
        SimpleAlertDialog(
            description = "Please Check Input Fields",
            onDismiss = {
                shouldShouldErrorDialog = false
            },

        )
    }

}

fun combineJson(
    algeries: List<String>,
    diets: List<DietEntity.Diets>,
    healthConcen: List<HealthConcernEntity.HealthConcern>,
    sunExposure:String,
    smokestatus:String,
    alcoholIntake:String

):String {

    val healthConcernJsonString = Gson().toJson(healthConcen)
    val algeriesJsonString = Gson().toJson(algeries)
    val dietsJsonString = Gson().toJson(diets)
    val combineJson = """
        {
            "allergies": $healthConcernJsonString,
            "diets": $dietsJsonString,
            "algeries":$algeriesJsonString,
            "Sun Exposure":$sunExposure,
            "Smoke Status:$smokestatus,
            "Alcohol Intake:$alcoholIntake
            
        }
    """.trimIndent()
    return combineJson
}

@Preview
@Composable
fun PreviewSummaryScreen() {
    val navController = rememberNavController()
   // SummaryScreen(navController)
}