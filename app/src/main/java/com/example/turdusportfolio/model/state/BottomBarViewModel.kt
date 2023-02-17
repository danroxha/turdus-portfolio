package com.example.turdusportfolio.model.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import com.example.turdusportfolio.ui.components.MenuItem
import com.example.turdusportfolio.ui.presentation.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BottomBarViewModel : ViewModel() {

    private val _mainBottomBarUIState = MutableStateFlow(listOf<MenuItem>())
    val mainBottomBarUIState = _mainBottomBarUIState.asStateFlow()

    init {

        val bottomMainBar = listOf(
            MenuItem(
                label = "Home",
                index = MainBarBottomIndex.Home.index,
                icon = Icons.Default.Home,
                selected = true,
                perform = {}
            ),
            MenuItem(
                label = "Transaction",
                index = MainBarBottomIndex.Transaction.index,
                icon = Icons.Default.ShoppingCart,
                perform = {}
            ),
            MenuItem(
                label = "Add",
                index = MainBarBottomIndex.Add.index,
                icon = Icons.Default.AddCircle,
                perform = {
                    it.navigate(route = Router.AddTransactionScreen.name)
                }
            ),
            MenuItem(
                label = "More",
                index = MainBarBottomIndex.More.index,
                icon = Icons.Default.MoreHoriz,
                perform = {

                }
            ),
        )

        _mainBottomBarUIState.update { bottomMainBar }
    }


    fun selectBottomBarItem(presentation: MainBarBottomIndex) {
        println("select bottom" + presentation.name)
        _mainBottomBarUIState.update { menuBar ->
            menuBar.map {
                it.copy(selected = it.index == presentation.index)
            }
        }
    }
}

enum class MainBarBottomIndex(val index: Int) {
    Home(0),
    Transaction(1),
    Add(2),
    More(3);

    companion object {
        fun fromIndex(index: Int) = MainBarBottomIndex.values().first { it.index == index }
    }
}