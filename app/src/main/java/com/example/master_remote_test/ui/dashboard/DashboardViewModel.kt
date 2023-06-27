package com.example.master_remote_test.ui.dashboard

import androidx.lifecycle.ViewModel
import com.example.master_remote_test.model.BatteryInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class DashboardViewModel : ViewModel() {

    private val _batteryInfo = MutableStateFlow<BatteryInfo?>(null)
    internal val batteryInfo: StateFlow<BatteryInfo?> = _batteryInfo

    private val _airplaneMode = MutableStateFlow(false)
    internal val airplaneMode: StateFlow<Boolean> = _airplaneMode

    private val _batterySaverStatus = MutableStateFlow(false)
    internal val batterySaverStatus: StateFlow<Boolean> = _batterySaverStatus

    fun updateBatteryInfo(batteryInfo: BatteryInfo) {
        _batteryInfo.update { batteryInfo }
    }

    fun updateAirplaneMode(isEnabled: Boolean) {
        _airplaneMode.update { isEnabled }
    }

    fun updateBatterySaverStatus(isEnabled: Boolean) {
        _batterySaverStatus.update { isEnabled }
    }

}