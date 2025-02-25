package com.example.possible.ui.drawing

import android.net.Uri
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.possible.databinding.ActivityDrawingBinding
import com.example.possible.repo.local.LettersAndNumbers
import com.example.possible.repo.local.SharedPref
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DrawingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrawingBinding
    private lateinit var fragment: DrawingFragment
    private lateinit var pref:SharedPref
    private var index=0
    private var type=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        pref=SharedPref(this)
        binding = ActivityDrawingBinding.inflate(layoutInflater)
        //get the data from the previos
        index=intent.extras?.getInt("letterIndex",0)!!
        type=intent.extras?.getString("type",null)!!
        fragment = DrawingFragment().getInstance(index,type)
        setFrame(fragment)

        binding.backArrowIV.setOnClickListener{
            finish()
        }
        binding.doneButton.setOnClickListener {
                lifecycleScope.launch {
                    if(fragment.getResult()){
                        binding.celeprationView.visibility= VISIBLE
                        binding.celeprationAnim.playAnimation()
                        binding.doneButton.isEnabled=false
                        Toast.makeText(this@DrawingActivity, "Correct", Toast.LENGTH_SHORT).show()                    }
                    else{
                        Toast.makeText(this@DrawingActivity, "Wrong Try Again", Toast.LENGTH_SHORT).show()
                        delay(2000)
                        fragment.reset()
                    }
                }

        }
        binding.nextBtn.setOnClickListener {
            handleNext()
        }


        setContentView(binding.root)

    }


    private fun handleNext(){
        if(type=="letter"){
            if(index!= LettersAndNumbers.letters.size-1){
                //go to next letter
                setNextData(index +1,"letter")
            }
            else{
                this.finish()
            }
        }
        else{
            if(index!= LettersAndNumbers.numbers.size-1){
                //go to next number
                    setNextData(index+1,"number")
            }
        }
    }
    private fun setNextData(index:Int, type:String){
        this.index=index
        this.type=type
        binding.nextBtn.isEnabled=false
        binding.nextBtn.postDelayed({
            binding.nextBtn.isEnabled=true}
            ,1000)
        binding.celeprationView.visibility= GONE
        binding.doneButton.isEnabled=true
        fragment= DrawingFragment().getInstance(index,type)
        setFrame(fragment)
    }

    private fun setFrame(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(binding.frameDrawer.id,fragment)
            .commit()
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
        val user=pref.getProfileDetails()
        binding.userNameTV.text=user.getName()
    }

}