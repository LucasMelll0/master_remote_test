@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.master_remote_test.ui.dashboard

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.master_remote_test.R
import com.example.master_remote_test.broadcast.DeviceInfoReceiver
import com.example.master_remote_test.model.BatteryInfo
import com.example.master_remote_test.ui.components.CustomCircularProgressbar
import com.example.master_remote_test.ui.components.DefaultAlertDialog
import com.example.master_remote_test.ui.components.TextWithIcon
import com.example.master_remote_test.ui.theme.Master_remote_testTheme
import com.example.master_remote_test.utils.BatteryHealth
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: DashboardViewModel = koinViewModel(), onClickExit: () -> Unit) {
    var showExitAlertDialog by remember { mutableStateOf(false) }
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
    Scaffold(topBar = { DefaultTopAppBar(onClickExit = { showExitAlertDialog = true }) }) { paddingValues ->
        if (showExitAlertDialog) DefaultAlertDialog(
            title = stringResource(id = R.string.common_dialog_title),
            text = stringResource(id = R.string.auth_exit_confirmation_dialog_text),
            onDismissRequest = { showExitAlertDialog = false },
            onConfirm = { onClickExit() }
        )
        batteryInfo?.let {
            when (LocalConfiguration.current.orientation) {
                ORIENTATION_LANDSCAPE -> {
                    DashboardLandscapeOrientation(
                        batteryInfo = it,
                        airplaneModeIsEnabled = airplaneModeIsEnabled,
                        batterySaverIsEnabled = batterySaverIsEnabled,
                        modifier = Modifier.padding(paddingValues)
                    )
                }

                else -> DashboardPortraitOrientation(
                    it,
                    airplaneModeIsEnabled,
                    batterySaverIsEnabled,
                    modifier = Modifier.padding(paddingValues)
                )

            }
        }
    }
}

@Composable
private fun DashboardPortraitOrientation(
    batteryInfo: BatteryInfo,
    airplaneModeIsEnabled: Boolean,
    batterySaverIsEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        CustomCircularProgressbar(
            progress = batteryInfo.percent.toFloat(),
            text = "${batteryInfo.percent}%",
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.large_padding))
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            BatteryInfoCardPortraitOrientation(
                batteryInfo,
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

@Composable
fun DashboardLandscapeOrientation(
    batteryInfo: BatteryInfo,
    airplaneModeIsEnabled: Boolean,
    batterySaverIsEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CustomCircularProgressbar(
                progress = batteryInfo.percent.toFloat(),
                text = "${batteryInfo.percent}%",
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.large_padding))
                    .weight(1f)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = dimensionResource(id = R.dimen.large_padding))
            ) {
                BatteryInfoCardLandscapeOrientation(
                    batteryInfo,
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
fun BatteryInfoCardPortraitOrientation(
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

@Composable
fun BatteryInfoCardLandscapeOrientation(
    batteryInfo: BatteryInfo,
    modifier: Modifier = Modifier
) {
    Card(shape = MaterialTheme.shapes.medium, modifier = modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.large_padding)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.default_padding))
        ) {
            Text(text = "Status da bateria")
            TextWithIcon(
                icon = painterResource(id = R.drawable.ic_temperature),
                text = "${batteryInfo.temperature}ºC",
            )
            TextWithIcon(
                icon = painterResource(id = R.drawable.ic_health),
                text = "Saúde ${stringResource(id = batteryInfo.health.status())}",
            )

            TextWithIcon(
                icon = painterResource(id = R.drawable.ic_bolt),
                text = "${batteryInfo.voltage}v",
            )
            TextWithIcon(
                icon = painterResource(id = R.drawable.ic_battery_charging),
                text = if (batteryInfo.isCharging) "Carregando" else "desconectado",
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
fun DashboardPortraitOrientationPreview() {
    Master_remote_testTheme {
        val batteryInfo =
            BatteryInfo(
                isCharging = false,
                percent = 77,
                temperature = "38",
                voltage = "3.5",
                health = BatteryHealth.GOOD
            )
        DashboardPortraitOrientation(
            batteryInfo = batteryInfo,
            airplaneModeIsEnabled = true,
            batterySaverIsEnabled = false
        )
    }
}

@Preview(uiMode = ORIENTATION_LANDSCAPE, showSystemUi = true)
@Composable
fun DashboardLandscapeOrientationPreview() {
    Master_remote_testTheme {
        val batteryInfo =
            BatteryInfo(
                isCharging = false,
                percent = 77,
                temperature = "38",
                voltage = "3.5",
                health = BatteryHealth.GOOD
            )
        DashboardLandscapeOrientation(
            batteryInfo = batteryInfo,
            airplaneModeIsEnabled = true,
            batterySaverIsEnabled = false
        )
    }
}

@Preview
@Composable
fun AirplaneModeCardPreview() {
    Master_remote_testTheme {
        AirplaneModeCard(isEnabled = true)
    }
}