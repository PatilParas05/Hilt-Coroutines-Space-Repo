package com.example.spaceexpo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.spaceexpo.presentation.detail.SpaceDetailScreen
import com.example.spaceexpo.presentation.list.SpaceListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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

@Composable
fun SpaceApp() {
    var selectedSpaceObjectId by remember { mutableStateOf<Int?>(null) }

    if (selectedSpaceObjectId == null) {
        SpaceListScreen(
            onSpaceObjectClick = { id ->
                selectedSpaceObjectId = id
            }
        )
    } else {
        SpaceDetailScreen(
            spaceObjectId = selectedSpaceObjectId!!,
            onBackClick = { selectedSpaceObjectId = null }
        )
    }
}