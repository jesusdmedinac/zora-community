package com.jesusdmedinac.zora.ui.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.parts.model.CommunityInsightsState
import com.jesusdmedinac.zora.ui.parts.model.PartCategory
import com.jesusdmedinac.zora.ui.parts.model.PartItem
import com.jesusdmedinac.zora.ui.parts.model.VisualBlueprintState
import com.jesusdmedinac.zora.ui.parts.model.defaultPartsCatalog
import com.jesusdmedinac.zora.ui.theme.ZoraTokens

/**
 * Screen / M3 - Contextual Parts Feed, Salvage Yonke Network & 3D Component Engine.
 * Adheres to Feature 03 BDD specifications:
 * 1. Filter by guaranteed fitment & category chips ("All", "Brakes", "Fluids & Filters", "Suspension", "Junkyard Network")
 * 2. Yonke Salvage Yard highlights: "Verified Salvage · Yonke Network", Donor VIN & mileage, savings calculation, distance/grade
 * 3. 3D Cutaway Visual Blueprint with quota counter ("1 of 3 Used" -> "2 of 3 Used") and disclaimer
 * 4. Fleet-wide community insights privacy gate behind Cloud AI Opt-In
 */
@Composable
fun PartsFeedScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    activeVehicle: String = "2018 Honda Civic EX 2.0L K20C2",
    onReviewOptInSettings: (() -> Unit)? = null,
) {
    var selectedCategory by remember { mutableStateOf(PartCategory.ALL) }
    var blueprintState by remember { mutableStateOf(VisualBlueprintState(quotaCount = 1)) }
    var communityState by remember { mutableStateOf(CommunityInsightsState()) }
    var showOptInModal by remember { mutableStateOf(false) }

    val parts = remember { defaultPartsCatalog() }
    val filteredParts = remember(selectedCategory) {
        if (selectedCategory == PartCategory.ALL) parts
        else parts.filter { it.category == selectedCategory }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ZoraTokens.Canvas)
            .padding(vertical = ZoraTokens.SpacingSm),
    ) {
        // --- 1. Top Bar with Fitment Pill ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = ZoraTokens.SpacingMd),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(ZoraTokens.SurfaceContainerHigh)
                    .border(1.dp, ZoraTokens.OutlineVariant, CircleShape)
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "←",
                    color = ZoraTokens.OnSurface,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            // Vehicle Fitment Pill
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(ZoraTokens.SurfaceContainerHigh)
                    .border(1.dp, ZoraTokens.StatusSuccess.copy(alpha = 0.5f), CircleShape)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(ZoraTokens.StatusSuccess)
                    )
                    Text(
                        text = "Fitment Active · Precision Match",
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ZoraTokens.OnSurface,
                    )
                }
            }

            Box(modifier = Modifier.size(36.dp))
        }

        // Subtitle with active vehicle name
        Text(
            text = activeVehicle,
            fontFamily = ZoraTokens.FontHeadline,
            fontSize = 13.sp,
            color = ZoraTokens.Primary,
            modifier = Modifier.padding(horizontal = ZoraTokens.SpacingMd, vertical = 2.dp),
        )

        // --- 2. Category Filter Chips ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = ZoraTokens.SpacingMd, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PartCategory.entries.forEach { category ->
                val isSelected = category == selectedCategory
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) ZoraTokens.PrimaryContainer else ZoraTokens.SurfaceContainerHigh)
                        .border(
                            1.dp,
                            if (isSelected) ZoraTokens.Primary else ZoraTokens.OutlineVariant,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { selectedCategory = category }
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                ) {
                    Text(
                        text = category.displayName,
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else ZoraTokens.OnSurfaceVariant,
                    )
                }
            }
        }

        // --- 3. Scrollable Content ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ZoraTokens.SpacingMd, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Parts List
            filteredParts.forEach { item ->
                PartCard(item = item)
            }

            // ==========================================
            // 4. On-demand 3D Component Visual Blueprint
            // ==========================================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(ZoraTokens.ShapeLg)
                    .background(ZoraTokens.SurfaceContainer)
                    .border(1.dp, ZoraTokens.OutlineVariant, ZoraTokens.ShapeLg)
                    .padding(14.dp),
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "3D COMPONENT VISUAL BLUEPRINT",
                            fontFamily = ZoraTokens.FontBody,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ZoraTokens.Primary,
                        )
                        Text(
                            text = blueprintState.quotaText,
                            fontFamily = ZoraTokens.FontMono,
                            fontSize = 10.sp,
                            color = ZoraTokens.Primary,
                        )
                    }

                    if (blueprintState.isGenerated) {
                        // 3D Cutaway Wireframe container
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .clip(ZoraTokens.ShapeMd)
                                .background(Color(0xFF090A0D))
                                .border(1.dp, ZoraTokens.PrimaryContainer, ZoraTokens.ShapeMd)
                                .padding(12.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                // Locator pin with glowing beacon
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .clip(CircleShape)
                                            .background(ZoraTokens.TertiaryContainer)
                                    )
                                    Text(
                                        text = "📍 PIN: Front-Right Offset Loc",
                                        fontFamily = ZoraTokens.FontMono,
                                        fontSize = 11.sp,
                                        color = ZoraTokens.Tertiary,
                                    )
                                }

                                Text(
                                    text = "[3D Cutaway Wireframe Rendered: Engine Bay]",
                                    fontFamily = ZoraTokens.FontMono,
                                    fontSize = 12.sp,
                                    color = ZoraTokens.OnSurface,
                                )

                                Text(
                                    text = blueprintState.componentTag,
                                    fontFamily = ZoraTokens.FontMono,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ZoraTokens.Primary,
                                )
                            }
                        }
                    } else {
                        // Request button
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(42.dp)
                                .clip(CircleShape)
                                .background(ZoraTokens.SurfaceContainerHigh)
                                .border(1.dp, ZoraTokens.Primary, CircleShape)
                                .clickable {
                                    blueprintState = blueprintState.copy(
                                        isGenerated = true,
                                        quotaCount = 2,
                                    )
                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Request Visual Blueprint for \"DENSO ALTERNATOR\"",
                                fontFamily = ZoraTokens.FontBody,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = ZoraTokens.Primary,
                            )
                        }
                    }

                    // Disclaimer
                    Text(
                        text = blueprintState.disclaimer,
                        fontFamily = ZoraTokens.FontBody,
                        fontStyle = FontStyle.Italic,
                        fontSize = 10.sp,
                        color = ZoraTokens.OnSurfaceVariant,
                        lineHeight = 14.sp,
                    )
                }
            }

            // ==========================================
            // 5. Fleet-wide Community Insights Privacy Gate
            // ==========================================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(ZoraTokens.ShapeLg)
                    .background(ZoraTokens.SurfaceContainer)
                    .border(1.dp, ZoraTokens.OutlineVariant, ZoraTokens.ShapeLg)
                    .padding(14.dp),
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "COMMUNITY INSIGHTS & FLEET RADAR",
                            fontFamily = ZoraTokens.FontBody,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ZoraTokens.Primary,
                        )

                        // Privacy Mode Badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF380062))
                                .padding(horizontal = 8.dp, vertical = 2.dp),
                        ) {
                            Text(
                                text = communityState.sessionStatus,
                                fontFamily = ZoraTokens.FontMono,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = ZoraTokens.Primary,
                            )
                        }
                    }

                    // Blurred / Locked Stats preview
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(ZoraTokens.ShapeMd)
                            .background(ZoraTokens.SurfaceContainerLow)
                            .padding(12.dp),
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Question of the week preview: Failure trend stats [BLURRED]",
                                fontFamily = ZoraTokens.FontMono,
                                fontSize = 11.sp,
                                color = ZoraTokens.Outline,
                            )
                            Text(
                                text = communityState.explanation,
                                fontFamily = ZoraTokens.FontBody,
                                fontSize = 11.sp,
                                color = ZoraTokens.OnSurfaceVariant,
                            )
                        }
                    }

                    // Opt-In CTA
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .clip(CircleShape)
                            .background(ZoraTokens.SurfaceContainerHigh)
                            .border(1.dp, ZoraTokens.OutlineVariant, CircleShape)
                            .clickable {
                                showOptInModal = true
                                onReviewOptInSettings?.invoke()
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = communityState.ctaText,
                            fontFamily = ZoraTokens.FontBody,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = ZoraTokens.OnSurface,
                        )
                    }

                    if (showOptInModal) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(ZoraTokens.ShapeMd)
                                .background(ZoraTokens.SurfaceContainerHighest)
                                .border(1.dp, ZoraTokens.Primary, ZoraTokens.ShapeMd)
                                .padding(10.dp),
                        ) {
                            Text(
                                text = "Privacy Preferences: Cloud AI Opt-In is disabled by default. Your telemetry remains strictly on-device until you explicitly choose to share anonymous diagnostic logs.",
                                fontFamily = ZoraTokens.FontBody,
                                fontSize = 11.sp,
                                color = ZoraTokens.OnSurface,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PartCard(item: PartItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(ZoraTokens.ShapeLg)
            .background(ZoraTokens.SurfaceContainer)
            .border(
                1.dp,
                if (item.isSalvage) ZoraTokens.SecondaryContainer else ZoraTokens.OutlineVariant,
                ZoraTokens.ShapeLg
            )
            .padding(14.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            // Badge & Price Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            if (item.isSalvage) Color(0xFF380062) else Color(0xFF003822)
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                ) {
                    Text(
                        text = item.badge,
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (item.isSalvage) ZoraTokens.Secondary else ZoraTokens.StatusSuccess,
                    )
                }

                // Price
                Text(
                    text = item.price,
                    fontFamily = ZoraTokens.FontMono,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = ZoraTokens.OnSurface,
                )
            }

            // Title
            Text(
                text = item.title,
                fontFamily = ZoraTokens.FontBody,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = ZoraTokens.OnSurface,
            )

            // OEM Interchange if regular part
            if (item.oemInterchange != null) {
                Text(
                    text = "OEM Interchange: ${item.oemInterchange}",
                    fontFamily = ZoraTokens.FontMono,
                    fontSize = 11.sp,
                    color = ZoraTokens.Primary,
                )
            }

            // Salvage specifics if Yonke
            if (item.isSalvage) {
                if (item.donorVinAndMileage != null) {
                    Text(
                        text = item.donorVinAndMileage,
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 11.sp,
                        color = ZoraTokens.Primary,
                    )
                }
                if (item.savingsCalculation != null) {
                    Text(
                        text = item.savingsCalculation,
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ZoraTokens.StatusSuccess,
                    )
                }
                if (item.distanceAndGrade != null) {
                    Text(
                        text = item.distanceAndGrade,
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 11.sp,
                        color = ZoraTokens.OnSurfaceVariant,
                    )
                }
            }
        }
    }
}
