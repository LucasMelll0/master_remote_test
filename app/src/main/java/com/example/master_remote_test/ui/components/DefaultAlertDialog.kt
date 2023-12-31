package com.example.master_remote_test.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.master_remote_test.R
import com.example.master_remote_test.ui.theme.Master_remote_testTheme

@Composable
fun DefaultAlertDialog(
    title: String,
    text: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    onConfirm: () -> Unit
) {
    AlertDialog(
        modifier = modifier.fillMaxWidth(),
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(id = R.string.common_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(id = R.string.common_cancel))
            }
        },
        icon = icon,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    )
}

@Preview
@Composable
private fun DefaultAlertDialogPreview() {
    Master_remote_testTheme {
        DefaultAlertDialog(
            title = "teste",
            text = "teste",
            icon = { Icon(imageVector = Icons.Rounded.Delete, contentDescription = null) },
            onDismissRequest = {}) {

        }
    }
}