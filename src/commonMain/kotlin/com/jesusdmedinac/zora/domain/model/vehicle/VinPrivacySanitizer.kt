package com.jesusdmedinac.zora.domain.model.vehicle

object VinPrivacySanitizer {
    const val TECHNICAL_PREFIX_LENGTH = 11
    const val FULL_VIN_LENGTH = 17
    private val VIN_REGEX = Regex("^[A-HJ-NPR-Z0-9]{11,17}$", RegexOption.IGNORE_CASE)

    /**
     * Sanitizes a full VIN under strict Zero-Trust privacy rules.
     * Extracts only the first 11 characters (WMI + VDS + Model Year / Plant Code)
     * and guarantees that the last 6 unique production serial numbers are stripped
     * before leaving the local device.
     */
    fun sanitizeToTechnicalPrefix(vin: String): Result<String> {
        val trimmed = vin.trim().uppercase()
        if (trimmed.length < TECHNICAL_PREFIX_LENGTH) {
            return Result.failure(
                IllegalArgumentException("VIN must have at least 11 characters to extract technical prefix")
            )
        }
        if (!VIN_REGEX.matches(trimmed)) {
            return Result.failure(
                IllegalArgumentException("Invalid VIN characters. Standard VINs cannot contain I, O, or Q.")
            )
        }
        val prefix = trimmed.substring(0, TECHNICAL_PREFIX_LENGTH)
        return Result.success(prefix)
    }

    /**
     * Returns true if the provided string is an already-sanitized 11-character technical prefix.
     */
    fun isSanitizedPrefix(prefix: String): Boolean {
        return prefix.length == TECHNICAL_PREFIX_LENGTH && VIN_REGEX.matches(prefix)
    }
}
