package com.example.myapplication.ui

import SettingsScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.myapplication.ui.dashboard.GraphScreen
import com.example.myapplication.ui.home.HomeScreen
import com.example.myapplication.ui.home.HomeViewModel

//import com.example.myapplication.ui.dashboard.DashboardScreen
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val viewModel: HomeViewModel = viewModel() // <-- importante

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = "home") {
                HomeScreen(
                    ingresos = viewModel.ingresos.value,
                    gastos = viewModel.gastos,
                    onAgregarIngreso = { viewModel.agregarIngreso(it) },
                    onAgregarGasto = { nombre, cantidad, descripcion ->
                        viewModel.agregarGasto(nombre, cantidad, descripcion)
                    }
                )
            }

            composable(route = "graph") { GraphScreen() }
            composable(route = "settings") { SettingsScreen() }
        }
    }
}
