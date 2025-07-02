package com.desafio.tecnomotor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.desafio.tecnomotor.ui.navigation.AppDrawer
import com.desafio.tecnomotor.ui.navigation.AppNavigation
import com.desafio.tecnomotor.ui.navigation.Routes
import com.desafio.tecnomotor.ui.theme.TecnomotorTheme
import com.desafio.tecnomotor.ui.viewmodel.MainUiState
import com.desafio.tecnomotor.ui.viewmodel.MainViewModel
import com.desafio.tecnomotor.util.NetworkStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.desafio.tecnomotor.ui.components.NetworkStatusBadge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.hilt.navigation.compose.hiltViewModel
import com.desafio.tecnomotor.ui.viewmodel.AllVeiculosViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TecnomotorTheme {
                val uiState by viewModel.uiState.collectAsState()
                val networkStatus by viewModel.networkStatus.collectAsState()

                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val allVeiculosViewModel: AllVeiculosViewModel = hiltViewModel()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val startDestination = (uiState as? MainUiState.Success)?.startDestination

                val showDrawer = uiState !is MainUiState.Loading &&
                        (currentRoute ?: startDestination) != Routes.SYNC

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        AppDrawer(
                            navController = navController,
                            drawerState = drawerState,
                            currentRoute = currentRoute ?: startDestination ?: Routes.MONTADORA_LIST,
                            onNavigate = {
                                if (currentRoute == Routes.ALL_VEICULOS_LIST) {
                                    allVeiculosViewModel.onMontadoraSelected(null)
                                }
                            }
                        )
                    },
                    gesturesEnabled = showDrawer
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "Tecnomotor") },
                                navigationIcon = {
                                    if (showDrawer) {
                                        IconButton(onClick = {
                                            scope.launch {
                                                drawerState.apply {
                                                    if (isClosed) open() else close()
                                                }
                                            }
                                        }) {
                                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                                        }
                                    }
                                },
                                actions = {
                                    NetworkStatusBadge(networkStatus = networkStatus)
                                    Spacer(modifier = Modifier.width(16.dp))
                                }
                            )
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            when (val state = uiState) {
                                is MainUiState.Loading -> {
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                }

                                is MainUiState.Success -> {
                                    AppNavigation(
                                        navController = navController,
                                        startDestination = state.startDestination
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}