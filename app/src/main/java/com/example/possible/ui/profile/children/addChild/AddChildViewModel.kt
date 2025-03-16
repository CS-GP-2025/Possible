package com.example.possible.ui.profile.children.addChild
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.possible.model.Child
import com.example.possible.repo.local.SharedPref
import com.example.possible.repo.local.database.LocalRepoImp
import com.example.possible.repo.remote.RetrofitBuilder
import com.example.possible.util.helper.dataFormater.DataFormater
import com.example.possible.util.helper.dataManager.AppDataManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddChildViewModel : ViewModel() {

    private val _child = MutableLiveData<Child>()
    val child: LiveData<Child> = _child

    fun addChild(
        onStart: () -> Unit,
        name: String,
        age: Int,
        gender: Int,
        imageUriString: String,
        parentUserName: String,
        difficulty: String,
        disease: String,
        pref: SharedPref,
        context: Context,
        db: LocalRepoImp,
        onEnd: () -> Unit
    ) {
        addChildRemoteAPI(
            onStart,name, age, gender, imageUriString, parentUserName, pref, context, disease, difficulty, db,onEnd
        )
    }

    private fun addChildRemoteAPI(
        onStart: () -> Unit,
        name: String,
        age: Int,
        gender: Int,
        clientImage: String,
        parentUserName: String,
        pref: SharedPref,
        context: Context,
        disease: String,
        difficulty: String,
        db: LocalRepoImp,
        onEnd: () -> Unit

    ) {
        val clientFile: File = DataFormater.uriToFile(context, clientImage)!!
        val requestFile = clientFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("ClientFile", clientFile.name, requestFile)

        val requestName = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val requestAge = age.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val requestGender = gender.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val requestParentUserName = parentUserName.toRequestBody("text/plain".toMediaTypeOrNull())
        val token = "Bearer ${pref.getProfileDetails().token}"
        Log.d("GenderValue", gender.toString())

        viewModelScope.launch {
            onStart()
            val response = RetrofitBuilder.addChildApiService.addChild(
                token,
                requestName,
                requestAge,
                requestGender,
                imagePart,
                requestParentUserName
            )
            if (response.isSuccessful) {
                val responseBody = response.body()!!
                val id = responseBody.id
                val imageName = "${responseBody.name}_$id"
                val imageUrl = responseBody.image
                Log.d("RequestGender", responseBody.gender.toString())


                val uri = AppDataManager.downloadAndSaveChildImage(context, imageUrl, imageName)
                addTheChildLocalDB(name, id, age, uri.toString(), gender, disease, difficulty, db, onEnd)

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show()
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error Occurred, try again!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun addTheChildLocalDB(
        name: String,
        id: Int,
        age: Int,
        imageUri: String,
        genderInt: Int,
        disease: String,
        difficulty: String,
        db: LocalRepoImp,
        onEnd: () -> Unit
    ) {
        val child = Child(
            id, name, age, imageUri, genderInt, disease, difficulty,
            0, 0, 0, 0, "", "", returnDate()
        )
        db.insertChild(child)
        withContext(Dispatchers.Main) {
            onEnd()
        }
    }


    private fun returnDate(): String {
        val currentDate = LocalDateTime.now()
        return currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }

    fun updateChild(onStart: () -> Unit,id: Int, name: String, age: Int, imageUri: String, gender: Int,dis:String,diff:String,db:LocalRepoImp,context: Context,pref: SharedPref,onEnd: () -> Unit){

           viewModelScope.launch(Dispatchers.IO){
               try {

                   withContext(Dispatchers.Main){
                Toast.makeText(context, "Updating...", Toast.LENGTH_SHORT).show()
                onStart()
            }
            val oChd = db.getChildById(id)
            val parentName = pref.getProfileDetails().name
            val clientFile: File = DataFormater.uriToFile(context, imageUri)!!
            val requestFile = clientFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData("ClientFile", clientFile.name, requestFile)

            val requestName = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val requestAge = age.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val requestGender = gender.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val requestParentUserName = parentName.toRequestBody("text/plain".toMediaTypeOrNull())
            val token = "Bearer ${pref.getProfileDetails().token}"

            val response = RetrofitBuilder.updateChildApiService.updateChild(id,
                token,
                requestName,
                requestAge,
                requestGender,
                imagePart,
                requestParentUserName
            )
            if (response.isSuccessful){
                val resBody = response.body()!!
                val imageName = "${resBody.name}_${resBody.id}"
                val imageUrl = resBody.image
                val newImageUri = AppDataManager.downloadAndSaveChildImage(context, imageUrl, imageName).toString()
                val newChild = Child(
                    resBody.id,
                    resBody.name,
                    resBody.age,
                    newImageUri,
                    resBody.gender,
                    dis,
                    diff,
                    oChd.readingRate,
                    oChd.writingRate,
                    oChd.readingDays,
                    oChd.writingDays,
                    oChd.latestReadingDay,
                    oChd.latestWritingDay,
                    oChd.date
                )
                db.updateChild(newChild)
                withContext(Dispatchers.Main){
                    Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show()
                    onEnd()
                }
            }
            else{
                withContext(Dispatchers.Main){
                    Toast.makeText(context, "Error Occurred, try again!", Toast.LENGTH_SHORT).show()
                }
            }
    } catch (e:Exception){
        Log.d("UpdateChild", e.message.toString())
           Toast.makeText(context, "Check your internet connection, try again!", Toast.LENGTH_SHORT).show()
       }
    }
    }



}
