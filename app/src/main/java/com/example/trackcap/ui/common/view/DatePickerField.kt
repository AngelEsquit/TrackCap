package com.example.trackcap.ui.common.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.Calendar

@SuppressLint("DefaultLocale")
@Composable
fun DatePickerField(
    label: String,
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateState = remember { mutableStateOf(selectedDate) }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val date = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            dateState.value = date
            onDateSelected(date)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Row (verticalAlignment = Alignment.CenterVertically) {
        TextField(
            value = dateState.value,
            onValueChange = {},
            label = { Text(label) },
            enabled = false,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .weight(1f)
        )
        Button(
            onClick = { datePickerDialog.show() },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Box (modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                Text(text = "Seleccionar fecha")
            }
        }
    }
}