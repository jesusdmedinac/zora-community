package com.jesusdmedinac.zora.ui.paywall.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.paywall.model.SubscriptionBilling
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.stringResource
import zora_community.generated.resources.Res
import zora_community.generated.resources.plan_annual
import zora_community.generated.resources.plan_annual_price
import zora_community.generated.resources.plan_annual_savings
import zora_community.generated.resources.plan_monthly
import zora_community.generated.resources.plan_monthly_price
import zora_community.generated.resources.subscription_title
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Radio selector for Annual vs Monthly subscription tiers.
 */
@Composable
fun SubscriptionPlansSelector(
    selectedBilling: SubscriptionBilling,
    onSelectBilling: (SubscriptionBilling) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = stringResource(Res.string.subscription_title),
            fontFamily = ZoraTokens.FontBody,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = ZoraTokens.Primary,
        )

        // Option 1: Annual Membership (Default)
        val isAnnual = selectedBilling == SubscriptionBilling.ANNUAL
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(ZoraTokens.ShapeMd)
                .background(if (isAnnual) ZoraTokens.SurfaceContainerHigh else ZoraTokens.SurfaceContainerLow)
                .border(
                    1.dp,
                    if (isAnnual) ZoraTokens.Primary else ZoraTokens.OutlineVariant,
                    ZoraTokens.ShapeMd
                )
                .clickable { onSelectBilling(SubscriptionBilling.ANNUAL) }
                .padding(14.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = stringResource(Res.string.plan_annual),
                            fontFamily = ZoraTokens.FontBody,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = ZoraTokens.OnSurface,
                        )
                        // Best value badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(ZoraTokens.TertiaryContainer)
                                .padding(horizontal = 6.dp, vertical = 2.dp),
                        ) {
                            Text(
                                text = stringResource(Res.string.plan_annual_savings),
                                fontFamily = ZoraTokens.FontMono,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                            )
                        }
                    }
                    Text(
                        text = stringResource(Res.string.plan_annual_price),
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 12.sp,
                        color = ZoraTokens.Primary,
                    )
                }

                // Radio Button
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .border(
                            2.dp,
                            if (isAnnual) ZoraTokens.Primary else ZoraTokens.OutlineVariant,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    if (isAnnual) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(ZoraTokens.Primary)
                        )
                    }
                }
            }
        }

        // Option 2: Monthly Membership
        val isMonthly = selectedBilling == SubscriptionBilling.MONTHLY
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(ZoraTokens.ShapeMd)
                .background(if (isMonthly) ZoraTokens.SurfaceContainerHigh else ZoraTokens.SurfaceContainerLow)
                .border(
                    1.dp,
                    if (isMonthly) ZoraTokens.Primary else ZoraTokens.OutlineVariant,
                    ZoraTokens.ShapeMd
                )
                .clickable { onSelectBilling(SubscriptionBilling.MONTHLY) }
                .padding(14.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = stringResource(Res.string.plan_monthly),
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = ZoraTokens.OnSurface,
                    )
                    Text(
                        text = stringResource(Res.string.plan_monthly_price),
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 12.sp,
                        color = ZoraTokens.OnSurfaceVariant,
                    )
                }

                // Radio Button
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .border(
                            2.dp,
                            if (isMonthly) ZoraTokens.Primary else ZoraTokens.OutlineVariant,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    if (isMonthly) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(ZoraTokens.Primary)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SubscriptionPlansSelectorPreview() {
    ZoraTheme {
        SubscriptionPlansSelector(
            selectedBilling = com.jesusdmedinac.zora.ui.paywall.model.SubscriptionBilling.ANNUAL,
            onSelectBilling = {},
        )
    }
}
