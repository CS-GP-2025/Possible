package com.example.possible.repo.local.database

import com.example.possible.model.Child

interface LocalRepo {
    suspend fun insertChild(child: Child)
    suspend fun updateChild(child: Child)
    suspend fun getAllChildren(): List<Child>
    suspend fun getChildById(id: Int): Child?
    suspend fun deleteChild(child: Child)

}