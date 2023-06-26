package com.example.master_remote_test.ui.auth

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.master_remote_test.R
import com.example.master_remote_test.ui.theme.Master_remote_testTheme

@Composable
fun AuthenticationScreen(onClickLogin: () -> Unit, onClickRegister: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Image(
            painter = painterResource(id = R.drawable.master_logo),
            contentDescription = "logo",
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.large_padding))
        )
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.large_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.default_padding))
        ) {
            Button(onClick = onClickLogin, modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(id = R.string.common_login))
            }
            Button(onClick = onClickRegister, modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(id = R.string.common_register))
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
fun AuthenticationScreenPreview() {
    Master_remote_testTheme {
        AuthenticationScreen(onClickLogin = {}, onClickRegister = {})
    }
}