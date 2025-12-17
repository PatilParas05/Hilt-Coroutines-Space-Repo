package com.example.spaceexpo.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.spaceexpo.data.model.SpaceObject
import com.example.spaceexpo.data.model.SpaceObjectType

// Define the core color palette for the black theme
val BlackBackground = Color(0xFF000000)
val DarkSurface = Color(0xFF1C1C1C) // For Cards
val MidDarkSurface = Color(0xFF2A2A2A) // For Chips/Image Placeholders
val AccentPurple = Color(0xFF8B5CF6) // Kept the vibrant accent

@Composable
fun SpaceListScreen(
    viewModel: SpaceListViewModel = hiltViewModel(),
    onSpaceObjectClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        BlackBackground, // Pure Black at the top
                        Color(0xFF0D0D0D), // Subtle gradient transition
                        BlackBackground // Pure Black at the bottom
                    )
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Column(
                modifier = Modifier.padding(top = 50.dp, bottom = 22.dp, start = 24.dp, end = 24.dp)
            ) {
                Text(
                    text = "🌌 Space Explorer",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Discover the wonders of the universe",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }

            // Filter Chips
            LazyRow(
                modifier = Modifier.padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChipCustom(
                        selected = selectedFilter == null,
                        onClick = { viewModel.filterByType(null) },
                        label = "All"
                    )
                }
                items(SpaceObjectType.values()) { type ->
                    FilterChipCustom(
                        selected = selectedFilter == type,
                        onClick = { viewModel.filterByType(type) },
                        label = type.name.lowercase().replaceFirstChar { it.uppercase() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Content
            when (val state = uiState) {
                is SpaceUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(
                                color = AccentPurple,
                                strokeWidth = 3.dp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Loading celestial objects...",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
                is SpaceUiState.Success -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.spaceObject) { spaceObject ->
                            SpaceObjectCard(
                                spaceObject = spaceObject,
                                onClick = { onSpaceObjectClick(spaceObject.id) }
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
                is SpaceUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(32.dp)
                        ) {
                            Text(
                                text = "⚠️",
                                fontSize = 48.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Oops! Something went wrong",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = state.message,
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(
                                onClick = { viewModel.loadSpaceObjects() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AccentPurple
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Retry")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterChipCustom(
    selected: Boolean,
    onClick: () -> Unit,
    label: String
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        // Updated colors to match black theme
        color = if (selected) AccentPurple else MidDarkSurface,
        modifier = Modifier.height(36.dp)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                color = Color.White
            )
        }
    }
}

@Composable
fun SpaceObjectCard(
    spaceObject: SpaceObject,
    onClick: () -> Unit
) {
    // Helper function to get a relevant emoji based on the object type
    val fallbackEmoji = when (spaceObject.type) {
        SpaceObjectType.PLANET -> "🪐" // Ringed Planet
        SpaceObjectType.GALAXY -> "🌌" // Milky Way
        SpaceObjectType.STAR -> "⭐"   // Star
        SpaceObjectType.MOON -> "🌕"   // Full Moon
        SpaceObjectType.NEBULA -> "✨" // Sparkles
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            // Updated card color
            containerColor = DarkSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image with gradient overlay and loading states
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    // Updated placeholder background
                    .background(MidDarkSurface)
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(spaceObject.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = spaceObject.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    loading = {
                        // Removed CircularProgressIndicator as requested. Keeping Box to reserve space.
                        Box(
                            modifier = Modifier.fillMaxSize()
                        )
                    },
                    error = {
                        // FIX: Use a highly relevant, visible emoji as the fallback
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MidDarkSurface),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = fallbackEmoji, // Display the object-specific emoji
                                    fontSize = 48.sp // Make it large and prominent
                                )
                                Text(
                                    spaceObject.type.name,
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                )

                // Subtle gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    BlackBackground.copy(alpha = 0.4f) // Black overlay
                                )
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text Content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = spaceObject.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Surface(
                    color = AccentPurple.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = spaceObject.type.name,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccentPurple,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = spaceObject.description,
                    fontSize = 13.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White.copy(alpha = 0.7f),
                    lineHeight = 18.sp
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🌍",
                        fontSize = 12.sp
                    )
                    Text(
                        text = spaceObject.distanceFromEarth,
                        fontSize = 12.sp,
                        color = AccentPurple,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
