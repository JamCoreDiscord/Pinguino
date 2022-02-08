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

package io.github.jamalam

import com.kotlindiscord.kord.extensions.ExtensibleBot
import dev.kord.rest.builder.message.create.embed
import io.github.jamalam.database.migration.migrate
import io.github.jamalam.extensions.bot.BotUtilityExtension
import io.github.jamalam.extensions.moderation.*
import io.github.jamalam.extensions.user.*
import io.github.jamalam.util.*

suspend fun main() {
    BOOT_TIME // init this field

    val bot = ExtensibleBot(TOKEN) {
        applicationCommands {
            if (!PRODUCTION) {
                defaultGuild(TEST_SERVER_ID)
            }
        }

        errorResponse { message, _ ->
            embed {
                title = "Error"
                description = message
                pinguino()
                error()
                footer {
                    text = "Report bugs at [GitHub](https://github.com/JamCoreDiscord/Pinguino/issues)"
                }
            }
        }

        extensions {
            add(::QuoteExtension)
            add(::BotUtilityExtension)
            add(::ModuleExtension)
            add(::LoggingExtension)
            add(::ModerationExtension)
            add(::ModeratorUtilityExtension)
            add(::UserUtilityExtension)
            add(::FunExtension)
            add(::TagExtension)
            add(::NotificationsExtension)
            add(::FilePasteExtension)
            add(::PhishingExtension)

            help {
                enableBundledExtension = false
            }

            if (PRODUCTION) {
                sentry {
                    enable = true
                    dsn = SENTRY_URL
                    environment = "Production"
                    release = VERSION
                }
            }
        }
    }

    migrate(database.db)
    bot.start()
}