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

package io.github.jamalam.pinguino.database.collection

import com.mongodb.client.MongoDatabase
import dev.kord.common.entity.Snowflake
import io.github.jamalam.pinguino.database.entity.AnnouncementSubscriber
import io.github.jamalam.pinguino.database.tryOperationUntilSuccess
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

/**
 * @author  Jamalam360
 */
@Suppress("RemoveExplicitTypeArguments")
class AnnouncementSubscriberCollection(db: MongoDatabase) :
    CachedDatabaseCollection<Snowflake, AnnouncementSubscriber>(db.getCollection<AnnouncementSubscriber>()) {

    fun addSubscriber(channelId: Snowflake) {
        val subscriber = AnnouncementSubscriber(channelId.value.toLong())
        tryOperationUntilSuccess { collection.insertOne(subscriber) }
        cache[channelId] = subscriber
    }

    fun removeSubscriber(channelId: Snowflake) {
        tryOperationUntilSuccess { collection.deleteOne(AnnouncementSubscriber::channel eq channelId.value.toLong()) }
        cache.remove(channelId)
    }

    fun getSubscribers(): List<Snowflake> {
        val subscribers = mutableListOf<Snowflake>()
        tryOperationUntilSuccess { collection.find().forEach { subscribers.add(Snowflake(it.channel)) } }
        return subscribers
    }
}
