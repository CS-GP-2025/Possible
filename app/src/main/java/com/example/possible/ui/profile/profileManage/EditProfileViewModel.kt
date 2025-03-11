package com.example.possible.ui.profile.profileManage

import android.content.Context
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.possible.databinding.ActivityEditProfileBinding
import com.example.possible.repo.local.SharedPref
import com.example.possible.repo.remote.RetrofitBuilder
 import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File

class EditProfileViewModel: ViewModel() {
    fun update(
        username: String,
        email: String,
        password: String,
        roleNo: String,
        imageFile: File,
        context: Context,
        bind: ActivityEditProfileBinding,
        onUpdateSuccess: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                bind.loadingView.visibility = VISIBLE
            }
            try {
                val usernamePart = username.toRequestBody("text/plain".toMediaTypeOrNull())
                val emailPart = email.toRequestBody("text/plain".toMediaTypeOrNull())
                val passwordPart = password.toRequestBody("text/plain".toMediaTypeOrNull())
                val roleNoPart = roleNo.toRequestBody("text/plain".toMediaTypeOrNull())

                val requestFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData("ClientFile", imageFile.name, requestFile)

                val response = RetrofitBuilder.editProfileApiService.updateProfile(
                    token = "Bearer ${SharedPref(context).getProfileDetails().token}",usernamePart, emailPart, passwordPart, roleNoPart, imagePart)
                    .execute()

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val registerResponse = response.body()!!
                        Toast.makeText(context, "✅ Updated Successfully!", Toast.LENGTH_SHORT).show()
                        onUpdateSuccess()
                        Toast.makeText(context, registerResponse.string(), Toast.LENGTH_SHORT).show()

                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.d("EditProfileViewModel", "Response: ${response.body()}")
                        Log.e("EditProfileViewModel", "Error Code: ${response.code()} - Error Body: ${response.errorBody()?.string()}")



                        errorBody?.let {
                            val jsonObject = JSONObject(it)
                            if (jsonObject.has("errors")) {
                                val errors = jsonObject.getJSONObject("errors")
                                if (errors.has("Email")) {
                                    val emailErrors = "❌ invalid email: " + errors.getJSONArray("Email").getString(0)
                                    withContext(Dispatchers.Main) {
                                        bind.emailLayout.error = emailErrors
                                    }

                                }
                                if (errors.has("Password")) {
                                    val passwordErrors = "❌ invalid password: " + errors.getJSONArray("Password").getString(0)
                                    withContext(Dispatchers.Main) {
                                        bind.passLayout.error = passwordErrors
                                    }
                                }
                            } else if (jsonObject.has("Custom")) {
                                val customErrors = jsonObject.getJSONArray("Custom")
                                val errorMessage = customErrors.getString(0)
                                if (errorMessage.split(" ")[0]=="Passwords")  {
                                    withContext(Dispatchers.Main) {
                                        bind.passLayout.error = errorMessage
                                    }
                                }
                                else {
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(context,errorMessage, Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    }
                }
          } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "✅ Updated Successfully!!", Toast.LENGTH_SHORT).show()
                }
            }
            withContext(Dispatchers.Main){
                bind.loadingView.visibility = GONE
                //val pref = SharedPref(context)
                onUpdateSuccess()


            }

        }
    }

}