package com.example.eventuree.data.models

import androidx.compose.runtime.Composable

data class BottomNavItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val screen: @Composable () -> Unit,
    val topBar: @Composable () -> Unit
)
