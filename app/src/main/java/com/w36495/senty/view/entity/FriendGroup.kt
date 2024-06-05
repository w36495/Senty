package com.w36495.senty.view.entity

import android.graphics.Color
import com.w36495.senty.data.domain.FriendGroupEntity
import com.w36495.senty.util.DateUtil
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class FriendGroup(
    val name: String,
    val color: String = DEFAULT_COLOR
) {
    var id = ""
        private set

    fun setId(id: String) {
        this.id = id
    }

    fun toDataEntity() = FriendGroupEntity(
        name = name,
        color = color,
    )

    fun getIntTypeColor(): Int {
        val formatColor = StringBuilder().append("#").append(this.color).toString()
        return Color.parseColor(formatColor)
    }

    companion object {
        private const val DEFAULT_COLOR = "D9D9D9"
        val emptyFriendGroup = FriendGroup(name = "")

        fun encodeToJson(group: FriendGroup): String = Json.encodeToString(group)
        fun decodeToObject(jsonGroup: String): FriendGroup = Json.decodeFromString(jsonGroup)
    }
}