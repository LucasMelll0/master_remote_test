package com.example.master_remote_test.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.BatteryManager.EXTRA_HEALTH
import android.os.BatteryManager.EXTRA_LEVEL
import android.os.BatteryManager.EXTRA_SCALE
import android.os.BatteryManager.EXTRA_TEMPERATURE
import android.os.BatteryManager.EXTRA_VOLTAGE
import android.os.PowerManager
import android.os.PowerManager.ACTION_POWER_SAVE_MODE_CHANGED
import com.example.master_remote_test.model.BatteryInfo
import com.example.master_remote_test.utils.BatteryHealth

class DeviceInfoReceiver(
    val onBatteryInfoChanged: (BatteryInfo) -> Unit,
    val onAirplaneModeChanged: (Boolean) -> Unit,
    val onBatterySaverStatusChanged: (Boolean) -> Unit
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let { deviceStatus ->
            when (deviceStatus.action) {
                Intent.ACTION_BATTERY_CHANGED -> {
                    val status = deviceStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                    val isCharging: Boolean =
                        status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
                    val percent = deviceStatus.let {
                        val level = it.getIntExtra(EXTRA_LEVEL, -1)
                        val scale = it.getIntExtra(EXTRA_SCALE, -1)
                        level * 100 / scale
                    }
                    val temperature = deviceStatus.getIntExtra(EXTRA_TEMPERATURE, -1).let {
                        String.format("%.1f", (it.toFloat() / 10))
                    }
                    val health = deviceStatus.getIntExtra(EXTRA_HEALTH, -1).let {
                        when (it) {
                            BatteryManager.BATTERY_HEALTH_COLD -> BatteryHealth.COLD
                            BatteryManager.BATTERY_HEALTH_DEAD -> BatteryHealth.DEAD
                            BatteryManager.BATTERY_HEALTH_GOOD -> BatteryHealth.GOOD
                            BatteryManager.BATTERY_HEALTH_OVERHEAT -> BatteryHealth.OVERHEAT
                            BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> BatteryHealth.OVER_VOLTAGE
                            BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> BatteryHealth.UNSPECIFIED_FAILURE
                            else -> BatteryHealth.UNKNOWN
                        }
                    }
                    val voltage = deviceStatus.getIntExtra(EXTRA_VOLTAGE, -1).let {
                        String.format("%.1f", (it.toFloat() / 1000))
                    }
                    onBatteryInfoChanged(
                        BatteryInfo(
                            isCharging = isCharging,
                            percent = percent,
                            temperature = temperature,
                            voltage = voltage,
                            health = health
                        )
                    )
                }

                Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                    val isEnabled = deviceStatus.getBooleanExtra("state", false)
                    onAirplaneModeChanged(isEnabled)
                }

                ACTION_POWER_SAVE_MODE_CHANGED -> {
                    context?.let {
                        val powerManager: PowerManager =
                            context.getSystemService(Context.POWER_SERVICE) as PowerManager
                        onBatterySaverStatusChanged(powerManager.isPowerSaveMode)
                    }
                }

                else -> Unit
            }
        }
    }
}