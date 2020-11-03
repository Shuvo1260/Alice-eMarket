package org.binaryitplanet.aliceemarket.Utils

import java.io.Serializable

data class ProfileUtils(
        val phoneNumber: String?,
        val location: String?
): Serializable {
    constructor(
    ): this(
            null,
            null
    )
}