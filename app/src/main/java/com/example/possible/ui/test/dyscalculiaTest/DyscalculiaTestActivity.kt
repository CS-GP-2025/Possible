package com.example.possible.ui.test.dyscalculiaTest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.possible.databinding.ActivityTestBinding
import com.example.possible.model.Question
import com.example.possible.model.Test
import com.example.possible.util.Tests
import com.example.possible.util.adapter.FragmentAdapter

class DyscalculiaTestActivity : AppCompatActivity() {
    // View binding for accessing UI components
    private lateinit var binding: ActivityTestBinding
    private lateinit var test: Test
    private lateinit var frameAdapter: FragmentAdapter
    private val fragments = ArrayList<Fragment>()
    private var result: Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Get test ID from intent and initialize test data
        val testId = intent.getIntExtra("test", -1)
        test = Tests.tests[testId]

        // Set initial UI values
        binding.pages.text = "1/${test.listOfQuestions.size}"
        binding.nameOfTest.text = test.name
        binding.typeOfTest.text = test.type

        // Setup test questions and UI elements
        setupTestFragments()
        setupResultHandler()
        setupPageChangeListener()
    }

    // Initialize and setup fragments for test questions
    private fun setupTestFragments() {
        test.listOfQuestions.forEach { setTheSuitableFrameForTheQuestions(it) }
        frameAdapter = FragmentAdapter(this, fragments)
        binding.viewPager.adapter = frameAdapter
        binding.dotsIndicator.attachTo(binding.viewPager)
    }

    // Determine the appropriate fragment for each question type
    private fun setTheSuitableFrameForTheQuestions(q: Question) {
        val fragment = when (q.type) {
            "Adding", "Subtraction" -> AddingFragment().getInstance(q.question, q.type)
            "Arithmetic Sequence" -> ArithmeticFragment().getInstance(q.question)
            "Inequality" -> ComparisonFragment().getInstance(q.question)
            else -> null
        }
        fragment?.let { fragments.add(it) }
    }

    // Handle result calculation and display
    @SuppressLint("SetTextI18n")
    private fun setupResultHandler() {
        binding.viewResult.setOnClickListener {
            result = calculateResult()
            binding.result.text = result.toString()
        }
    }

    // Calculate the total result from all fragments
    private fun calculateResult(): Int {
        return fragments.sumOf {
            when (it) {
                is AddingFragment -> it.getResult()
                is ArithmeticFragment -> it.getResults()
                is ComparisonFragment -> it.getResult()
                else -> 0
            }
        }
    }

    // Update the page number display when the user navigates
    private fun setupPageChangeListener() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.pages.text = "${position + 1}/${fragments.size}"
            }
        })
    }
}
