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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.possible.R
import com.example.possible.databinding.ActivitySettingTestBinding
import com.example.possible.model.Question
import com.example.possible.model.Test
import com.example.possible.repo.local.LettersAndNumbers
import com.example.possible.repo.local.SharedPref
import com.example.possible.ui.drawing.DrawingActivity
import com.example.possible.ui.drawing.DrawingFragment
import com.example.possible.ui.math.AddingActivity
import com.example.possible.ui.math.ArithmeticSequenceActivity
import com.example.possible.ui.math.ComparisonActivity
import com.example.possible.ui.test.TestActivity
import com.example.possible.ui.test.dysgraphiaTest.DysgraphiaTestActivity
import com.example.possible.ui.test.dysgraphiaTest.SentenceMainActivity
import com.example.possible.ui.tracing.TracingActivity
import com.example.possible.util.Tests
import com.google.android.material.textfield.TextInputLayout

class SettingTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingTestBinding
    private lateinit var listOfOption: List<String>
    private lateinit var test:Test
    private var numOfQuestion:Int = 0
    private var typeLetterOrNumber:String = ""
    private lateinit var pref: SharedPref
    private var level = "beginner"
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingTestBinding.inflate(layoutInflater)
        val type = intent.getStringExtra("type")
        val name = intent.getStringExtra("name")
        pref = SharedPref(this)

        binding.nameOfTest.text=name
        binding.typeOfTest.text=type
        listOfOption = setTheList(type!!)

        setContentView(binding.root)
        setupControllers()
        mangeLevel()
        //set auto complete text view



    }
    private fun setupControllers(){
       binding.backArrowIV.setOnClickListener {
           finish()
       }
        setAutoChoose(binding.autoCompleteQ1,1)
        setAutoChoose(binding.autoCompleteQ2,2)
        setAutoChoose(binding.autoCompleteQ3,3)
        setAutoChoose(binding.autoCompleteQ4,4)

        setListOfOption(binding.autoCompleteQ1,listOfOption)
        setListOfOption(binding.autoCompleteQ2,listOfOption)
        setListOfOption(binding.autoCompleteQ3,listOfOption)
        setListOfOption(binding.autoCompleteQ4,listOfOption)
        binding.doneBtn.setOnClickListener {
            if (isAllFieldsFilled()) {
                Toast.makeText(this, "Test is ready", Toast.LENGTH_SHORT).show()
                setTest()
                finish()
            }
            else{
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun setAutoChoose(dropout:AutoCompleteTextView,numOfQ:Int){
        dropout.setOnItemClickListener { parent, _, position, _ ->
            // Get the selected item
            val selectedItem = parent.getItemAtPosition(position) as String

            setAction(selectedItem,numOfQ)
        }
    }

    private fun setListOfOption(dropout:AutoCompleteTextView, options:List<String>){
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, options)
        dropout.setAdapter(adapter)
    }
    private fun setAction(selectedItem: String,numOfQ:Int){
        when (selectedItem) {
            "Adding" -> {
                val intent = Intent(this, AddingActivity::class.java)
                intent.putExtra("operationName", selectedItem)
                intent.putExtra("operationLevel", "beginner")
                intent.putExtra("noOfQuestion", numOfQ)
                startActivity(intent)
            }
            "Subtraction" -> {
                val intent = Intent(this, AddingActivity::class.java)
                intent.putExtra("operationName", selectedItem)
                intent.putExtra("operationLevel", "beginner")
                intent.putExtra("noOfQuestion", numOfQ)
                startActivity(intent)
            }
            "Arithmetic Sequence" -> {
                val intent = Intent(this, ArithmeticSequenceActivity::class.java)
                intent.putExtra("level", "beginner")
                intent.putExtra("noOfQuestion", numOfQ)
                startActivity(intent)
            }
            "Inequality" -> {
                val intent = Intent(this, ComparisonActivity::class.java)
                intent.putExtra("level", "beginner")
                intent.putExtra("noOfQuestion", numOfQ)
                startActivity(intent)
            }
            "Write a letter" ->{
                typeLetterOrNumber ="letter"
                numOfQuestion = numOfQ
                binding.beginnerOrProf.visibility = VISIBLE

            }
            "Write a number" ->{
                typeLetterOrNumber ="number"
                numOfQuestion = numOfQ
                binding.beginnerOrProf.visibility = VISIBLE
            }
            "Sentence Combining" ->{
                val intent = Intent(this, SentenceMainActivity::class.java)
                intent.putExtra("mode","combining")
                intent.putExtra("noOfQuestion", numOfQ)
                startActivity(intent)
            }
            "Complete" ->{
                val intent = Intent(this, SentenceMainActivity::class.java)
                intent.putExtra("noOfQuestion", numOfQ)
                intent.putExtra("mode","completing")
                startActivity(intent)
            }
            else -> {
                //
            }
    }
}
    private fun isAllFieldsFilled(): Boolean {
        val q1 = binding.autoCompleteQ1.text.toString()
        val q2 = binding.autoCompleteQ2.text.toString()
        val q3 = binding.autoCompleteQ3.text.toString()
        val q4 = binding.autoCompleteQ4.text.toString()

        showError(q1,binding.inputLayoutQ1)
        showError(q2,binding.inputLayoutQ2)
        showError(q3,binding.inputLayoutQ3)
        showError(q4,binding.inputLayoutQ4)




        return q1.isNotEmpty() && q2.isNotEmpty() && q3.isNotEmpty() && q4.isNotEmpty()

}
    private fun showError(option:String,inputLayout: TextInputLayout){
        if (option.isEmpty()){
        inputLayout.error = "This field is required"}
        else{
            inputLayout.error = null
        }
    }
    private fun setTheList(type:String):List<String>{

       return when (type) {
            "Dyslexia" -> {
                listOf("Adding","Subtraction","Arithmetic Sequence","Inequality")

            }
            "Dyscalculia" -> {
                 listOf("Adding","Subtraction","Arithmetic Sequence","Inequality")

            }
            "Dysgraphia" -> {
                listOf("Write a letter","Write a number","Sentence Combining","Complete")
            }
            else ->{

                emptyList()
            }
       }
}

    private fun setTest(){
        val q1Type = binding.autoCompleteQ1.text.toString()
        val q2Type = binding.autoCompleteQ2.text.toString()
        val q3Type = binding.autoCompleteQ3.text.toString()
        val q4Type = binding.autoCompleteQ4.text.toString()
        val name = binding.nameOfTest.text.toString()
        val type = binding.typeOfTest.text.toString()
        val listOfQuestions = listOf(
            Question(q1Type,pref.getQ1()!!),
            Question(q2Type,pref.getQ2()!!),
            Question(q3Type,pref.getQ3()!!),
            Question(q4Type,pref.getQ4()!!)
        )
        test = Test(name,type,listOfQuestions)
        Tests.tests.add(test)
      //  pref.resetQuestions()
    }

    private fun setRandomLetterOrNumber(type:String,numOfQuestion:Int){
        val index: Int
        if(type=="letter"){
            val size = LettersAndNumbers.letters.size
             index = (0 until size).random()
        }
        else{
            val size = LettersAndNumbers.numbers.size
             index = (0 until size).random()
        }
        val intent :Intent = if(level =="beginner"){
            Intent(this, TracingActivity::class.java)
        } else{
            Intent(this, DrawingActivity::class.java)
        }
        intent.putExtra("letterIndex", index)
        intent.putExtra("type", type)
        intent.putExtra("noOfQuestion", numOfQuestion)
        startActivity(intent)
    }
    private fun mangeLevel(){
        binding.backArrowIV.setOnClickListener {
            binding.beginnerOrProf.visibility = GONE
        }
        binding.proIV.setOnClickListener {
            level = "pro"
            binding.beginnerOrProf.visibility = GONE
            setRandomLetterOrNumber(typeLetterOrNumber,numOfQuestion)
        }
        binding.beginnerIV.setOnClickListener {
            level = "beginner"
            binding.beginnerOrProf.visibility = GONE
            setRandomLetterOrNumber(typeLetterOrNumber,numOfQuestion)
        }
    }
}