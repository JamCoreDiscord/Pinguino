package io.github.jamalam360.pinguino.config.types.nested

import kotlinx.serialization.Serializable

/**
 * @author  Jamalam
 */

@Serializable
data class BotAdministrationConfig(
    val admins: List<Long>
)
