package com.homelandpay.codigodietplan.ui.screen.diet_selection_screen

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.homelandpay.codigodietplan.components.CustomCheckboxItem
import com.homelandpay.codigodietplan.components.CustomTextButton
import com.homelandpay.codigodietplan.components.GradientButton
import com.homelandpay.codigodietplan.components.SimpleAlertDialog
import com.homelandpay.codigodietplan.components.TrailingIconAlertDialog
import com.homelandpay.codigodietplan.data.PreferencesManager
import com.homelandpay.codigodietplan.data.modal.DietEntity
import com.homelandpay.codigodietplan.navigation.Routes
import com.homelandpay.codigodietplan.shareViewmodel.ShareViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietSelectionScreen(
    navController: NavController,
    shareViewModel: ShareViewModel
) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val conc = preferencesManager.getHealthConcernItem()
    Log.d("@selectedConcerns", conc.toString())
    val dietList = preferencesManager.getAllDiets()
var shouldShouldErrorDialog by remember {
    mutableStateOf(false)
}

    val shareValue = shareViewModel.progressStatus.value ?: 0

    var progressStatus by remember {
        mutableIntStateOf(shareValue)
    }
    var isShowWarningStatus by remember {
        mutableStateOf(false)
    }
    var warningStatus by remember {
        mutableStateOf("")
    }

    val selectedDiets = remember { mutableStateOf(mapOf<DietEntity.Diets, Boolean>()) }

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
                text = "Select the diet you follow",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(20.dp))


            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                dietList.forEach { diet ->
                    val isChecked = selectedDiets.value[diet] ?: false
                    CustomCheckboxItem(
                        text = diet.name,
                        checked = isChecked,
                        onCheckedChange = { selected ->
                            selectedDiets.value = selectedDiets.value.toMutableMap().also {
                                it[diet] = selected
                            }
                        },
                        onTrailingItemClick = {
                            Log.d("@teil", it)
                            isShowWarningStatus = true
                            warningStatus = it

                        }
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

                            val selectedValue = selectedDiets.value.keys.toList()
                            if (selectedValue.isNotEmpty()){
                                preferencesManager.saveDietItem(selectedValue)
                                shareViewModel.progressStatus.value = 80
                                navController.navigate(Routes.ALLERGIES_INPUT_SCREEN)
                                Log.d("@dsf", selectedValue.toString())
                            }else{
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
    }
    if (shouldShouldErrorDialog){
        SimpleAlertDialog(
            description = "Please Check Input Fields",
            onDismiss = {
                shouldShouldErrorDialog = false
            },

            )
    }

    if (isShowWarningStatus){
        TrailingIconAlertDialog(
            title = warningStatus,
            onDismiss = {
                isShowWarningStatus = false
            }
        )
    }


}

@Preview
@Composable
fun PreviewDietSelectionScreen() {
    val navController = rememberNavController()
    DietSelectionScreen(navController, shareViewModel = hiltViewModel())

}