package com.example.examenalumnobreakdown.ui.base.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
@Composable
fun AlertDialogOk(
    title: String = "Información Importante",
    text: String,
    onOk: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /* No se cierra tocando fuera */ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Color(0xFF1565C0),
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Text(
                text = text,
                fontSize = 17.sp,
                lineHeight = 22.sp
            )
        },
        containerColor = Color(0xFFF4F6F8),
        tonalElevation = 8.dp,

        // SOLO BOTÓN OK
        confirmButton = {
            Button(
                onClick = onOk,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1565C0),
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(end = 6.dp, bottom = 6.dp)
            ) {
                Text(
                    text = "Ok",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAlertDialogOk() {
    MaterialTheme {
        AlertDialogOk(
            title = "Aviso",
            text = "Operación realizada correctamente.",
            onOk = {}
        )
    }
}

