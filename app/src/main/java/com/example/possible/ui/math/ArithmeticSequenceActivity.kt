package com.example.possible.ui.math

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.possible.R
import com.example.possible.databinding.ActivityArithmeticSequenceBinding
import com.example.possible.repo.local.MathQuestions

class ArithmeticSequenceActivity : AppCompatActivity() {
    lateinit var binding: ActivityArithmeticSequenceBinding
    private var firstResult = 0
    private var secondResult = 0
    private var sequence :List<Int> = emptyList()
    private var level=""
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityArithmeticSequenceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        level = intent.getStringExtra("level").toString()
        setContentView(binding.root)
        setSequence(level)
        handleDone()
        handleChange()
        binding.backArrowIV.setOnClickListener {
            finish()
        }
    }

    private fun getSequence(level: String): List<Int> {
       return if (level == "beginner") {
           MathQuestions.getRandomSequence(MathQuestions.simpleSequences)
       }
        else{
            MathQuestions.getRandomSequence(MathQuestions.proLevelSequences)
       }
    }
    @SuppressLint("SetTextI18n")
    private fun setSequence(level: String) {
        sequence = getSequence(level)
        binding.first.text = sequence[0].toString()+" ,   "
        binding.second.text = sequence[1].toString()+" ,  "
        binding.third.text = sequence[2].toString()+" ,  "
        binding.fivth.text = sequence[4].toString()+" ,  "
        binding.seventh.text = " ,  "+sequence[6].toString()
        firstResult = sequence[3]
        secondResult = sequence[5]
    }
    private fun handleCardBackground(card: View, expectedResult: Boolean) {
        if (expectedResult){
            card.setBackgroundResource(R.color.correct)
        }
        else{
            card.setBackgroundResource(R.color.error)
        }
    }
    private fun handleDone(){
        binding.done.setOnClickListener {
            val firstInput = if(binding.fourth.text.toString().isNotEmpty()){
                binding.fourth.text.toString().toInt()
            } else{
                -1
            }
            val secondInput = if(binding.sixth.text.toString().isNotEmpty()){
                binding.sixth.text.toString().toInt()
            } else{
                -1
            }

            if(firstInput == firstResult && secondInput == secondResult){
                Toast.makeText(this, "Correct", LENGTH_SHORT).show()
                binding.celeprationAnim.visibility = View.VISIBLE
                binding.celeprationAnim.playAnimation()
            }
            else{
                Toast.makeText(this, "Wrong", LENGTH_SHORT).show()
            }
            handleCardBackground(binding.fourth, firstInput == firstResult)
            handleCardBackground(binding.sixth, secondInput == secondResult)

        }
    }
    private fun handleChange(){
        binding.changeQues.setOnClickListener {
            setSequence(level)
            binding.fourth.text.clear()
            binding.sixth.text.clear()
            binding.celeprationAnim.visibility = View.GONE
            binding.celeprationAnim.cancelAnimation()
            binding.fourth.setBackgroundResource(R.color.white)
            binding.sixth.setBackgroundResource(R.color.white)
        }
    }
}