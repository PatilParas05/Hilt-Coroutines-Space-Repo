package com.example.spaceexpo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.spaceexpo.presentation.detail.SpaceDetailScreen
import com.example.spaceexpo.presentation.list.SpaceListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val darkTheme = isSystemInDarkTheme()

             val colorScheme = when {
                 Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                     if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
                 }
                 darkTheme -> darkColorScheme()
                 else -> lightColorScheme()
             }
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SpaceApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SpaceApp() {
    val navController = rememberNavController()

    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = "list") {
            composable("list") {
                SpaceListScreen(
                    onSpaceObjectClick = { spaceObjectId ->
                        navController.navigate("detail/$spaceObjectId")
                    },
                    animatedVisibilityScope = this@composable,
                    sharedTransitionScope = this@SharedTransitionLayout
                )
            }
            composable(
                "detail/{spaceObjectId}",
                arguments = listOf(navArgument("spaceObjectId") { type = NavType.IntType })
            ) { backStackEntry ->
                val spaceObjectId = backStackEntry.arguments?.getInt("spaceObjectId") ?: 0
                SpaceDetailScreen(
                    spaceObjectId = spaceObjectId,
                    animatedVisibilityScope = this@composable,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
