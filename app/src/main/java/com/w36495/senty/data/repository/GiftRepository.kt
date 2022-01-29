package com.w36495.senty.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.w36495.senty.data.domain.Gift

class GiftRepository(private val friendKey: String) {

    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private var storage = FirebaseStorage.getInstance()

    private var userId: String = FirebaseAuth.getInstance().currentUser!!.uid

    /**
     * 선물 등록
     */
    fun saveNewGift(gift: Gift) {
        val giftKey = database.push().key!!
        gift.giftKey = giftKey

        // 사진 등록
        val giftImagePath = System.currentTimeMillis().toString()
        storage.reference.child("images/gifts/${giftImagePath}")
            .putFile(Uri.parse(gift.giftImagePath))
            .addOnSuccessListener {
                gift.giftImagePath = "images/gifts/$giftImagePath"
                database.child(userId).child("gifts").child(friendKey).child(giftKey).setValue(gift)
            }
            .addOnFailureListener { }
    }

    /**
     * 선물 목록 조회
     */
    fun getGiftsList(): LiveData<List<Gift>> {
        val giftListLiveData = MutableLiveData<List<Gift>>()
        val giftDatabase = FirebaseDatabase.getInstance().getReference(userId).child("gifts").child(friendKey)

        // 선물 정보
        giftDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val giftList = snapshot.children.map { giftSnapshot ->
                        giftSnapshot.getValue(Gift::class.java)!!
                    }
                    giftListLiveData.postValue(giftList)
                } else {
                    val giftList = emptyList<Gift>()
                    giftListLiveData.postValue(giftList)
                }
            }
            override fun onCancelled(error: DatabaseError) { }
        })

        return giftListLiveData
    }

    /**
     * 선물 수정
     */
    fun updateGift(gift: Gift, oldImagePath: String) {
        val updateGiftImagePath = System.currentTimeMillis().toString()

        storage.reference.child("images/gifts/$updateGiftImagePath")
            .putFile(Uri.parse(gift.giftImagePath))
            .addOnSuccessListener {
                gift.giftImagePath = "images/gifts/$updateGiftImagePath"

                val giftValue = gift.toMap()
                val giftUpdate = hashMapOf<String, Any>(
                    "/$userId/gifts/${friendKey}/${gift.giftKey}/" to giftValue
                )
                database.updateChildren(giftUpdate)
            }
            .addOnFailureListener { }

        // 기존의 image path 삭제
        storage.reference.child(oldImagePath).delete()
            .addOnSuccessListener {

            }
    }

    /**
     * 선물 삭제
     */
    fun deleteGift(gift: Gift) {
        storage.reference.child(gift.giftImagePath!!).delete()
            .addOnSuccessListener {
                database.child(userId).child("gifts").child(friendKey).child(gift.giftKey)
                    .removeValue()
            }
            .addOnFailureListener { }
    }
}