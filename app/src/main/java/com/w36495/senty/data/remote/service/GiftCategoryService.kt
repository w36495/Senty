package com.w36495.senty.data.remote.service

import com.w36495.senty.data.domain.FriendKeyDTO
import com.w36495.senty.view.entity.gift.GiftCategory
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface GiftCategoryService {
    @GET("giftCategories/{userId}.json")
    suspend fun getCategories(
        @Path("userId") userId: String
    ): Response<ResponseBody>

    @POST("giftCategories/{userId}.json")
    suspend fun initGiftCategory(
        @Path("userId") userId: String,
        @Body category: GiftCategory
    ): Response<ResponseBody>

    @PATCH("giftCategories/{userId}/{categoryKey}.json")
    suspend fun patchCategoryKey(
        @Path("userId") userId: String,
        @Path("categoryKey") categoryKey: String,
        @Body body: FriendKeyDTO
    ): Response<ResponseBody>
}