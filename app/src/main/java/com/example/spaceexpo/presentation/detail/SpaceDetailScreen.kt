package com.example.spaceexpo.presentation.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext

@Composable
fun SpaceDetailScreen(
    spaceObjectId: Int,
    viewModel: SpaceDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val spaceObject by viewModel.spaceObject.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Intercepts the back button press and calls onBackClick
    BackHandler(onBack = onBackClick)

    LaunchedEffect(spaceObjectId) {
        viewModel.loadSpaceObject(spaceObjectId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFF00D9FF),
                        strokeWidth = 3.dp
                    )
                    Text(
                        text = "Loading details...",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            spaceObject?.let { obj ->
                // This Column contains all the content that will scroll
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Hero Image Section
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(450.dp)
                    ) {
                        // Main Image with loading and error states
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(obj.imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = obj.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            loading = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color(0xFF1A1A1A)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        color = Color(0xFF00D9FF),
                                        strokeWidth = 3.dp
                                    )
                                }
                            },
                            error = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color(0xFF1A1A1A)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("🌌", fontSize = 72.sp)
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            "Image Loading Failed",
                                            color = Color.White.copy(alpha = 0.7f),
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        )

                        // Dark overlay at bottom
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.7f),
                                            Color.Black
                                        )
                                    )
                                )
                        )

                        // Title Section at Bottom
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(24.dp)
                        ) {
                            // Type Badge
                            Surface(
                                color = Color(0xFF00D9FF),
                                shape = RoundedCornerShape(10.dp),
                                shadowElevation = 4.dp
                            ) {
                                Text(
                                    text = obj.type.name,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                                    letterSpacing = 1.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Name
                            Text(
                                text = obj.name,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                letterSpacing = 1.2.sp
                            )
                        }
                    }

                    // Content Cards
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        // About Card
                        InfoCard(title = "✨ About") {
                            Text(
                                text = obj.description,
                                fontSize = 13.sp,
                                color = Color.White.copy(alpha = 0.9f),
                                lineHeight = 5.sp
                            )
                        }

                        // Statistics Card
                        InfoCard(title = "📊 Statistics") {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                StatItem(
                                    icon = "🌍",
                                    label = "Distance from Earth",
                                    value = obj.distanceFromEarth
                                )

                                Divider(
                                    color = Color.White.copy(alpha = 0.1f),
                                    thickness = 1.dp
                                )

                                StatItem(
                                    icon = "📅",
                                    label = "Discovery Year",
                                    value = if (obj.discoveryYear < 0) {
                                        "Ancient Times"
                                    } else {
                                        obj.discoveryYear.toString()
                                    }
                                )
                            }
                        }

                        // Interesting Facts Card
                        InfoCard(title = "💫 Fascinating Facts") {
                            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                obj.interestingFacts.forEachIndexed { index, fact ->
                                    FactItem(number = index + 1, fact = fact)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                // Back button - Moved here, outside the scrollable Column
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .padding(top = 48.dp, start = 20.dp) // Adjusted padding for status bar
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.4f))
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun InfoCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00D9FF),
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
fun StatItem(icon: String, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = icon,
                fontSize = 15.sp
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium
            )
        }
        Text(
            text = value,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun FactItem(number: Int, fact: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Number Badge
        Surface(
            color = Color(0xFF00D9FF).copy(alpha = 0.25f),
            shape = CircleShape,
            modifier = Modifier.size(32.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = number.toString(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00D9FF)
                )
            }
        }

        // Fact Text
        Text(
            text = fact,
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.9f),
            lineHeight = 14.sp,
            modifier = Modifier.weight(1f)
        )
    }
}
