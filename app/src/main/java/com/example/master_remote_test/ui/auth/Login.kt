package com.example.master_remote_test.ui.auth

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.master_remote_test.R
import com.example.master_remote_test.utils.UserCredentials
import com.example.master_remote_test.ui.components.DefaultButton
import com.example.master_remote_test.ui.components.DefaultPasswordTextField
import com.example.master_remote_test.ui.components.DefaultTextField
import com.example.master_remote_test.ui.theme.Master_remote_testTheme

@Composable
fun LoginScreen(onClickLogin: (UserCredentials) -> Unit) {
    val context = LocalContext.current
    var user: String by rememberSaveable { mutableStateOf("") }
    var password: String by rememberSaveable { mutableStateOf("") }
    var userHasError: Boolean by remember { mutableStateOf(false) }
    val userErrorMessage: @Composable () -> String = {
        if (user.isEmpty()) {
            stringResource(
                id = R.string.common_text_field_error_message
            )
        } else {
            ""
        }
    }
    val passwordErrorMessage: @Composable () -> String = {
        if (password.isEmpty()) {
            stringResource(id = R.string.common_text_field_error_message)
        } else {
            ""
        }
    }
    var passwordHasError: Boolean by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.default_padding)
            ),
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.extra_large_padding),
                    vertical = dimensionResource(id = R.dimen.large_padding)
                )
                .align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = R.drawable.master_logo),
                contentDescription = null,
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.default_padding))
            )
            DefaultTextField(
                singleLine = true,
                value = user,
                onValueChange = { user = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(id = R.string.common_user)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Person, contentDescription = null
                    )
                },
                isError = userHasError,
                errorMessage = userErrorMessage(),
            )
            DefaultPasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it.trim() },
                label = { Text(text = stringResource(id = R.string.common_password)) },
                leadingIcon = { Icon(imageVector = Icons.Rounded.Lock, contentDescription = null) },
                isError = passwordHasError,
                errorMessage = passwordErrorMessage()
            )
            DefaultButton(onClick = {
                userHasError = user.isEmpty() || user.trim() != "admin"
                passwordHasError = password.isEmpty() || password.trim() != "admin"
                if (!userHasError && !passwordHasError) {
                    val userCredentials = UserCredentials(email = user, password = password)
                    onClickLogin(userCredentials)
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.auth_invalid_credentials_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }, modifier = Modifier.fillMaxWidth()) {
                Icon(painter = painterResource(id = R.drawable.ic_key), contentDescription = null)
                Spacer(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.default_padding)))
                Text(
                    text = stringResource(id = R.string.common_login),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.default_padding))
                )
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
fun LoginScreenPreview() {
    Master_remote_testTheme {
        LoginScreen {}
    }
}