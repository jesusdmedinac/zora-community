package com.jesusdmedinac.zora.ui.home.sdui

import com.jesusdmedinac.jsontocompose.model.ComposeAction
import com.jesusdmedinac.jsontocompose.model.ComposeDocument
import com.jesusdmedinac.jsontocompose.model.ComposeType
import com.jesusdmedinac.zora.domain.ai.SpecialistType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ZoraSduiTemplatesTest {

    private val json = Json { ignoreUnknownKeys = true; prettyPrint = true }

    @Test
    fun `buildSpecialistTeaserDocument creates valid ComposeDocument with custom action`() {
        val document = ZoraSduiTemplates.buildSpecialistTeaserDocument(
            type = SpecialistType.MECHANIC_SHOP,
            warningNotice = "La vibración al frenar requiere inspección física especializada.",
            teaserMessage = "La asistencia con mecánicos certificados estará disponible próximamente en Zora Pro.",
        )

        assertEquals(ComposeType.Card, document.root.type)
        assertEquals(1, document.actions.size)

        val actionList = document.actions.values.first()
        assertEquals(1, actionList.size)

        val customAction = actionList.first() as ComposeAction.Custom
        assertEquals(ZoraSduiTemplates.ACTION_OPEN_PAYWALL, customAction.customType)
        assertEquals("mechanic_shop", customAction.params["source"]?.jsonPrimitive?.content)

        // Verify JSON roundtrip
        val serializedJson = json.encodeToString(document)
        assertTrue(serializedJson.isNotEmpty())

        val deserializedDocument = json.decodeFromString<ComposeDocument>(serializedJson)
        assertEquals(ComposeType.Card, deserializedDocument.root.type)
        assertNotNull(deserializedDocument.actions.values.firstOrNull())
    }

    @Test
    fun `buildConfirmMemoryDocument creates valid ComposeDocument with confirm and dismiss actions`() {
        val memory = "Aceite sintético 0W-20 cambiado a los 112,000 km"
        val document = ZoraSduiTemplates.buildConfirmMemoryDocument(
            proposedMemory = memory,
        )

        assertEquals(ComposeType.Card, document.root.type)
        assertEquals(2, document.actions.size)

        val confirmActions = document.actions["on_confirm_memory_event"]
        assertNotNull(confirmActions)
        val confirmCustom = confirmActions.first() as ComposeAction.Custom
        assertEquals(ZoraSduiTemplates.ACTION_CONFIRM_MEMORY, confirmCustom.customType)
        assertEquals(memory, confirmCustom.params["memory"]?.jsonPrimitive?.content)

        val dismissActions = document.actions["on_dismiss_memory_event"]
        assertNotNull(dismissActions)
        val dismissCustom = dismissActions.first() as ComposeAction.Custom
        assertEquals(ZoraSduiTemplates.ACTION_DISMISS_MEMORY, dismissCustom.customType)
        assertEquals(memory, dismissCustom.params["memory"]?.jsonPrimitive?.content)

        // Verify JSON roundtrip
        val serializedJson = json.encodeToString(document)
        assertTrue(serializedJson.isNotEmpty())

        val deserializedDocument = json.decodeFromString<ComposeDocument>(serializedJson)
        assertEquals(2, deserializedDocument.actions.size)
    }
}
