package com.example.master_remote_test.utils

import androidx.annotation.StringRes
import com.example.master_remote_test.R

enum class BatteryHealth {
    COLD,
    DEAD,
    GOOD,
    OVERHEAT,
    OVER_VOLTAGE,
    UNKNOWN,
    UNSPECIFIED_FAILURE;

    @StringRes
    fun status(): Int {
        return when (this) {
            COLD -> R.string.battery_status_cold
            DEAD -> R.string.battery_status_dead
            GOOD -> R.string.battery_status_good
            OVERHEAT -> R.string.battery_status_overheat
            OVER_VOLTAGE -> R.string.battery_status_over_voltage
            else -> R.string.battery_status_UNKNOW
        }
    }
}