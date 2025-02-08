package com.example.possible.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.possible.databinding.ActivityMainBinding
import com.example.possible.repo.local.SharedPref
import com.example.possible.ui.math.MainMathActivity
import com.example.possible.ui.profile.ProfileActivity
import com.example.possible.ui.reading.ReadingMainActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pref: SharedPref
    private val Handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        pref = SharedPref(this)
        loadProfileImage()


        binding.letters.setOnClickListener {
            numbersOrLetters(result = "letters")
        }
        binding.numbers.setOnClickListener {
            numbersOrLetters(result = "numbers")
        }
        binding.hideView.setOnClickListener{
            hideTheView()
        }
        binding.readingBtn.setOnClickListener {
            val intent = Intent(this, ReadingMainActivity::class.java)
            startActivity(intent)
        }
        binding.mathBtn.setOnClickListener {
            val intent = Intent(this, MainMathActivity::class.java)
            startActivity(intent)
        }
        binding.helloLayout.setOnClickListener {
            goToProfile()
        }
        binding.profileIV.setOnClickListener {
           goToProfile()
        }
        binding.settingIV.setOnClickListener {
          goToProfile()
        }
        setContentView(binding.root)

    }
    private fun goToProfile() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }
    private fun numbersOrLetters(result: String){
        viewBeginnerOrProf()
        binding.beginnerIV.setOnClickListener {
            pref.setMode("beginner")
            sendResult(result)
        }
        binding.proIV.setOnClickListener {
            pref.setMode("professional")
            sendResult(result)
        }
    }
    private fun viewBeginnerOrProf() {
        binding.beginnerOrProf.visibility= VISIBLE
       // binding.btns.visibility= GONE
        binding.beginnerOrProf.animate().scaleX(0f).scaleY(0f).setDuration(0).start()
        binding.beginnerOrProf.animate().scaleX(1f).scaleY(1f).setDuration(500).start()
    }

    private fun hideTheView(){
        Handler.postDelayed({
            binding.beginnerOrProf.animate().scaleX(0f).scaleY(0f).setDuration(300).withEndAction{
                binding.beginnerOrProf.visibility= GONE
            }.start()
        },200)

    }


    private fun sendResult(result: String) {
        val intent = Intent(this, LettersNumbersActivity::class.java)
        intent.putExtra("result", result)
        startActivity(intent)
        hideTheView()
    }
    private fun loadProfileImage() {
        val savedUri = pref.getImage()
        if (savedUri != null) {
            val uri = Uri.parse(savedUri)
            binding.profileIV.setImageURI(uri)
        }}

    override fun onResume() {
        super.onResume()
        loadProfileImage()
        val userName=pref.getProfileDetails().getName()
        binding.userNameTV.text=userName
    }
}