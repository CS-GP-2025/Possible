package com.example.possible.ui.profile

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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.possible.databinding.ActivityEditProfileBinding
import com.example.possible.repo.local.SharedPref

class EditProfileActivity : AppCompatActivity() {

   private companion object {
        const val GALLERY_REQUEST_CODE = 101
        const val PREF_NAME = "ProfilePreferences"
        const val IMAGE_URI_KEY = "profileImageUri"
    }

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var sharedPreferences: SharedPref

    @SuppressLint("IntentReset")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
         checkAndRequestPermissions()
        // إعداد SharedPreferences
        sharedPreferences = SharedPref(this)
        val user=sharedPreferences.getProfileDetails()
        binding.nameET.setText(user.getName())
        binding.emailET.setText(user.getEmail())
        binding.passwordET.setText(user.getPassword())

        // تحميل الصورة المحفوظة (لو موجودة)
        loadProfileImage()
        setControllers()
        binding.chooseImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY_REQUEST_CODE)
            animateBtn(binding.chooseImage)
        }
        // زر اختيار الصورة


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

                saveProfileImageUri(selectedImageUri.toString())
            }
        }
    }

    private fun saveProfileImageUri(uri: String) {
        sharedPreferences.setImage(uri)
    }
    private fun setControllers(){
        binding.backArrowIV.setOnClickListener{
            finish()
        }
        binding.editBtn.setOnClickListener{
            val name=binding.nameET.text.toString()
            val email=binding.emailET.text.toString()
            val password=binding.passwordET.text.toString()
            sharedPreferences.setProfileData(name,email,password,true)
            animateBtn(binding.editBtn)
            Toast.makeText(this, "Edited", Toast.LENGTH_SHORT).show()

        }
    }

    private fun loadProfileImage() {
        val savedUri = sharedPreferences.getImage()
        if (savedUri != null) {
            val uri = Uri.parse(savedUri)
            binding.profileImage.setImageURI(uri)
        }}

    override fun onResume() {
        super.onResume()
        loadProfileImage()
    }
    private fun animateBtn(b:View){
        b.animate().scaleY(1.2f).scaleX(1.2f).setDuration(150).withEndAction{
            b.animate().scaleX(1f).scaleY(1f).setDuration(150).start()
        }.start()

    }
}
