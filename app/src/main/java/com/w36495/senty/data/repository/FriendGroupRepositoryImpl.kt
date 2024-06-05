package com.w36495.senty.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.w36495.senty.data.domain.FriendGroupEntity
import com.w36495.senty.data.remote.service.FriendGroupService
import com.w36495.senty.domain.repository.FriendGroupRepository
import com.w36495.senty.view.entity.FriendGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class FriendGroupRepositoryImpl @Inject constructor(
    private val friendGroupService: FriendGroupService
) : FriendGroupRepository {
    private var userId: String = FirebaseAuth.getInstance().currentUser!!.uid

    override fun getFriendGroups(): Flow<List<FriendGroup>> = flow {
        val result = friendGroupService.getFriendGroups(userId)
        val friendGroups = mutableListOf<FriendGroup>()

        if (result.isSuccessful) {
            if (result.headers()["Content-length"]?.toInt() != 4) {
                result.body()?.let {
                    val responseJson = Json.parseToJsonElement(it.string())

                    responseJson.jsonObject.mapKeys { (key, jsonElement) ->
                        val group = Json.decodeFromJsonElement<FriendGroupEntity>(jsonElement).toDomainModel()
                            .apply { setId(key) }
                        friendGroups.add(group)
                    }
                }
            }
        } else throw IllegalArgumentException("Failed to get friend groups(${result.errorBody().toString()})")

        emit(friendGroups)
    }

    override suspend fun insertFriendGroup(friendGroupEntity: FriendGroupEntity): Boolean {
        val result = friendGroupService.insertFriendGroup(userId, friendGroupEntity)

        return result.isSuccessful
    }

    override suspend fun patchFriendGroup(friendKey: String, friendGroupEntity: FriendGroupEntity): Response<ResponseBody> {
        return friendGroupService.patchFriendGroup(userId, friendKey, friendGroupEntity)
    }

    override suspend fun deleteFriendGroup(friendGroupKey: String): Boolean {
        val result = friendGroupService.deleteFriendGroup(userId, friendGroupKey)

        if (result.isSuccessful) {
            if (result.headers()["Content-length"]?.toInt() == 4) return true
        }

        return false
    }
}