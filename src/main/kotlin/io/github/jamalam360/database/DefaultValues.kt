@file:Suppress("unused")

package io.github.jamalam360.database

import dev.kord.common.entity.Snowflake
import io.github.jamalam360.database.entity.*
import kotlin.reflect.KClass

/**
 * @author  Jamalam360
 */

fun KClass<ServerConfig>.getDefault(id: Snowflake): ServerConfig {
    return ServerConfig(
        id.value,
        ServerQuotesConfig::class.getDefault(),
        ServerLoggingConfig::class.getDefault(),
        ServerModerationConfig::class.getDefault(),
        ServerTagsConfig::class.getDefault()
    )
}

fun KClass<ServerQuotesConfig>.getDefault(): ServerQuotesConfig {
    return ServerQuotesConfig(
        true,
        null,
    )
}

fun KClass<ServerLoggingConfig>.getDefault(): ServerLoggingConfig {
    return ServerLoggingConfig(
        true,
        null
    )
}

fun KClass<ServerModerationConfig>.getDefault(): ServerModerationConfig {
    return ServerModerationConfig(
        enabled = true,
        moderatorRole = 0,
        mutedRole = 0,

        mutableListOf()
    )
}

fun KClass<ServerTagsConfig>.getDefault(): ServerTagsConfig {
    return ServerTagsConfig(
        HashMap()
    )
}
