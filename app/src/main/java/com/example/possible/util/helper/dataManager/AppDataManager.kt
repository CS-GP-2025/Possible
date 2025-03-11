package com.example.possible.util.helper.dataManager

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.possible.model.User
import com.example.possible.repo.local.SharedPref
import com.example.possible.util.helper.dataFormater.DataFormater
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

object AppDataManager {

    fun saveProfileData(
        context: Context,
        pref: SharedPref,
        image: String,
        name: String,
        email: String,
        password: String,
        expiration: String,
        token: String,
        userId: String,
        role:String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val imagePath = DataFormater.imageUrlToPathInApp(context, image, "profile_image")
            Log.d("ImageProfile",imagePath!!)


            if (imagePath != null) {
                pref.setProfileData(
                    name,
                    email,
                    password,
                    token,
                    userId,
                    expiration,
                    imagePath,
                    role
                )
            } else {
                Log.e("SaveProfile", "فشل تحميل الصورة، لم يتم حفظ البيانات")
            }
        }
    }
    fun imageUriToPath(context: Context, imageUri: String): String? {
         return " "
    }

    fun getRoleForApi(sharedPref: SharedPref):String{
        return if(sharedPref.getRole()=="User"){
            "1"
        } else{
            "2"
        }
    }


    fun getProfileDetails(sharedPref: SharedPref): User {
       return sharedPref.getProfileDetails()
    }

    /*fun viewProfileImage(imageView: ImageView, sharedPref: SharedPref, context: Context) {
        val imagePath = sharedPref.getProfileDetails().imagePath

        if (imagePath.isEmpty()) {
            Log.e("ViewProfile", "مسار الصورة فاضي أو null")
            return
        }

        val file = File(imagePath)
        if (!file.exists()) {
            Log.e("ViewProfile", "الملف غير موجود: $imagePath")
            return
        }

        Glide.with(context)
            .load(file)
            .into(imageView)
    }*/
    fun viewProfileImage(imageView: ImageView, sharedPref: SharedPref, context: Context) {
        val imagePath = sharedPref.getProfileDetails().imagePath

        if (imagePath.isEmpty()) {
            Log.e("ViewProfile", "مسار الصورة فاضي أو null")
            return
        }

        val file = File(imagePath)
        if (!file.exists()) {
            Log.e("ViewProfile", "الملف غير موجود: $imagePath")
            return
        }

        // تحميل الصورة بدون Glide
        val bitmap = BitmapFactory.decodeFile(imagePath)
        imageView.setImageBitmap(bitmap)
    }

    fun saveProfileDataAfterUpdate(
        pref: SharedPref,
        image: String,
        name: String,
        email: String,
        password: String,
        expiration: String,
        token: String,
        userId: String,
        role:String
    ) {

                pref.setProfileData(
                    name,
                    email,
                    password,
                    token,
                    userId,
                    expiration,
                    image,
                    role
                )

        }
    fun deleteProfileFile(context: Context) {
        val file =
            File(context.getExternalFilesDir(null), "Pictures/ProfileImages/profile_image.jpg")
        if (file.exists()) {
            val deleted = file.delete()
            println("تم حذف الملف: $deleted")
        } else {
            println("لم يتم العثور على الملف.")
        }
    }
    }


