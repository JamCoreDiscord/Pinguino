@file:OptIn(ExperimentalTime::class)

package io.github.jamalam360.extensions.user

import com.kotlindiscord.kord.extensions.DISCORD_BLURPLE
import com.kotlindiscord.kord.extensions.DISCORD_GREEN
import com.kotlindiscord.kord.extensions.components.components
import com.kotlindiscord.kord.extensions.components.publicButton
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.event
import com.kotlindiscord.kord.extensions.types.respond
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.channel.createMessage
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.rest.builder.message.create.allowedMentions
import dev.kord.rest.builder.message.create.embed
import io.github.jamalam360.Modules
import io.github.jamalam360.api.HastebinApi
import io.github.jamalam360.database.entity.ServerConfig
import io.github.jamalam360.util.database
import io.github.jamalam360.util.getLoggingExtension
import io.github.jamalam360.util.isModuleEnabled
import kotlin.time.ExperimentalTime

/**
 * @author  Jamalam360
 */

@OptIn(KordPreview::class)
class FilePasteExtension : Extension() {
    override val name = "file-paste"

    private val hasteBin = HastebinApi()

    override suspend fun setup() {
        event<MessageCreateEvent> {
            check {
                isModuleEnabled(Modules.FilePaste)
            }

            action {
                if (event.member == null || event.message.attachments.isEmpty()) {
                    return@action
                }

                val conf: ServerConfig = database.config.getConfig(event.member!!.guildId)

                event.message.attachments.forEach {
                    if (!it.isImage && (it.url.endsWith(".txt") || it.url.endsWith(".log"))) {
                        event.message.channel.createMessage {
                            allowedMentions {
                                repliedUser = true
                            }

                            messageReference = event.message.id

                            embed {
                                title = "Upload File to Hastebin?"
                                description =
                                    "The attached file can be uploaded to Hastebin for easier viewing. Would you like me to upload it and post a link?"
                                color = DISCORD_BLURPLE
                            }

                            components {
                                publicButton {
                                    label = "Yes"

                                    action {
                                        hasteBin.pasteFromCdn(conf.filePasteConfig.hastebinUrl, it.url)
                                            .let { hastebinApiResponse ->
                                                respond {
                                                    embed {
                                                        title = "File Uploaded to Hastebin"
                                                        url =
                                                            "${conf.filePasteConfig.hastebinUrl}${hastebinApiResponse}"
                                                        color = DISCORD_GREEN
                                                    }
                                                }

                                                bot.getLoggingExtension().logAction(
                                                    "Uploaded File to Hastebin",
                                                    "${conf.filePasteConfig.hastebinUrl}${hastebinApiResponse}",
                                                    user.asUser(),
                                                    guild!!.asGuild()
                                                )
                                            }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
