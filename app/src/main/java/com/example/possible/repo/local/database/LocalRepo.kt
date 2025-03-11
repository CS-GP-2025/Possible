package com.example.possible.repo.local.database

import com.example.possible.model.Child

interface LocalRepo {
    suspend fun insertChild(child: Child)
    suspend fun updateChild(child: Child)
    suspend fun getAllChildren(): List<Child>
    suspend fun getChildById(id: Int): Child?
    suspend fun deleteChild(child: Child)

    suspend fun updateReadingRate(childId: Int, readingRate: Int)
    suspend fun updateWritingRate(childId: Int, writingRate: Int)
    suspend fun updateReadingDays(childId: Int, readingDays: Int)
    suspend fun updateWritingDays(childId: Int, writingDays: Int)
    suspend fun updateLatestReadingDay(childId: Int, latestReadingDay: String)
    suspend fun updateLatestWritingDay(childId: Int, latestWritingDay: String)

}