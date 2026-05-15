package com.example.examenalumnobreakdown.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.examenalumnobreakdown.ui.list.ListBreakDownScreen
import com.example.examenalumnobreakdown.ui.add.AddBreakDownScreen
import com.example.examenalumnobreakdown.ui.city.ListCityScreen
import com.example.examenalumnobreakdown.ui.city.AddCityScreen

object Routes {
    const val LIST_BREAKDOWN = "list_breakdown"
    const val ADD_BREAKDOWN = "add_breakdown"
    const val LIST_CITY = "list_city"
    const val ADD_CITY = "add_city"
}

@Composable
fun NavHostScreen(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Routes.LIST_BREAKDOWN,
        modifier = modifier
    ) {
        composable(Routes.LIST_BREAKDOWN) {
            ListBreakDownScreen(
                modifier = Modifier,
                onAdd = { navController.navigate(Routes.ADD_BREAKDOWN) },
                onEdit = { breakdown -> 
                    navController.navigate("${Routes.ADD_BREAKDOWN}?id=${breakdown.id}") 
                },
                onNavigateToCities = { navController.navigate(Routes.LIST_CITY) }
            )
        }
        
        composable(
            route = "${Routes.ADD_BREAKDOWN}?id={id}",
            arguments = listOf(navArgument("id") { 
                type = NavType.IntType
                defaultValue = -1 
            })
        ) {
            AddBreakDownScreen(
                modifier = Modifier,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.LIST_CITY) {
            ListCityScreen(
                onBack = { navController.popBackStack() },
                onAdd = { navController.navigate(Routes.ADD_CITY) },
                onEdit = { city -> 
                    navController.navigate("${Routes.ADD_CITY}?id=${city.id}") 
                }
            )
        }

        composable(
            route = "${Routes.ADD_CITY}?id={id}",
            arguments = listOf(navArgument("id") { 
                type = NavType.IntType
                defaultValue = -1 
            })
        ) {
            AddCityScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}