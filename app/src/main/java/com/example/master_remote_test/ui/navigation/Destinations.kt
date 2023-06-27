package com.example.master_remote_test.ui.navigation

interface Destination {
    val route: String
}

object Login : Destination {
    override val route: String = "login"
}

object Dashboard : Destination {
    override val route: String = "dashboard"
}