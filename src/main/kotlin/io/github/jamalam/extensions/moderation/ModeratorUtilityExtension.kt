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

package io.github.jamalam.extensions.moderation

import com.kotlindiscord.kord.extensions.checks.hasPermission
import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.*
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.ephemeralSlashCommand
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import dev.kord.common.entity.Permission
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.entity.channel.MessageChannel
import dev.kord.rest.builder.message.EmbedBuilder
import dev.kord.rest.builder.message.create.embed
import io.github.jamalam.util.*

/**
 * @author  Jamalam360
 */
class ModeratorUtilityExtension : Extension() {
    override val name: String = "moderator-utility"

    override suspend fun setup() {
        ephemeralSlashCommand(::ScheduleMessageArgs) {
            name = "schedule"
            description = "Schedule a message to be sent"

            check {
                hasModeratorRole()
            }

            action {
                scheduler.schedule(arguments.delay.seconds.toLong()) {
                    (arguments.channel.asChannel() as MessageChannel).createMessage(arguments.message)
                }

                guild?.getLogChannel()?.createEmbed {
                    info("Message Scheduled")
                    userAuthor(user.asUser())
                    log()
                    now()
                    channelField("Channel", arguments.channel)
                    stringField("Message", arguments.message)
                }

                respond {
                    embed {
                        info("Message scheduled")
                        pinguino()
                        now()
                        success()
                    }
                }
            }
        }

        ephemeralSlashCommand(::EchoArgs) {
            name = "echo"
            description = "Echo a message to a channel, or the current channel is no channel is specified"

            check {
                hasModeratorRole()
            }

            action {
                val channel: MessageChannel = if (arguments.channel == null) {
                    channel.asChannel()
                } else {
                    arguments.channel!!.asChannel() as MessageChannel
                }

                channel.createMessage(arguments.message)

                guild!!.getLogChannel()?.createEmbed {
                    info("Echo command used")
                    userAuthor(user.asUser())
                    now()
                    log()
                    channelField("Channel", channel)
                    stringField("Content", arguments.message)
                }

                respond {
                    embed {
                        info("Message sent")
                        pinguino()
                        now()
                        success()
                    }
                }
            }
        }

        ephemeralSlashCommand(::AskArgs) {
            name = "ask"
            description = "Ask a yes/no question!"

            check {
                hasModeratorRole()
            }

            action {
                val channel: MessageChannel = if (arguments.channel == null) {
                    channel.asChannel()
                } else {
                    arguments.channel!!.asChannel() as MessageChannel
                }

                val message = channel.createEmbed {
                    this.title = arguments.string
                    this.author = EmbedBuilder.Author()
                    this.author!!.name = user.asUser().username
                    this.author!!.icon = user.asUser().avatar!!.url
                }

                message.addReaction(ReactionEmoji.Unicode("\uD83D\uDC4D"))
                message.addReaction(ReactionEmoji.Unicode("\uD83D\uDC4E"))

                guild!!.getLogChannel()?.createEmbed {
                    info("Ask command used")
                    userAuthor(user.asUser())
                    now()
                    log()
                    channelField("Channel", channel)
                    stringField("Content", arguments.string)
                }

                respond {
                    embed {
                        info("Vote created")
                        pinguino()
                        now()
                        success()
                    }
                }
            }
        }

        ephemeralSlashCommand {
            name = "delete-config"
            description = "Delete this servers config from the Pinguino database"

            check {
                hasModeratorRole()
                hasPermission(Permission.Administrator)
            }

            action {
                database.config.deleteConfig(guild!!.id)

                guild!!.getLogChannel()?.createEmbed {
                    info("Config deleted")
                    userAuthor(user.asUser())
                    now()
                    error()
                }

                respond {
                    embed {
                        info("Config deleted")
                        pinguino()
                        now()
                        success()
                    }
                }
            }
        }

        publicSlashCommand {
            name = "leave"
            description = "Make Pinguino leave the server :("

            check {
                hasModeratorRole()
                hasPermission(Permission.Administrator)
            }

            action {
                database.config.deleteConfig(guild!!.id)

                guild!!.getLogChannel()?.createEmbed {
                    info("Goodbye.")
                    userAuthor(user.asUser())
                    now()
                    error()
                }

                respond {
                    embed {
                        info("Pinguino is leaving, goodbye :wave:")
                        pinguino()
                        now()
                        success()
                    }
                }


                guild!!.leave()
            }
        }

        ephemeralSlashCommand(::EmbedCreateArgs) {
            name = "embed"
            description = "Post a customised embed"

            check {
                hasModeratorRole()
            }

            action {
                val channel: MessageChannel = if (arguments.channel == null) {
                    channel.asChannel()
                } else {
                    arguments.channel!!.asChannel() as MessageChannel
                }

                guild!!.getLogChannel()?.createEmbed {
                    info("Embed command used")
                    userAuthor(user.asUser())
                    now()
                    log()
                    channelField("Channel", channel)
                    stringField("Delay", arguments.delay?.toPrettyString())
                    stringField("Title", arguments.title)
                    stringField("Description", arguments.description)
                    stringField("Image", arguments.image)
                    userField("Author", arguments.author)
                }

                if (arguments.delay == null) {
                    channel.createEmbed {
                        title = arguments.title
                        description = arguments.description
                        image = arguments.image

                        author {
                            name = arguments.author?.username
                            icon = arguments.author?.avatar?.url
                        }
                    }

                    respond {
                        embed {
                            info("Embed posted")
                            pinguino()
                            now()
                            success()
                        }
                    }
                } else {
                    scheduler.schedule(arguments.delay!!.seconds.toLong()) {
                        channel.createEmbed {
                            title = arguments.title
                            description = arguments.description
                            image = arguments.image

                            author {
                                name = arguments.author?.username
                                icon = arguments.author?.avatar?.url
                            }
                        }
                    }

                    respond {
                        embed {
                            info("Embed scheduled")
                            pinguino()
                            now()
                            success()
                        }
                    }
                }
            }
        }
    }

    inner class AskArgs : Arguments() {
        val string by string {
            name = "question"
            description = "The question to ask"
        }
        val channel by optionalChannel {
            name = "channel"
            description = "The channel to send the message to, or the current one if unspecified"
        }
    }

    inner class EchoArgs : Arguments() {
        val message by string {
            name = "message"
            description = "The message to be sent"
        }
        val channel by optionalChannel {
            name = "channel"
            description = "The channel to send the message to, or the current one if unspecified"
        }
    }

    inner class EmbedCreateArgs : Arguments() {
        val channel by optionalChannel {
            name = "channel"
            description = "The channel to send the message to, or the current one if unspecified"
        }
        val delay by optionalDuration {
            name = "delay"
            description = "The time until the embed should be sent - optional"
        }
        val title by optionalString {
            name = "title"
            description = "The title of the embed"
        }
        val description by optionalString {
            name = "description"
            description = "The description of the embed"
        }
        val image by optionalString {
            name = "image-url"
            description = "The URL of the image of the embed"
        }
        val author by optionalUser {
            name = "author"
            description = "The author"
        }
    }

    inner class ScheduleMessageArgs : Arguments() {
        val channel by channel {
            name = "channel"
            description = "The channel to send the message to"
        }
        val delay by duration {
            name = "duration"
            description = "The time until the message should be sent"
        }
        val message by string {
            name = "message"
            description = "The message to send"
        }
    }
}