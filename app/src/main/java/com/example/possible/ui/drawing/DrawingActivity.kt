package com.example.possible.ui.drawing

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.possible.databinding.ActivityDrawingBinding
import com.example.possible.repo.local.SharedPref
import com.example.possible.util.TestDecoder

class DrawingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrawingBinding
    private lateinit var fragment: DrawingFragment
    private lateinit var pref:SharedPref
    private var numOfQuestion=0
    private var level="pro"
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
            if(isItFromSettingTest()){
                getTheQuestionOnDone(numOfQuestion)
                finish()
            }
            else{
                finish()
            }
        }


        setContentView(binding.root)

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
    private fun isItFromSettingTest():Boolean{
        numOfQuestion = intent.getIntExtra("noOfQuestion",0)
        return numOfQuestion!=0

    }
    private fun getTheQuestionOnDone(numOfQuestion:Int){
        val ques = TestDecoder.encodeLetterOrNumber(index,type,level)
        setWhichQuestion(ques,numOfQuestion)
    }
    private fun setWhichQuestion(ques:String,numOfQuestion:Int){
        when(numOfQuestion){
            1->{
                pref.setQ1(ques)
            }
            2->{
                pref.setQ2(ques)
            }
            3->{
                pref.setQ3(ques)
            }
            4->{
                pref.setQ4(ques)
            }
        }
    }
}