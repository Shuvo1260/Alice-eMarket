package org.binaryitplanet.aliceemarket.Utils

import java.io.Serializable

data class NewsUtils(
        val title: String,
        val news: String
): Serializable {
    constructor(): this(
            "",
            ""
    )
}