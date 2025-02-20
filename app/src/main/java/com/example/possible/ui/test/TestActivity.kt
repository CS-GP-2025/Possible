package com.example.possible.ui.test

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.possible.databinding.ActivityTestBinding
import com.example.possible.model.Question
import com.example.possible.model.Test
import com.example.possible.ui.test.dyscalculiaTest.AddingFragment
import com.example.possible.ui.test.dyscalculiaTest.ArithmeticFragment
import com.example.possible.ui.test.dyscalculiaTest.ComparisonFragment
import com.example.possible.ui.tracing.TracingFragment
import com.example.possible.util.Tests
import com.example.possible.util.adapter.FragmentAdapter

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding
    private lateinit var test: Test
    private lateinit var frameAdapter: FragmentAdapter
    private val fragments = ArrayList<Fragment>()
    private var result : Int = 0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTestBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val testId = intent.getIntExtra("test", -1)
        // Initialize the data
        test = Tests.tests[testId]
        binding.pages.text= "1/${test.listOfQuestions.size}"
        binding.nameOfTest.text = test.name
        binding.typeOfTest.text = test.type

        if (test.type == "Dyscalculia") {
            setDyscalculiaFragmentsAndQuestions()
        }
        else{
            setDysgraphyaFragmentsAndQuestions()
        }
        handleGetResult()
        changePageNumber()

        setContentView(binding.root)
    }
    private fun setDyscalculiaFragmentsAndQuestions(){
        setTheSuitableFrameForTheQuestions(test.listOfQuestions[0])
        setTheSuitableFrameForTheQuestions(test.listOfQuestions[1])
        setTheSuitableFrameForTheQuestions(test.listOfQuestions[2])
        setTheSuitableFrameForTheQuestions(test.listOfQuestions[3])
        frameAdapter = FragmentAdapter(this, fragments)
        binding.viewPager.adapter = frameAdapter
        binding.dotsIndicator.attachTo(binding.viewPager)
    }
    private fun setDysgraphyaFragmentsAndQuestions(){
        setTheSuitableFrameForTheQuestions(test.listOfQuestions[0])
        setTheSuitableFrameForTheQuestions(test.listOfQuestions[1])
        setTheSuitableFrameForTheQuestions(test.listOfQuestions[2])
        setTheSuitableFrameForTheQuestions(test.listOfQuestions[3])
        frameAdapter = FragmentAdapter(this, fragments)
        binding.viewPager.adapter = frameAdapter
        binding.dotsIndicator.attachTo(binding.viewPager)
    }
    private fun setTheSuitableFrameForTheQuestions(q:Question){
        when (q.type) {
            "Adding", "Subtraction" -> {
                fragments.add(AddingFragment().getInstance(q.question,q.type))
            }
            "Arithmetic Sequence" -> {
                fragments.add(ArithmeticFragment().getInstance(q.question))
            }
            "Inequality" -> {
                fragments.add(ComparisonFragment().getInstance(q.question))
            }
            "Write a letter" -> {
                val (index,type)=q.question.split(" ")
                fragments.add(TracingFragment().getInstance(index.toInt(),type))
            }
            "Write a number" -> {
                val (index,type)=q.question.split(" ")
                fragments.add(TracingFragment().getInstance(index.toInt(),type))            }
        }
    }
    private fun onBack(){
    }
    @SuppressLint("SetTextI18n")
    private fun handleGetResult(){
        binding.viewResult.setOnClickListener {
            result=calcResult()
            binding.result.text=result.toString()
        }
    }

    private fun  calcResult():Int {
        var res = 0
        for (frag in fragments) {
            when (frag) {
                is AddingFragment -> {
                    res += frag.getResult()
                }
                is ArithmeticFragment -> {
                    res += frag.getResults()
                }
                is ComparisonFragment -> {
                    res += frag.getResult()
                }
            }
        }
        return res
    }
    private fun changePageNumber(){
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.pages.text = "${(position + 1)}/${fragments.size}"
            }
        })

    }
}