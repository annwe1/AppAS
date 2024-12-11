package br.edu.up.as34123768.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.edu.up.as34123768.ui.home.HomeDestination
import br.edu.up.as34123768.ui.home.HomeScreen
import br.edu.up.as34123768.ui.item.ItemDetailsDestination
import br.edu.up.as34123768.ui.item.ItemDetailsScreen
import br.edu.up.as34123768.ui.item.ItemEditDestination
import br.edu.up.as34123768.ui.item.ItemEditScreen
import br.edu.up.as34123768.ui.item.ItemEntryDestination
import br.edu.up.as34123768.ui.item.ItemEntryScreen

@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController, startDestination = HomeDestination.route, modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(navigateToItemEntry = { navController.navigate(ItemEntryDestination.route) },
                navigateToItemUpdate = {
                    navController.navigate("${ItemDetailsDestination.route}/${it}")
                })
        }
        composable(route = ItemEntryDestination.route) {
            ItemEntryScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
        composable(
            route = ItemDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemDetailsScreen(
                navigateToEditItem =
                {
                    navController.navigate("${ItemEditDestination.route}/$it")
                },
                navigateBack = { navController.navigateUp() })
        }
        composable(
            route = ItemEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemEditScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
    }
}
