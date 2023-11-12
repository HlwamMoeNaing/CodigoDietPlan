package com.homelandpay.codigodietplan.ui.screen.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.homelandpay.codigodietplan.components.CustomTextButton
import com.homelandpay.codigodietplan.components.GradientButton
import com.homelandpay.codigodietplan.components.HealthConcernChip
import com.homelandpay.codigodietplan.components.SimpleAlertDialog
import com.homelandpay.codigodietplan.data.PreferencesManager
import com.homelandpay.codigodietplan.data.modal.HealthConcernEntity
import com.homelandpay.codigodietplan.navigation.Routes
import com.homelandpay.codigodietplan.shareViewmodel.ShareViewModel
import com.homelandpay.codigodietplan.util.DragDropList
import com.homelandpay.codigodietplan.util.move

@Composable
fun HomeScreen(
    navController: NavController,
    shareViewModel: ShareViewModel
) {

    var shouldShouldErrorDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val healthConcernList = preferencesManager.getAllHealthConcern()

    var selectedList = remember {
        mutableStateListOf<HealthConcernEntity.HealthConcern>()
    }
   val shareValue = shareViewModel.progressStatus.value ?: 0

    var progressStatus by remember {
       mutableIntStateOf(shareValue)
    }

    var selectedConcerns by remember { mutableStateOf(setOf<String>()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
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
                text = "Select the top health concerns.* (upto 5)",
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                items(healthConcernList) { concern ->
                    HealthConcernChip(
                        healthConcern = concern,
                        isSelected = selectedConcerns.contains(concern.name),
                        onSelectionChanged = { selected ->
                            selectedConcerns = selectedConcerns.toMutableSet().apply {
                                if (selected) {
                                    //Log.d("@concern",concern)
                                    selectedList.add(concern)
                                    if (size < 5) add(concern.name)
                                } else {
                                    selectedList.remove(concern)
                                    remove(concern.name)
                                }
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "Prioritize",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            //todo save vm

            DragDropList(
                items = selectedList,
                onMove = { fromIndex, toIndex ->
                    selectedList.move(fromIndex, toIndex)
                }
            )

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

                    if (selectedList.toList().isNotEmpty()){
                        Log.d("@concern", selectedList.toList().toString())
                        preferencesManager.saveHealthConcernItem(selectedList.toList())
                        shareViewModel.progressStatus.value = 50
                        navController.navigate(Routes.DIET_SELECTION_SCREEN)
                    }else{
                        shouldShouldErrorDialog = true
                    }

                }

            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                //val progressStatus = shareViewModel.progressStatus.value ?: 0
                val progressFloat =
                    progressStatus / 100f
            Log.d("@Prg",progressStatus.toString())
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
    if (shouldShouldErrorDialog){
        SimpleAlertDialog(
            description = "Please Check Input Fields",
            onDismiss = {
                shouldShouldErrorDialog = false
            },

            )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    val navController = rememberNavController()
  HomeScreen(navController = navController, shareViewModel = hiltViewModel())
}


