package com.example.possible.repo.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.possible.model.Child

import androidx.room.*

@Dao
interface ChildDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChild(child: Child)

    @Query("SELECT * FROM children")
    suspend fun getAllChildren(): List<Child>
    @Query("SELECT * FROM children WHERE id = :id")
    suspend fun getChildById(id: Int): Child?

    @Delete
    suspend fun deleteChild(child: Child)

    @Update
    suspend fun editChild(child: Child)
}
