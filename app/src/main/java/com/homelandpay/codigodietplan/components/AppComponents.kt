package com.homelandpay.codigodietplan.components

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import com.homelandpay.codigodietplan.data.modal.HealthConcernEntity

@Composable
fun GridCellItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    var isSelected by remember {
        mutableStateOf(false)
    }
    val selectedColor = Color(0xFF182944)
    val gradient = Brush.linearGradient(
        colors = listOf(Color.Transparent, Color.Transparent), // Define your gradient colors here
        start = Offset(0f, 0f),
        end = Offset(100f, 100f)
    )

    val mContainerColor = if (isSelected) {
        selectedColor
    } else {
        Color.Transparent
    }

    val textColor = if (isSelected) {
        Color.White
    } else {
        Color.Gray
    }
    OutlinedButton(
        onClick = {
            isSelected = !isSelected
            onClick()
        },
        modifier = modifier
            .background(brush = gradient),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = mContainerColor,
            contentColor = Color.Black
        ),
        border = BorderStroke(1.dp, Color.Gray),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 15.sp
        )
    }
}


@Composable
fun HealthConcernChip(
    healthConcern: HealthConcernEntity.HealthConcern,
    isSelected: Boolean,
    onSelectionChanged: (Boolean) -> Unit
) {

    val backgroundColor = if (isSelected) Color(0xFF182944) else Color.Transparent
    val contentColor = if (isSelected) Color.White else Color.Black
    val borderColor = if (isSelected) Color.Transparent else Color.Gray

    OutlinedButton(
        onClick = { onSelectionChanged(!isSelected) },
        modifier = Modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(50),
        border = BorderStroke(width = 1.dp, color = borderColor)
    ) {
        Text(
            text = healthConcern.name,
            textAlign = TextAlign.Center,

            )
    }
}

@Composable
fun CustomTextButton(
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = {
            onClick()
            // Handle the button click here
        },
        modifier = Modifier.padding(16.dp), // Add padding around the button as needed
        contentPadding = PaddingValues( // Optionally adjust the padding inside the button
            start = 5.dp,
            top = 12.dp,
            end = 20.dp,
            bottom = 12.dp

        )
    ) {
        Text(
            text = text,
            color = Color(0xFFBC3429) // Set the text color as needed
        )
    }
}


@Composable
fun GradientButton(
    modifier: Modifier = Modifier,
    percent: Int,
    text: String,
    onClick: () -> Unit
) {
    // tra 0xFF81C784
    val gradient = Brush.linearGradient(
        colors = listOf(Color(0xFFBC3429), Color(0xFFBC3429)), // Define your gradient colors here
        start = Offset(0f, 0f),
        end = Offset(100f, 100f)
    )

    Button(
        onClick = { onClick() },

        modifier = modifier
            .padding(8.dp)
            .background(
                brush = gradient,
                shape = RoundedCornerShape(percent)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        )
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp
        )
    }
}

@Composable
fun CustomRadioButtonItem(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    // Custom colors for the RadioButton
    val radioButtonColors = RadioButtonDefaults.colors(
        selectedColor = MaterialTheme.colorScheme.primary,
        unselectedColor = Color.Gray,
        disabledSelectedColor = Color.LightGray
    )

    // Creating a Row that behaves like a RadioButton
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .selectable(
                selected = selected,
                onClick = onSelect
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // This Box is the custom 'RadioButton'
        Box(
            modifier = Modifier
                .size(24.dp)
                .border(
                    BorderStroke(
                        2.dp,
                        if (selected) MaterialTheme.colorScheme.primary else Color.Gray
                    ),
                    RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(2.dp))
                )
            }
        }

        Spacer(Modifier.width(16.dp))

        Text(text)

        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Warning",
            modifier = Modifier.size(24.dp)
        )

//        if (selected) {
//
//        }
    }
}


@Composable
fun CustomCheckboxItem(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onTrailingItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
            .clickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Checkbox(
                checked = checked,
                onCheckedChange = null
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = text)
            Spacer(modifier = Modifier.width(12.dp))
        }

        if (text != "None") {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Warning",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onTrailingItemClick(text)
                    }
            )
        }

    }
}



@Composable
fun SimpleAlertDialog(
    description:String,
    onDismiss: () -> Unit,

) {

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
              onDismiss()

            })
            { Text(text = "OK") }
        },
        title = { Text(text = "Validation Fail") },
        text = { Text(text = description) }
    )

}

@Composable
fun TrailingIconAlertDialog(
    title:String,
    onDismiss: () -> Unit,

    ) {

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDismiss()

            })
            { Text(text = "OK") }
        },
        title = { Text(text = title) },
        text = { Text(text = title) }
    )

}



@Preview
@Composable
fun PreviewComponent() {
    GradientButton(percent = 20, text = "Next") {

    }
}