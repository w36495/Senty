package com.w36495.senty.viewModel

import android.net.Uri
import androidx.lifecycle.*
import com.w36495.senty.data.domain.Gift
import com.w36495.senty.data.repository.GiftRepository

class GiftViewModel(friendKey: String) : ViewModel() {

    class GiftViewModelFactory(private val friendKey: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(GiftViewModel::class.java)) {
                GiftViewModel(friendKey) as T
            } else {
                throw IllegalArgumentException()
            }
        }
    }

    private val giftRepository = GiftRepository(friendKey)

    val giftList: LiveData<List<Gift>> = giftRepository.getGiftsList()

    /**
     * 선물 등록
     */
    fun addGift(gift: Gift, giftImage: Uri) {
        giftRepository.saveNewGift(gift, giftImage)
    }

    /**
     * 선물 수정
     */
    fun updateGift(gift: Gift) {
        giftRepository.updateGift(gift)
    }

    /**
     * 선물 삭제
     */
    fun removeGift(gift: Gift) {
        giftRepository.deleteGift(gift)
    }
}