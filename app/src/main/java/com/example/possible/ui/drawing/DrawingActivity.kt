package com.example.possible.ui.drawing

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.possible.databinding.ActivityDrawingBinding
import com.example.possible.repo.local.LettersAndNumbers
import com.example.possible.repo.local.SharedPref

class DrawingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrawingBinding
    private lateinit var fragment: DrawingFragment
    private lateinit var sharedPreferences:SharedPref
    private var index=0
    private var type=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        sharedPreferences=SharedPref(this)
        binding = ActivityDrawingBinding.inflate(layoutInflater)
        //get the data from the previos
        index=intent.extras?.getInt("letterIndex",0)!!
        type=intent.extras?.getString("type",null)!!
        fragment = DrawingFragment().getInstance(index,type)
        setFrame(fragment)
        binding.letterName.text=setNameOfLetter(type,index)

        binding.backArrowIV.setOnClickListener{
            finish()
        }


        setContentView(binding.root)

    }

    private fun setFrame(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(binding.frameDrawer.id,fragment)
            .commit()
    }
    private fun setNameOfLetter(type:String,index:Int):String{
        val name =if(type=="letter"){
             LettersAndNumbers.letters[index].name
        }else{
             LettersAndNumbers.numbers[index].name
        }
        return name
    }
    private fun loadProfileImage() {
        val savedUri = sharedPreferences.getImage()
        if (savedUri != null) {
            val uri = Uri.parse(savedUri)
            binding.profileIV.setImageURI(uri)
        }}

    override fun onResume() {
        super.onResume()
        loadProfileImage()
        val user=sharedPreferences.getProfileDetails()
        binding.userNameTV.text=user.getName()
    }
}