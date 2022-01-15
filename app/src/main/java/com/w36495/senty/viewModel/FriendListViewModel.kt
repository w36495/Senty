package com.w36495.senty.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.w36495.senty.data.domain.Friend
import com.w36495.senty.data.repository.FriendRepository

class FriendListViewModel : ViewModel() {

    private val _friendList = MutableLiveData<ArrayList<Friend>>()
    private val friendListItem : ArrayList<Friend> = arrayListOf()
    private val friendRepository = FriendRepository()

    val friendList : LiveData<ArrayList<Friend>> = _friendList

    init {
        friendRepository.initializeDatabaseReference()
    }

    fun addFriendInfo(friend: Friend) {
        friendRepository.writeNewFriend("toby", friend.name, friend.phone)
        friendListItem.add(friend)
        _friendList.value = friendListItem
    }

    fun updateFriendInfo(friend: Friend, position: Int) {
        friendListItem[position] = friend
        _friendList.value = friendListItem
    }

}