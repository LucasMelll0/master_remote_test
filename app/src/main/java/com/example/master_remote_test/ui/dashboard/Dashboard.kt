@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.master_remote_test.ui.dashboard

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.PowerManager
import android.os.PowerManager.ACTION_POWER_SAVE_MODE_CHANGED
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.master_remote_test.R
import com.example.master_remote_test.broadcast.DeviceInfoReceiver
import com.example.master_remote_test.model.BatteryInfo
import com.example.master_remote_test.ui.components.CustomCircularProgressbar
import com.example.master_remote_test.ui.components.TextWithIcon
import com.example.master_remote_test.ui.theme.Master_remote_testTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: DashboardViewModel = koinViewModel()) {
    val context = LocalContext.current
    SetupBroadcastReceiver(viewModel)
    viewModel.updateBatterySaverStatus((context.getSystemService(Context.POWER_SERVICE) as PowerManager).isPowerSaveMode)
    viewModel.updateAirplaneMode(
        Settings.System.getInt(
            context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON,
            0
        ) != 0
    )
    val batteryInfo: BatteryInfo? by viewModel.batteryInfo.collectAsStateWithLifecycle()
    val airplaneModeIsEnabled: Boolean by viewModel.airplaneMode.collectAsStateWithLifecycle()
    val batterySaverIsEnabled: Boolean by viewModel.batterySaverStatus.collectAsStateWithLifecycle()
    Scaffold(topBar = { DefaultTopAppBar(onClickExit = {}) }) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            batteryInfo?.let {
                CustomCircularProgressbar(progress = it.percent.toFloat(), text = "${it.percent}%")
                Column(modifier = Modifier.fillMaxWidth()) {
                    BatteryInfoCard(
                        it,
                        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.large_padding))
                    )
                    AirplaneModeCard(
                        isEnabled = airplaneModeIsEnabled, modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.large_padding))
                            .fillMaxWidth()
                    )
                    BatterySaverCard(
                        isEnabled = batterySaverIsEnabled,
                        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.large_padding))
                    )
                }
            }
        }
    }
}

@Composable
private fun SetupBroadcastReceiver(viewModel: DashboardViewModel) {
    val context = LocalContext.current
    val deviceInfoReceiver =
        DeviceInfoReceiver(
            onBatteryInfoChanged = { viewModel.updateBatteryInfo(it) },
            onAirplaneModeChanged = { viewModel.updateAirplaneMode(it) },
            onBatterySaverStatusChanged = { viewModel.updateBatterySaverStatus(it) }
        )
    val filters = IntentFilter(Intent.ACTION_BATTERY_CHANGED).apply {
        addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        addAction(ACTION_POWER_SAVE_MODE_CHANGED)
    }
    context.registerReceiver(deviceInfoReceiver, filters)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DefaultTopAppBar(onClickExit: () -> Unit) {
    TopAppBar(title = { Text(text = "Dashboard") }, actions = {
        IconButton(onClick = onClickExit) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = stringResource(id = R.string.common_exit)
            )
        }
    })
}

@Composable
fun AirplaneModeCard(isEnabled: Boolean, modifier: Modifier = Modifier) {
    Card(shape = MaterialTheme.shapes.medium, modifier = modifier) {
        TextWithIcon(
            icon = painterResource(id = R.drawable.ic_airplane),
            text = stringResource(
                id = R.string.airplane_mode_prefix,
                stringResource(id = if (isEnabled) R.string.common_on else R.string.common_off)
            ),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.large_padding))
                .fillMaxWidth()
        )
    }
}

@Composable
fun BatterySaverCard(isEnabled: Boolean, modifier: Modifier) {
    Card(shape = MaterialTheme.shapes.medium, modifier = modifier) {
        TextWithIcon(
            icon = painterResource(id = R.drawable.ic_battery_saver),
            text = stringResource(
                id = R.string.battery_saver_status_prefix,
                stringResource(id = if (isEnabled) R.string.common_on else R.string.common_off)
            ),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.large_padding))
                .fillMaxWidth()
        )
    }
}

@Composable
fun BatteryInfoCard(
    batteryInfo: BatteryInfo,
    modifier: Modifier = Modifier
) {
    Card(shape = MaterialTheme.shapes.medium, modifier = modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.large_padding)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.default_padding))
        ) {
            Text(text = "Status da bateria")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextWithIcon(
                    icon = painterResource(id = R.drawable.ic_temperature),
                    text = "${batteryInfo.temperature}ºC",
                    modifier = Modifier.weight(1f)
                )
                TextWithIcon(
                    icon = painterResource(id = R.drawable.ic_health),
                    text = "Saúde ${stringResource(id = batteryInfo.health.status())}",
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextWithIcon(
                    icon = painterResource(id = R.drawable.ic_bolt),
                    text = "${batteryInfo.voltage}v",
                    modifier = Modifier.weight(1f)
                )
                TextWithIcon(
                    icon = painterResource(id = R.drawable.ic_battery_charging),
                    text = if (batteryInfo.isCharging) "Carregando" else "desconectado",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
fun DashboardScreenPreview() {
    Master_remote_testTheme {
        val viewModelFactory = object : ViewModelProvider.AndroidViewModelFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DashboardViewModel() as T
            }
        }
        val viewModel: DashboardViewModel = viewModel(factory = viewModelFactory)
        DashboardScreen(viewModel = viewModel)
    }
}

@Preview
@Composable
fun AirplaneModeCardPreview() {
    Master_remote_testTheme {
        AirplaneModeCard(isEnabled = true)
    }
}