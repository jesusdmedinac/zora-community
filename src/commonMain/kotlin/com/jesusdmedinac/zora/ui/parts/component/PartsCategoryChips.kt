package com.jesusdmedinac.zora.ui.parts.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.parts.model.PartCategory
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Horizontal row of category filter chips for parts catalog.
 */
@Composable
fun PartsCategoryChips(
    selectedCategory: PartCategory,
    onSelectCategory: (PartCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
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
                    .clickable { onSelectCategory(category) }
                    .padding(horizontal = 10.dp, vertical = 6.dp),
            ) {
                Text(
                    text = org.jetbrains.compose.resources.stringResource(category.titleRes),
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 11.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) Color.White else ZoraTokens.OnSurfaceVariant,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PartsCategoryChipsPreview() {
    ZoraTheme {
        PartsCategoryChips(
            selectedCategory = com.jesusdmedinac.zora.ui.parts.model.PartCategory.ALL,
            onSelectCategory = {},
        )
    }
}
