package com.example.master_remote_test.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.master_remote_test.ui.theme.Master_remote_testTheme

@Composable
fun DefaultButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        content = content,
        shape = MaterialTheme.shapes.small
    )
}

@Preview
@Composable
fun DefaultButtonPreview() {
    Master_remote_testTheme {
        DefaultButton(onClick = { }) {

        }
    }
}