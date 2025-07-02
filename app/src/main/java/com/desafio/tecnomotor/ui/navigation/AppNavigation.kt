package com.desafio.tecnomotor.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.desafio.tecnomotor.ui.screens.AllVeiculosScreen
import com.desafio.tecnomotor.ui.screens.MontadoraListScreen
import com.desafio.tecnomotor.ui.screens.SyncScreen
import com.desafio.tecnomotor.ui.screens.VeiculoListScreen

object Routes {
    const val SYNC = "sync"
    const val MONTADORA_LIST = "montadora_list"
    const val ALL_VEICULOS_LIST = "all_veiculos_list"
    const val VEICULO_LIST = "veiculo_list/{montadoraId}"

    fun veiculoList(montadoraId: Int) = "veiculo_list/$montadoraId"
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.SYNC) {
            SyncScreen(onSyncSuccess = {
                navController.navigate(Routes.MONTADORA_LIST) {
                    popUpTo(Routes.SYNC) { inclusive = true }
                }
            })
        }
        composable(Routes.MONTADORA_LIST) {
            MontadoraListScreen(
                onMontadoraClick = { montadoraId ->
                    navController.navigate(Routes.veiculoList(montadoraId))
                }
            )
        }
        composable(Routes.ALL_VEICULOS_LIST) {
            AllVeiculosScreen()
        }
        composable(
            route = Routes.VEICULO_LIST,
            arguments = listOf(navArgument("montadoraId") { type = NavType.IntType })
        ) {
            VeiculoListScreen(navController = navController)
        }
    }
}
