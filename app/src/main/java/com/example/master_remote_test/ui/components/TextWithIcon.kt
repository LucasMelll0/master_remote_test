package com.example.master_remote_test.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.master_remote_test.R

@Composable
fun TextWithIcon(
    icon: Painter,
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.default_padding)
        ),
        modifier = modifier
    ) {
        Icon(painter = icon, contentDescription = null)
        Text(text = text, style = style)
    }
}

@Preview
@Composable
fun TextWithIconPreview() {
    TextWithIcon(icon = painterResource(id = R.drawable.ic_key), text = "Chave")
}