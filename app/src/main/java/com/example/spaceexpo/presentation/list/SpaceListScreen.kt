package com.example.spaceexpo.presentation.list

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.spaceexpo.data.model.SpaceObject
import com.example.spaceexpo.data.model.SpaceObjectType
import com.example.spaceexpo.ui.modifiers.glassmorphism

// Premium Cosmic Color Palette
val CosmicBlack = Color(0xFF000000)
val DeepSpace = Color(0xFF0A0A0C)
val NebulaPurple = Color(0xFF8B5CF6)
val StarlightWhite = Color(0xFFF8F9FA)
val BorderGlass = Color(0xFFFFFFFF).copy(alpha = 0.15f)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SpaceListScreen(
    viewModel: SpaceListViewModel = hiltViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onSpaceObjectClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CosmicBlack)
    ) {
        // Subtle background glow
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(x = (-100).dp, y = (-100).dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(NebulaPurple.copy(alpha = 0.12f), Color.Transparent)
                    )
                )
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // Refined Header
            Row(
                modifier = Modifier
                    .padding(top = 64.dp, start = 24.dp, end = 24.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = NebulaPurple.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, NebulaPurple.copy(alpha = 0.4f))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("🌌", fontSize = 22.sp)
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Space Explorer",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = StarlightWhite,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "Discover the wonders of the universe",
                        fontSize = 14.sp,
                        color = StarlightWhite.copy(alpha = 0.5f)
                    )
                }
            }

            // High-end Filter Chips
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
            ) {
                item {
                    PremiumGlassChip(
                        text = "All",
                        selected = selectedFilter == null,
                        onClick = { viewModel.filterByType(null) }
                    )
                }
                items(SpaceObjectType.values()) { type ->
                    PremiumGlassChip(
                        text = type.name.lowercase().replaceFirstChar { it.uppercase() },
                        selected = selectedFilter == type,
                        onClick = { viewModel.filterByType(type) }
                    )
                }
            }

            // Content Section
            when (val state = uiState) {
                is SpaceUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = NebulaPurple, strokeWidth = 2.dp)
                    }
                }
                is SpaceUiState.Success -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 40.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(state.spaceObject) { spaceObject ->
                            CosmicPlanetCard(
                                planet = spaceObject,
                                sharedTransitionScope = sharedTransitionScope,
                                animatedVisibilityScope = animatedVisibilityScope,
                                onClick = { onSpaceObjectClick(spaceObject.id) }
                            )
                        }
                    }
                }
                is SpaceUiState.Error -> {
                    ErrorState(message = state.message, onRetry = { viewModel.loadSpaceObjects() })
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CosmicPlanetCard(
    planet: SpaceObject,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClick: () -> Unit
) {
    with(sharedTransitionScope) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(DeepSpace)
                .border(1.dp, BorderGlass, RoundedCornerShape(28.dp))
                .clickable(onClick = onClick)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // High-quality image container
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(Color.White.copy(alpha = 0.05f))
                ) {
                    AsyncImage(
                        model = planet.imageUrl,
                        contentDescription = planet.name,
                        modifier = Modifier
                            .fillMaxSize()
                            .sharedElement(
                                rememberSharedContentState(key = "image-${planet.id}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            ),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = planet.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = StarlightWhite,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        color = NebulaPurple.copy(alpha = 0.15f),
                        shape = CircleShape,
                        border = BorderStroke(0.5.dp, NebulaPurple.copy(alpha = 0.3f))
                    ) {
                        Text(
                            text = planet.type.name,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = NebulaPurple,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                            letterSpacing = 1.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = planet.description,
                        fontSize = 13.sp,
                        color = StarlightWhite.copy(alpha = 0.5f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun PremiumGlassChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Box(
        modifier = Modifier
            .height(42.dp)
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        // Glassy Background layer
        Box(
            modifier = Modifier
                .matchParentSize()
                .glassmorphism(blurRadius = 20f, opacity = 0f)
                .background(
                    if (selected) NebulaPurple else Color.White.copy(alpha = 0.05f),
                    CircleShape
                )
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.White.copy(alpha = if (selected) 0.5f else 0.2f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 22.dp),
            color = if (selected) Color.White else StarlightWhite.copy(alpha = 0.7f),
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
fun ErrorState(message: String, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text("🔭", fontSize = 64.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Connection Interrupted",
                color = StarlightWhite,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                color = StarlightWhite.copy(alpha = 0.6f),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = NebulaPurple),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Retry Connection", fontWeight = FontWeight.Bold)
            }
        }
    }
}
