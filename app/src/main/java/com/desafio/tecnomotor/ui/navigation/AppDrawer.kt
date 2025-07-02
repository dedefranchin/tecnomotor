package com.desafio.tecnomotor.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(
    navController: NavController,
    drawerState: DrawerState,
    currentRoute: String,
    onNavigate: () -> Unit
) {
    val scope = rememberCoroutineScope()

    fun navigate(route: String) {
        onNavigate()
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        scope.launch {
            drawerState.close()
        }
    }

    fun navigateAndResetStack(route: String) {
        onNavigate()
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
        scope.launch {
            drawerState.close()
        }
    }

    ModalDrawerSheet {
        Text("Menu", modifier = Modifier.padding(16.dp))
        NavigationDrawerItem(
            label = { Text(text = "Montadoras") },
            selected = currentRoute == Routes.MONTADORA_LIST || currentRoute == Routes.VEICULO_LIST,
            onClick = { navigateAndResetStack(Routes.MONTADORA_LIST) }
        )
        NavigationDrawerItem(
            label = { Text(text = "Veículos") },
            selected = currentRoute == Routes.ALL_VEICULOS_LIST,
            onClick = { navigate(Routes.ALL_VEICULOS_LIST) }
        )
    }
} 