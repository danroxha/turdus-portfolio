package com.turdusportfolio.ui.presentation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.TipsAndUpdates
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.turdusportfolio.R
import com.turdusportfolio.ui.components.BottomNavigation
import com.turdusportfolio.ui.components.BottomNavigationItem
import com.turdusportfolio.ui.theme.TurdusDefault

enum class Router(val title: String) {

    HomeScreen("Home"),
    GoalScreen("Goal"),
    AddTransactionScreen("Add"),
    ToolsScreen("Tools"),
    MoreScreen("More");
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouterApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        content = { padding ->
            NavHostContainer(navController = navController, padding = padding)
        },
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            if(currentRoute == Router.GoalScreen.name)
                return@Scaffold

            BottomNavigationBar(navController = navController)
        },
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(
        modifier = Modifier.
            height(TurdusDefault.Container.height)
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val bottomNavItems = listOf(
            BottomNavItem(
                label = stringResource(R.string.home),
                icon = Icons.Filled.Home,
                iconSelected = Icons.Outlined.Home,
                route = Router.HomeScreen.name
            ),
            BottomNavItem(
                label = stringResource(R.string.add),
                icon = Icons.Filled.AddCircle,
                iconSelected = Icons.Outlined.AddCircleOutline,
                route = Router.AddTransactionScreen.name
            ),
            BottomNavItem(
                label = stringResource(R.string.tools),
                icon = Icons.Default.TipsAndUpdates,
                iconSelected = Icons.Outlined.TipsAndUpdates,
                route = Router.ToolsScreen.name
            ),
            BottomNavItem(
                label = stringResource(R.string.more),
                icon = Icons.Filled.MoreHoriz,
                iconSelected = Icons.Outlined.MoreHoriz,
                route = Router.MoreScreen.name
            )
        )

        bottomNavItems.forEach { navItem ->
            BottomNavigationItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    if(currentRoute == navItem.route)
                        return@BottomNavigationItem
                    navController.navigate(route = navItem.route) {
                        restoreState = true
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                            inclusive = false
                        }
                    }
                },
                icon = navItem.icon,
                iconSelected = navItem.iconSelected,
                label = navItem.label,
            )
        }
    }
}

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Router.HomeScreen.name,
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            composable(Router.HomeScreen.name) {
                HomeScreen {
                    navController.navigate(Router.GoalScreen.name)
                }
            }
            composable(Router.AddTransactionScreen.name) {
                AddTransactionScreen()
            }
            composable(Router.MoreScreen.name) {
                MoreScreen()
            }
            composable(Router.GoalScreen.name) {
                GoalScreen { navController.popBackStack() }
            }
            composable(Router.ToolsScreen.name) {
                ToolsScreen()
            }
        }
    )
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val iconSelected: ImageVector = icon,
    val route:String,
)



