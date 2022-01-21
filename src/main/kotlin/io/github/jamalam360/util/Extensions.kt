/*
 * Copyright (C) 2022 Jamalam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.jamalam360.util

import com.kotlindiscord.kord.extensions.*
import dev.kord.common.entity.ChannelType
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.GuildBehavior
import dev.kord.core.behavior.channel.threads.ThreadChannelBehavior
import dev.kord.core.entity.User
import dev.kord.core.entity.channel.MessageChannel
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.jamalam360.database.entity.ServerConfig
import io.github.jamalam360.extensions.moderation.LoggingExtension
import kotlinx.datetime.Clock

/**
 * @author  Jamalam360
 */

fun ExtensibleBot.getLoggingExtension(): LoggingExtension = this.extensions["logging"] as LoggingExtension

suspend fun GuildBehavior.getLogChannel(): MessageChannel? {
    val conf = database.config.getConfig(this.id)

    if (conf.loggingConfig.enabled && conf.loggingConfig.channel != null) {
        val channel = this.getChannel(Snowflake(conf.loggingConfig.channel!!))

        if (channel.type == ChannelType.GuildText) {
            return channel as MessageChannel
        }
    }

    return null
}

fun GuildBehavior.getConfig(): ServerConfig = database.config.getConfig(this.id)

fun ThreadChannelBehavior.save(save: Boolean = true) {
    if (save) {
        database.savedThreads.setSave(this.id, false)
    } else {
        database.savedThreads.setSave(this.id, false)
    }
}

fun EmbedBuilder.userAuthor(user: User) {
    author {
        name = user.username
        icon = user.avatar!!.url
    }
}

fun EmbedBuilder.pinguino() {
    author {
        name = "Pinguino"
        icon = PINGUINO_PFP
    }
}

fun EmbedBuilder.info(message: String) {
    title = message
}

fun EmbedBuilder.image(url: String?) {
    image = url
}

fun EmbedBuilder.userField(name: String, user: User) {
    field {
        this.name = name
        this.value = user.username
    }
}

fun EmbedBuilder.channelField(name: String, channel: MessageChannel) {
    field {
        this.name = name
        this.value = channel.mention
    }
}

fun EmbedBuilder.stringField(name: String, value: String) {
    field {
        this.name = name
        this.value = value
    }
}

fun EmbedBuilder.now() {
    timestamp = Clock.System.now()
}

fun EmbedBuilder.log() {
    color = DISCORD_BLURPLE
}

fun EmbedBuilder.success() {
    color = DISCORD_GREEN
}

fun EmbedBuilder.error() {
    color = DISCORD_RED
}