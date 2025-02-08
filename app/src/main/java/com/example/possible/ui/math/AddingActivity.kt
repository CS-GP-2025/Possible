package com.example.possible.ui.math

import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.possible.R
import com.example.possible.databinding.ActivityAddingBinding
import com.example.possible.model.Adding
import com.example.possible.repo.local.MathQuestions
import com.example.possible.util.adapter.ResultAdapter

class AddingActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddingBinding
    private lateinit var question: Adding
    private lateinit var resultArray: ArrayList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val operationType=intent.getStringExtra("operationName")
        val operationLevel=intent.getStringExtra("operationLevel")



        setTheQuestionView(operationType!!,operationLevel!!)

        binding.changeQues.setOnClickListener{
            setTheQuestionView(operationType,operationLevel)
        }

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.resultAdapter.layoutManager = linearLayoutManager
        binding.done.setOnClickListener{
            val msg=if(question.c==getTheSum()) "true" else "wrong"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            if(msg=="true"){
                binding.celeprationAnim.visibility=VISIBLE
                binding.celeprationAnim.playAnimation()
            }

        }
        binding.backArrowIV.setOnClickListener{
            finish()
        }
        setContentView(binding.root)
    }
    private fun setTheQuestionView(type:String, level:String){
        question=theQuestion(type,level)
        binding.firstNumber.text=question.a.toString()
        binding.secondNumber.text=question.b.toString()
        val answerPlaces=question.resultPlaces
        val arr= when(answerPlaces){
            1-> arrayListOf(0)
            2->arrayListOf(0,0)
            3->arrayListOf(0,0,0)
            4->arrayListOf(0,0,0,0)
            5->arrayListOf(0,0,0,0,0)
            else->arrayListOf(0,0,0,0,0)
        }
        resultArray=arr
        binding.resultAdapter.adapter=ResultAdapter(resultArray)
    }
    private fun getTheSum():Int{
        var sum=0
        var dub=1
        var i=0
        while (i<resultArray.size){
            sum+=resultArray[i]*dub
            dub*=10
            i++
        }
        return sum
    }
    private fun theQuestion(type:String, level:String):Adding{
        if(type=="Adding"){
            binding.sign.setImageResource(R.drawable.plus_sign)
            return if(level=="beginner"){
                MathQuestions.getRandomQuestion(MathQuestions.simpleAddition)
            } else{
                MathQuestions.getRandomQuestion(MathQuestions.proAddition)
            }
        }
        else{
            binding.sign.setImageResource(R.drawable.sign_minus)
            return if(level=="beginner"){
                MathQuestions.getRandomQuestion(MathQuestions.simpleSubtraction)
            }
            else{
                MathQuestions.getRandomQuestion(MathQuestions.proSubtraction)
            }

            }
    }
}