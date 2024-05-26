package com.w36495.senty.view.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.w36495.senty.view.screen.gift.GiftAddScreen
import com.w36495.senty.view.screen.gift.GiftCategoryScreen

fun NavGraphBuilder.nestedGiftGraph(navController: NavController) {
    navigation(
        startDestination = GiftNavigationItem.GIFT_ADD.name,
        route = GiftNavigationItem.GIFT.name
    ) {
        composable(GiftNavigationItem.GIFT_ADD.name) {
            GiftAddScreen(
                onPressedBack = {
                    navController.navigateUp()
                },
                onComplete = {
                    navController.navigate(BottomNavigationItem.HOME.name) {
                        popUpTo(BottomNavigationItem.HOME.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(GiftNavigationItem.GIFT_CATEGORY.name) {
            GiftCategoryScreen(
                onPressedBack = { navController.navigateUp() }
            )
        }
    }
}

enum class GiftNavigationItem {
    GIFT, GIFT_ADD, GIFT_CATEGORY,
}