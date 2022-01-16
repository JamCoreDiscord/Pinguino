package io.github.jamalam360

import com.kotlindiscord.kord.extensions.ExtensibleBot
import com.kotlindiscord.kord.extensions.utils.env
import dev.kord.common.entity.Snowflake
import io.github.jamalam360.database.Database
import io.github.jamalam360.extensions.bot.BotUtilityExtension
import io.github.jamalam360.extensions.moderation.LoggingExtension
import io.github.jamalam360.extensions.moderation.ModerationExtension
import io.github.jamalam360.extensions.moderation.ModuleExtension
import io.github.jamalam360.extensions.moderation.NotificationsExtension
import io.github.jamalam360.extensions.user.*

//region ENV Variables
val PRODUCTION = env("PRODUCTION").toBoolean()
val TEST_SERVER_ID = if (PRODUCTION) {
    Snowflake(0)
} else {
    Snowflake(
        env("TEST_SERVER_ID").toLong()
    )
}
private val TOKEN = if (PRODUCTION) {
    env("TOKEN")
} else {
    env("TEST_BOT_TOKEN")
}
val DBL_TOKEN = env("DBL_TOKEN")
val SENTRY_URL = env("SENTRY_URL")
//endregion

//region Constant Values
const val PINGUINO_PFP = "https://images-ext-2.discordapp.net/external/tM2ezTNgh6TK_9IW5eCGQLtuaarLJfjdRgJ3hmRQ5rs" +
        "/%3Fsize%3D256/https/cdn.discordapp.com/avatars/896758540784500797/507601ac" +
        "31f51ffc334fac125089f7ea.png"

const val VERSION = "v0.4.2"
const val DBL_URL = "https://top.gg/api/bots/896758540784500797/stats"
//endregion

val DATABASE = Database()

suspend fun main() {
    val bot = ExtensibleBot(TOKEN) {
        applicationCommands {
            if (!PRODUCTION) {
                defaultGuild(TEST_SERVER_ID)
            }
        }

        extensions {
            add(::QuoteExtension)
            add(::BotUtilityExtension)
            add(::ModuleExtension)
            add(::LoggingExtension)
            add(::ModerationExtension)
            add(::UtilExtension)
            add(::FunExtension)
            add(::TagExtension)
            add(::NotificationsExtension)
            add(::FilePasteExtension)

            help {
                enableBundledExtension = false
            }

            sentry {
                enable = true
                dsn = SENTRY_URL

                environment = if (PRODUCTION) {
                    "production"
                } else {
                    "testing"
                }

                distribution = VERSION
            }
        }
    }

    bot.start()

}

fun ExtensibleBot.getLoggingExtension(): LoggingExtension = this.extensions["logging"] as LoggingExtension
