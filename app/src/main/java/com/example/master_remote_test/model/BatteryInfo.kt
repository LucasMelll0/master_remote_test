package com.example.master_remote_test.model

import com.example.master_remote_test.utils.BatteryHealth

data class BatteryInfo(
    val isCharging: Boolean,
    val percent: Int,
    val temperature: String,
    val voltage: String,
    val health: BatteryHealth
)