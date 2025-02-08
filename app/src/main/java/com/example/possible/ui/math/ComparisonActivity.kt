package com.example.possible.ui.math

import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.possible.databinding.ActivityComparisionBinding
import com.example.possible.repo.local.MathQuestions

class ComparisonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityComparisionBinding
    private lateinit var comparison:Triple<Int,Int,String>
    private var level=""
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityComparisionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding.biggerThan.text=">"
        binding.smallerThan.text="<"
        binding.equal.text="="
         level = intent.getStringExtra("level")!!
        comparison=getComparison(level)
        setQuestionView(comparison)

        handleOptions()
        handleSignView()
        checkResult()
        setContentView(binding.root)
    }
    private fun setTheSign(sign: String) {
     binding.sign.text = sign
    }
    private fun handleSignView() {
        binding.sign.setOnClickListener{
            if(binding.sign.text != "") {
                binding.sign.text = ""
            }
        }
    }
    private fun handleOptions() {
        binding.biggerThan.setOnClickListener {
            setTheSign(">")
        }
        binding.smallerThan.setOnClickListener {
            setTheSign("<")
        }
        binding.equal.setOnClickListener {
            setTheSign("=")
        }
        binding.backArrowIV.setOnClickListener {
            finish()
        }
        binding.changeQues.setOnClickListener{
            comparison=getComparison(level)
            setQuestionView(comparison)
        }
    }
    private fun getComparison(level:String):Triple<Int,Int,String>{
        return if(level == "beginner") {
             MathQuestions.getRandomComparison(MathQuestions.simpleComparisons)
        }
        else
        {
             MathQuestions.getRandomComparison(MathQuestions.proComparisons)
        }
    }
    private fun setQuestionView(comparison: Triple<Int, Int, String>) {
        binding.firstNumber.text = comparison.first.toString()
        binding.secondNumber.text = comparison.second.toString()
        binding.sign.text = ""
    }
    private fun checkResult(){
        binding.done.setOnClickListener{
            if(binding.sign.text == comparison.third){
                Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show()
                binding.celeprationAnim.visibility = VISIBLE
                binding.celeprationAnim.playAnimation()
            }
            else{
                Toast.makeText(this,"Wrong",Toast.LENGTH_SHORT).show()
            }
        }
    }
}