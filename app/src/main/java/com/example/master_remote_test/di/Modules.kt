package com.example.master_remote_test.di

import com.example.master_remote_test.ui.dashboard.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        DashboardViewModel()
    }
}