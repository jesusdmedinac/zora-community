package com.jesusdmedinac.zora.ai

import ai.koog.agents.core.tools.ToolDescriptor
import ai.koog.prompt.Prompt
import ai.koog.prompt.dsl.ModerationResult
import ai.koog.prompt.executor.clients.LLMClient
import ai.koog.prompt.llm.LLMProvider
import ai.koog.prompt.llm.LLModel
import ai.koog.prompt.message.Message
import ai.koog.prompt.message.ResponseMetaInfo
import ai.koog.utils.time.KoogClock
import android.content.Context
import android.util.Log
import com.google.mlkit.genai.common.FeatureStatus
import com.google.mlkit.genai.prompt.Generation
import com.google.mlkit.genai.prompt.GenerationConfig
import com.google.mlkit.genai.prompt.GenerativeModel
import com.google.mlkit.genai.prompt.ModelConfig
import com.google.mlkit.genai.prompt.ModelPreference
import com.google.mlkit.genai.prompt.ModelReleaseStage
import com.google.mlkit.genai.prompt.generationConfig
import com.google.mlkit.genai.prompt.modelConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * On-device Gemini Nano [LLMClient] implementation for JetBrains Koog.
 * Connects Koog agents directly to Google AICore via Google ML Kit GenAI Prompt API on Android.
 */
class GeminiNanoKoogClient(
    private val context: Context,
) : LLMClient() {

    private var activeModel: GenerativeModel? = null

    init {
        // Pre-warm Gemini Nano in background to minimize first inference latency
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val model = resolveAvailableGenerativeModel()
                if (model != null) {
                    val status = model.checkStatus()
                    Log.d(TAG, "Gemini Nano initial checkStatus: $status")
                    if (status == FeatureStatus.AVAILABLE) {
                        model.warmup()
                        Log.d(TAG, "Gemini Nano warmed up successfully on-device")
                    } else if (status == FeatureStatus.DOWNLOADABLE) {
                        Log.i(TAG, "Gemini Nano is downloadable. Triggering download...")
                        model.download().collect { downloadStatus ->
                            Log.d(TAG, "Gemini Nano download: $downloadStatus")
                        }
                    }
                }
            } catch (e: Throwable) {
                Log.w(TAG, "Gemini Nano pre-warm check skipped: ${e.message}")
            }
        }
    }

    private suspend fun resolveAvailableGenerativeModel(): GenerativeModel? {
        activeModel?.let { return it }

        // Candidate configurations to attempt against on-device AICore
        val configs = listOf(
            // Candidate 1: Gemini Nano Fast Preview (Standard on Samsung Galaxy S24 Ultra AICore)
            generationConfig {
                modelConfig = modelConfig {
                    preference = ModelPreference.FAST
                    releaseStage = ModelReleaseStage.PREVIEW
                }
            },
            // Candidate 2: Gemini Nano Full Preview
            generationConfig {
                modelConfig = modelConfig {
                    preference = ModelPreference.FULL
                    releaseStage = ModelReleaseStage.PREVIEW
                }
            },
            // Candidate 3: Gemini Nano Fast Stable
            generationConfig {
                modelConfig = modelConfig {
                    preference = ModelPreference.FAST
                    releaseStage = ModelReleaseStage.STABLE
                }
            },
            // Candidate 4: Default System Client
            null,
        )

        for (cfg in configs) {
            try {
                val model = if (cfg != null) Generation.getClient(cfg) else Generation.getClient()
                val status = model.checkStatus()
                Log.d(TAG, "Testing ModelConfig: status=$status for cfg=$cfg")
                if (status == FeatureStatus.AVAILABLE || status == FeatureStatus.DOWNLOADABLE || status == FeatureStatus.DOWNLOADING) {
                    Log.i(TAG, "Successfully resolved active Gemini Nano model (status=$status)")
                    activeModel = model
                    return model
                }
            } catch (e: Throwable) {
                Log.d(TAG, "ModelConfig candidate skipped: ${e.message}")
            }
        }

        return null
    }

    override fun llmProvider(): LLMProvider = LLMProvider.Google

    override suspend fun execute(
        prompt: Prompt,
        model: LLModel,
        tools: List<ToolDescriptor>,
    ): Message.Assistant = withContext(Dispatchers.Default) {
        val generativeModel = resolveAvailableGenerativeModel()
            ?: throw IllegalStateException("Gemini Nano unavailable on device (no compatible AICore feature configuration found)")

        val status = try {
            generativeModel.checkStatus()
        } catch (e: Throwable) {
            Log.w(TAG, "Gemini Nano checkStatus error: ${e.message}")
            FeatureStatus.UNAVAILABLE
        }

        if (status != FeatureStatus.AVAILABLE) {
            Log.w(TAG, "Gemini Nano unavailable on device (status=$status). Falling back to local heuristic.")
            throw IllegalStateException("Gemini Nano unavailable on-device (status: $status)")
        }

        val systemInstructionText = prompt.messages
            .filterIsInstance<Message.System>()
            .joinToString("\n") { it.textContent() }

        val userText = prompt.messages
            .filterIsInstance<Message.User>()
            .joinToString("\n") { it.textContent() }
            .ifBlank { prompt.messages.lastOrNull()?.textContent().orEmpty() }

        Log.d(TAG, "Executing prompt on Gemini Nano (userQuery='$userText')")

        val combinedPrompt = if (systemInstructionText.isNotBlank()) {
            "$systemInstructionText\n\nDriver: $userText"
        } else {
            userText
        }

        val response = generativeModel.generateContent(combinedPrompt)
        val responseText = response.candidates.firstOrNull()?.text.orEmpty()
        Log.d(TAG, "Gemini Nano inference succeeded: ${responseText.take(100)}...")

        Message.Assistant(
            content = responseText,
            metaInfo = ResponseMetaInfo.create(clock = KoogClock.System),
        )
    }

    override suspend fun moderate(prompt: Prompt, model: LLModel): ModerationResult {
        return ModerationResult(isHarmful = false, categories = emptyMap())
    }

    override fun close() {
        try {
            activeModel?.close()
        } catch (e: Throwable) {
            Log.w(TAG, "Failed closing GenerativeModel: ${e.message}")
        }
    }

    companion object {
        private const val TAG = "GeminiNanoKoogClient"
    }
}
