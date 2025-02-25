package com.example.possible.ui.signLogin.signUp

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.possible.databinding.ActivitySignupBinding
import com.example.possible.repo.local.SharedPref
import com.example.possible.ui.MainActivity
import com.example.possible.ui.splach.AnimationActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var pref:SharedPref
    private lateinit var imageUriString:String
    private companion object {
        const val GALLERY_REQUEST_CODE = 101
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        pref= SharedPref(this)
        imageUriString=""
        checkAndRequestPermissions()
        setContentView(binding.root)
        setControllers()

    }
    private fun checkForApiConditions(): Boolean {
         return true
    }

    @SuppressLint("IntentReset")
    private fun setControllers(){
        binding.sign.setOnClickListener {
            onSignUp()
        }
        binding.chooseImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY_REQUEST_CODE)
        }
    }
    private fun goToAnimation(){
        startActivity(Intent(this, AnimationActivity::class.java))
        finish()
    }

    private fun onSignUp() {
            if(checkForApiConditions()){
               saveData(
                    binding.name.text.toString(),
                    binding.emailET.text.toString(),
                    binding.passwordET.text.toString()
                )
                sendDataForApi()
                viewMessageSuccessful()
                binding.nextBtn.setOnClickListener {
                    goToAnimation()
                }
        }
    }

    private fun saveData(name: String, email: String, password: String) {
         pref.setProfileData(name,email,password,true)
        pref.setImage(imageUriString)
    }

    private fun sendDataForApi(){
        //TO_DO
    }

    private val PERMISSION_REQUEST_CODE = 100

    private fun checkAndRequestPermissions() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(READ_MEDIA_IMAGES)
        } else {
            arrayOf(READ_EXTERNAL_STORAGE)
        }

        // فحص الأذونات دفعة واحدة وطلبها إذا لزم الأمر
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            if (selectedImageUri != null) {
                // تعيين الصورة في ImageView
                binding.profileImage.setImageURI(selectedImageUri)

                imageUriString = selectedImageUri.toString()
            }
        }
    }
    private fun viewMessageSuccessful(){
        binding.createdView.visibility=View.VISIBLE
        binding.createdView.animate().alpha(0f).setDuration(0).start()
        binding.createdView.animate().alpha(1f).setDuration(1000).start()
    }


}