package com.w36495.senty.data.remote.service

import com.w36495.senty.data.domain.FriendEntity
import com.w36495.senty.data.domain.FriendKeyDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface FriendService {
    @GET("friends/{userId}.json")
    suspend fun getFriends(
        @Path("userId") userId: String,
    ): Response<ResponseBody>

    @POST("friends/{userId}.json")
    suspend fun insertFriend(
        @Path("userId") userId: String,
        @Body friend: FriendEntity
    ): Response<ResponseBody>

    @PATCH("friends/{userId}/{friendId}.json")
    suspend fun patchFriendKey(
        @Path("userId") userId: String,
        @Path("friendId") friendId: String,
        @Body body: FriendKeyDTO
    ): Response<ResponseBody>
}