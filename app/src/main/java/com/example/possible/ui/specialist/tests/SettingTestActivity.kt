package com.example.possible.ui.specialist.tests

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.possible.databinding.ActivitySettingTestBinding
import com.example.possible.model.Question
import com.example.possible.model.Test
import com.example.possible.repo.local.LettersAndNumbers
import com.example.possible.repo.local.SharedPref
import com.example.possible.ui.drawing.DrawingActivity
import com.example.possible.ui.math.AddingActivity
import com.example.possible.ui.math.ArithmeticSequenceActivity
import com.example.possible.ui.math.ComparisonActivity
import com.example.possible.ui.test.dysgraphiaTest.SentenceMainActivity
import com.example.possible.ui.tracing.TracingActivity
import com.example.possible.util.Tests
import com.google.android.material.textfield.TextInputLayout
class SettingTestActivity : AppCompatActivity() {
    // Binding object for accessing UI components
    private lateinit var binding: ActivitySettingTestBinding
    private lateinit var listOfOption: List<String>
    private lateinit var test: Test
    private lateinit var pref: SharedPref

    private var numOfQuestion: Int = 0
    private var typeLetterOrNumber: String = ""
    private var level = "beginner"

    // Activity lifecycle method - initializes the activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = SharedPref(this)
        val type = intent.getStringExtra("type") ?: ""
        val name = intent.getStringExtra("name") ?: ""

        binding.nameOfTest.text = name
        binding.typeOfTest.text = type
        listOfOption = setTheList(type)

        setupControllers()
        manageLevel()
    }

    // Sets up UI controllers and event listeners
    private fun setupControllers() {
        binding.backArrowIV.setOnClickListener { finish() }

        listOf(binding.autoCompleteQ1, binding.autoCompleteQ2, binding.autoCompleteQ3, binding.autoCompleteQ4).forEachIndexed { index, autoComplete ->
            setAutoChoose(autoComplete, index + 1)
            setListOfOption(autoComplete, listOfOption)
        }

        binding.doneBtn.setOnClickListener {
            if (isAllFieldsFilled()) {
                Toast.makeText(this, "Test is ready", Toast.LENGTH_SHORT).show()
                setTest()
                finish()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Sets an item click listener for auto-complete dropdown selections
    private fun setAutoChoose(dropout: AutoCompleteTextView, numOfQ: Int) {
        dropout.setOnItemClickListener { parent, _, position, _ ->
            setAction(parent.getItemAtPosition(position) as String, numOfQ)
        }
    }

    // Populates an AutoCompleteTextView with options
    private fun setListOfOption(dropout: AutoCompleteTextView, options: List<String>) {
        dropout.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, options))
    }

    // Handles actions based on selected test type
    private fun setAction(selectedItem: String, numOfQ: Int) {
        val intent = when (selectedItem) {
            "Adding", "Subtraction" -> Intent(this, AddingActivity::class.java).apply {
                putExtra("operationName", selectedItem)
                putExtra("operationLevel", level)
            }
            "Arithmetic Sequence" -> Intent(this, ArithmeticSequenceActivity::class.java)
            "Inequality" -> Intent(this, ComparisonActivity::class.java)
            "Sentence Combining" -> Intent(this, SentenceMainActivity::class.java).apply { putExtra("mode", "combining") }
            "Complete" -> Intent(this, SentenceMainActivity::class.java).apply { putExtra("mode", "completing") }
            "Write a letter", "Write a number" -> {
                typeLetterOrNumber = if (selectedItem.contains("letter")) "letter" else "number"
                numOfQuestion = numOfQ
                binding.beginnerOrProf.visibility = VISIBLE
                return
            }
            else -> return
        }
        intent.putExtra("level", "beginner")
        intent.putExtra("noOfQuestion", numOfQ)
        startActivity(intent)
    }

    // Checks if all required fields are filled
    private fun isAllFieldsFilled(): Boolean {
        return listOf(binding.autoCompleteQ1, binding.autoCompleteQ2, binding.autoCompleteQ3, binding.autoCompleteQ4).all {
            val text = it.text.toString()
            showError(text, it.parent.parent as TextInputLayout)
            text.isNotEmpty()
        }
    }

    // Displays an error message if a field is empty
    private fun showError(option: String, inputLayout: TextInputLayout) {
        inputLayout.error = if (option.isEmpty()) "This field is required" else null
    }

    // Returns a list of test options based on the type
    private fun setTheList(type: String): List<String> {
        return when (type) {
            "Dyslexia", "Dyscalculia" -> listOf("Adding", "Subtraction", "Arithmetic Sequence", "Inequality")
            "Dysgraphia" -> listOf("Write a letter", "Write a number", "Sentence Combining", "Complete")
            else -> emptyList()
        }
    }

    // Saves the selected test settings
    private fun setTest() {
        val questions = listOf(
            Question(binding.autoCompleteQ1.text.toString(), pref.getQ1()!!),
            Question(binding.autoCompleteQ2.text.toString(), pref.getQ2()!!),
            Question(binding.autoCompleteQ3.text.toString(), pref.getQ3()!!),
            Question(binding.autoCompleteQ4.text.toString(), pref.getQ4()!!)
        )
        test = Test(binding.nameOfTest.text.toString(), binding.typeOfTest.text.toString(), questions)
        Tests.tests.add(test)
    }

    // Selects a random letter or number for tracing or drawing activity
    private fun setRandomLetterOrNumber(type: String, numOfQuestion: Int) {
        val index = if (type == "letter") LettersAndNumbers.letters.indices.random() else LettersAndNumbers.numbers.indices.random()
        val intent = if (level == "beginner") Intent(this, TracingActivity::class.java) else Intent(this, DrawingActivity::class.java)
        intent.putExtra("letterIndex", index)
        intent.putExtra("type", type)
        intent.putExtra("noOfQuestion", numOfQuestion)
        startActivity(intent)
    }

    // Manages the selection between beginner and professional levels
    private fun manageLevel() {
        binding.backArrowIV.setOnClickListener { binding.beginnerOrProf.visibility = GONE }
        binding.proIV.setOnClickListener { setLevel("pro") }
        binding.beginnerIV.setOnClickListener { setLevel("beginner") }
    }

    // Updates the difficulty level and starts the corresponding activity
    private fun setLevel(newLevel: String) {
        level = newLevel
        binding.beginnerOrProf.visibility = GONE
        setRandomLetterOrNumber(typeLetterOrNumber, numOfQuestion)
    }
}
