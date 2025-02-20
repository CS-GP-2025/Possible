package com.example.possible.ui.test.dysgraphiaTest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.possible.R
import com.example.possible.databinding.ActivityDysgraphiaTestBinding
import com.example.possible.model.Question
import com.example.possible.model.Test
import com.example.possible.ui.drawing.DrawingFragment
import com.example.possible.ui.test.dyscalculiaTest.AddingFragment
import com.example.possible.ui.test.dyscalculiaTest.ArithmeticFragment
import com.example.possible.ui.test.dyscalculiaTest.ComparisonFragment
import com.example.possible.ui.tracing.TracingFragment
import com.example.possible.util.TestDecoder
import com.example.possible.util.Tests
class DysgraphiaTestActivity : AppCompatActivity() {
    private lateinit var fragments: MutableList<Fragment?>
    private lateinit var binding: ActivityDysgraphiaTestBinding
    private lateinit var test: Test
    private var currentIndex = 0 // Track the current fragment
    private var result: Int = 0
    private var testId: Int = -1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDysgraphiaTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        testId = intent.getIntExtra("test", -1)
        // Initialize the data
        test = Tests.tests[testId]
        fragments = MutableList(test.listOfQuestions.size) { null } // Array to store fragments
        binding.result.text = result.toString()
        // Set the initial values in the UI
        binding.pages.text = "1/${test.listOfQuestions.size}"
        binding.nameOfTest.text = test.name
        binding.typeOfTest.text = test.type
        binding.noOfQuestions.text = test.listOfQuestions.size.toString()

        // Load the first fragment
        loadFragment(currentIndex)

        // Next button click listener
        binding.theNext.setOnClickListener {
            if (currentIndex < test.listOfQuestions.size - 1) {
                currentIndex++
                loadFragment(currentIndex)
            }
        }
        binding.viewResult.setOnClickListener {
            result = calcTheResult()
            binding.result.text = result.toString()
        }

        // Previous button click listener
        binding.backToPrevious.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                loadFragment(currentIndex)
            }
        }
    }


    private fun loadFragment(index: Int) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        // Hide all fragments
        fragments.forEachIndexed { i, fragment ->
            if (fragment != null && fragment.isAdded) {
                transaction.hide(fragment)
            }
        }

        // If the fragment is already created, show it
        var fragment = fragments[index]
        if (fragment == null) {
            // Create a new fragment only if it doesn't exist
            fragment = createFragmentForQuestion(test.listOfQuestions[index])
            fragments[index] = fragment
            transaction.add(R.id.frameT, fragment, "fragment_$index")
        } else {
            transaction.show(fragment)
        }

        // Commit the transaction
        transaction.commit()
    }

    private fun createFragmentForQuestion(q: Question): Fragment {
        return when (q.type) {
            "Write a letter", "Write a number" -> {
                val (index, type,level) = TestDecoder.decodeLetterOrNumber(q.question)
                if(level == "beginner"){
                    TracingFragment().getInstance(index, type)
                }
                else{
                    DrawingFragment().getInstance(index, type)
                }
            }
            "Sentence Combining"->{
                SentenceCombiningFragment().newInstance(q.question)
            }
            "Complete"->{
                SentenceCompletingFragment().newInstance(q.question)
            }
            else -> throw IllegalArgumentException("Unsupported question type")
        }
    }

    private fun calcTheResult(): Int {
        if (fragments.isEmpty()) {
            return 0
        }
        var result = 0
        fragments.forEach { fragment ->
            when (fragment) {
                is TracingFragment -> {
                    result += fragment.getResultsForTest()
                }
                is DrawingFragment -> {
                    result += 2
                }

                is SentenceCompletingFragment -> {
                    result += fragment.returnResult()
                }

                is SentenceCombiningFragment -> {
                    result += fragment.returnResult()
                }
            }
            }
        return result
    }
}

