package com.example.possible.repo.local.database

import android.content.Context
import com.example.possible.model.Child
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalRepoImp(context: Context):LocalRepo {
    private val db = AppDatabase.getDatabase(context)
    override suspend fun insertChild(child: Child) {
        withContext(Dispatchers.IO){
            db.childDao().insertChild(child)

        }
    }

    override suspend fun updateChild(child: Child) {
        withContext(Dispatchers.IO){
            db.childDao().editChild(child)
        }
    }

    override suspend fun getAllChildren(): List<Child> =
        withContext(Dispatchers.IO){
            db.childDao().getAllChildren()

        }


    override suspend fun getChildById(id: Int): Child =
        withContext(Dispatchers.IO) {
            db.childDao().getChildById(id)!!
        }


    override suspend fun deleteChild(child: Child) {
        withContext(Dispatchers.IO){
            db.childDao().deleteChild(child)

        }
    }
}
