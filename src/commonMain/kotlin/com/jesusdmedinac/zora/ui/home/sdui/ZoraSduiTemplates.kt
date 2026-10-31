package com.jesusdmedinac.zora.ui.home.sdui

import com.jesusdmedinac.jsontocompose.model.ComposeAction
import com.jesusdmedinac.jsontocompose.model.ComposeDocument
import com.jesusdmedinac.jsontocompose.model.ComposeModifier
import com.jesusdmedinac.jsontocompose.model.ComposeNode
import com.jesusdmedinac.jsontocompose.model.ComposeType
import com.jesusdmedinac.jsontocompose.model.NodeProperties
import com.jesusdmedinac.zora.domain.ai.SpecialistType
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/**
 * SDUI Templates for generative UI in the Copilot Chat using json-to-compose.
 */
object ZoraSduiTemplates {

    const val ACTION_OPEN_PAYWALL = "open_paywall"
    const val ACTION_CONFIRM_MEMORY = "confirm_memory"
    const val ACTION_DISMISS_MEMORY = "dismiss_memory"

    /**
     * Builds a specialist referral card with an advisory notice and a Zora Pro teaser.
     */
    fun buildSpecialistTeaserDocument(
        type: SpecialistType,
        warningNotice: String,
        teaserMessage: String,
    ): ComposeDocument {
        val badgeTitle = when (type) {
            SpecialistType.MECHANIC_SHOP -> "AVISO DE SEGURIDAD MECÁNICA"
            SpecialistType.SALVAGE_YARD -> "CATÁLOGO HIPOTÉTICO DE YONKE"
            SpecialistType.GAS_STATION -> "CENTRO DE SERVICIO Y COMBUSTIBLE"
        }

        val eventName = "on_explore_pro_${type.name.lowercase()}"

        val contentColumn = ComposeNode(
            type = ComposeType.Column,
            composeModifier = ComposeModifier(
                operations = listOf(
                    ComposeModifier.Operation.FillMaxWidth,
                    ComposeModifier.Operation.Padding(12),
                ),
            ),
            properties = NodeProperties.ColumnProps(
                children = listOf(
                    ComposeNode(
                        type = ComposeType.Text,
                        properties = NodeProperties.TextProps(
                            text = badgeTitle,
                            fontSize = 11.0,
                            fontWeight = "Bold",
                            color = "#F59E0B",
                        ),
                    ),
                    ComposeNode(
                        type = ComposeType.Spacer,
                        composeModifier = ComposeModifier(
                            operations = listOf(ComposeModifier.Operation.Height(6)),
                        ),
                    ),
                    ComposeNode(
                        type = ComposeType.Text,
                        properties = NodeProperties.TextProps(
                            text = warningNotice,
                            fontSize = 13.0,
                            fontWeight = "Normal",
                            color = "#E3E2E6",
                        ),
                    ),
                    ComposeNode(
                        type = ComposeType.Spacer,
                        composeModifier = ComposeModifier(
                            operations = listOf(ComposeModifier.Operation.Height(8)),
                        ),
                    ),
                    ComposeNode(
                        type = ComposeType.Text,
                        properties = NodeProperties.TextProps(
                            text = teaserMessage,
                            fontSize = 12.0,
                            fontWeight = "Normal",
                            color = "#A3AAC4",
                        ),
                    ),
                    ComposeNode(
                        type = ComposeType.Spacer,
                        composeModifier = ComposeModifier(
                            operations = listOf(ComposeModifier.Operation.Height(12)),
                        ),
                    ),
                    ComposeNode(
                        type = ComposeType.Button,
                        composeModifier = ComposeModifier(
                            operations = listOf(ComposeModifier.Operation.FillMaxWidth),
                        ),
                        properties = NodeProperties.ButtonProps(
                            onClickEventName = eventName,
                            containerColor = "#894CFF",
                            child = ComposeNode(
                                type = ComposeType.Text,
                                properties = NodeProperties.TextProps(
                                    text = "Explorar Zora Pro",
                                    fontWeight = "Bold",
                                    fontSize = 13.0,
                                    color = "#FFFFFF",
                                ),
                            ),
                        ),
                    ),
                ),
            ),
        )

        val rootCard = ComposeNode(
            type = ComposeType.Card,
            composeModifier = ComposeModifier(
                operations = listOf(ComposeModifier.Operation.FillMaxWidth),
            ),
            properties = NodeProperties.CardProps(
                containerColor = "#1B1B1F",
                borderColor = "#4A4455",
                borderWidth = 1,
                cornerRadius = 12,
                child = contentColumn,
            ),
        )

        return ComposeDocument(
            actions = mapOf(
                eventName to listOf(
                    ComposeAction.Custom(
                        customType = ACTION_OPEN_PAYWALL,
                        params = buildJsonObject {
                            put("source", type.name.lowercase())
                        },
                    ),
                ),
            ),
            root = rootCard,
        )
    }

    /**
     * Builds a confirmation card for persisting a newly learned vehicle memory in the Virtual Garage.
     */
    fun buildConfirmMemoryDocument(
        proposedMemory: String,
    ): ComposeDocument {
        val confirmEvent = "on_confirm_memory_event"
        val dismissEvent = "on_dismiss_memory_event"

        val contentColumn = ComposeNode(
            type = ComposeType.Column,
            composeModifier = ComposeModifier(
                operations = listOf(
                    ComposeModifier.Operation.FillMaxWidth,
                    ComposeModifier.Operation.Padding(12),
                ),
            ),
            properties = NodeProperties.ColumnProps(
                children = listOf(
                    ComposeNode(
                        type = ComposeType.Text,
                        properties = NodeProperties.TextProps(
                            text = "BÓVEDA DE MEMORIA DEL GARAJE",
                            fontSize = 11.0,
                            fontWeight = "Bold",
                            color = "#D1BCFF",
                        ),
                    ),
                    ComposeNode(
                        type = ComposeType.Spacer,
                        composeModifier = ComposeModifier(
                            operations = listOf(ComposeModifier.Operation.Height(6)),
                        ),
                    ),
                    ComposeNode(
                        type = ComposeType.Text,
                        properties = NodeProperties.TextProps(
                            text = "¿Deseas recordar este detalle en tu Garaje Virtual?",
                            fontSize = 13.0,
                            fontWeight = "Bold",
                            color = "#E3E2E6",
                        ),
                    ),
                    ComposeNode(
                        type = ComposeType.Spacer,
                        composeModifier = ComposeModifier(
                            operations = listOf(ComposeModifier.Operation.Height(4)),
                        ),
                    ),
                    ComposeNode(
                        type = ComposeType.Text,
                        properties = NodeProperties.TextProps(
                            text = "\"$proposedMemory\"",
                            fontSize = 12.0,
                            fontWeight = "Normal",
                            color = "#A3AAC4",
                        ),
                    ),
                    ComposeNode(
                        type = ComposeType.Spacer,
                        composeModifier = ComposeModifier(
                            operations = listOf(ComposeModifier.Operation.Height(12)),
                        ),
                    ),
                    ComposeNode(
                        type = ComposeType.Row,
                        composeModifier = ComposeModifier(
                            operations = listOf(ComposeModifier.Operation.FillMaxWidth),
                        ),
                        properties = NodeProperties.RowProps(
                            children = listOf(
                                ComposeNode(
                                    type = ComposeType.Button,
                                    properties = NodeProperties.ButtonProps(
                                        onClickEventName = confirmEvent,
                                        containerColor = "#894CFF",
                                        child = ComposeNode(
                                            type = ComposeType.Text,
                                            properties = NodeProperties.TextProps(
                                                text = "Guardar en Garage",
                                                fontSize = 12.0,
                                                fontWeight = "Bold",
                                                color = "#FFFFFF",
                                            ),
                                        ),
                                    ),
                                ),
                                ComposeNode(
                                    type = ComposeType.Spacer,
                                    composeModifier = ComposeModifier(
                                        operations = listOf(ComposeModifier.Operation.Width(8)),
                                    ),
                                ),
                                ComposeNode(
                                    type = ComposeType.Button,
                                    properties = NodeProperties.ButtonProps(
                                        onClickEventName = dismissEvent,
                                        containerColor = "#25262B",
                                        child = ComposeNode(
                                            type = ComposeType.Text,
                                            properties = NodeProperties.TextProps(
                                                text = "Descartar",
                                                fontSize = 12.0,
                                                fontWeight = "Normal",
                                                color = "#A3AAC4",
                                            ),
                                        ),
                                    ),
                                ),
                            ),
                        ),
                    ),
                ),
            ),
        )

        val rootCard = ComposeNode(
            type = ComposeType.Card,
            composeModifier = ComposeModifier(
                operations = listOf(ComposeModifier.Operation.FillMaxWidth),
            ),
            properties = NodeProperties.CardProps(
                containerColor = "#171A21",
                borderColor = "#894CFF",
                borderWidth = 1,
                cornerRadius = 12,
                child = contentColumn,
            ),
        )

        return ComposeDocument(
            actions = mapOf(
                confirmEvent to listOf(
                    ComposeAction.Custom(
                        customType = ACTION_CONFIRM_MEMORY,
                        params = buildJsonObject {
                            put("memory", proposedMemory)
                        },
                    ),
                ),
                dismissEvent to listOf(
                    ComposeAction.Custom(
                        customType = ACTION_DISMISS_MEMORY,
                        params = buildJsonObject {
                            put("memory", proposedMemory)
                        },
                    ),
                ),
            ),
            root = rootCard,
        )
    }
}
