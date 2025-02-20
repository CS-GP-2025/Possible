package com.example.possible.ui.specialist.tests

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.possible.R
import com.example.possible.databinding.ActivityTestsBinding
import com.example.possible.model.Test
import com.example.possible.ui.test.TestActivity
import com.example.possible.ui.test.dysgraphiaTest.DysgraphiaTestActivity
import com.example.possible.util.Tests
import com.example.possible.util.listener.TestListener

class TestsActivity : AppCompatActivity() , TestListener {
    private lateinit var binding: ActivityTestsBinding
    private lateinit var adapter : TestAdapter
    private var testList = ArrayList<Test>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTestsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        testList = Tests.tests
        adapter = TestAdapter(testList,this)
        setView()
    }

    private fun setView(){
        binding.backArrowIV.setOnClickListener {
            finish()
        }
        if(testList.isEmpty()){
            binding.empty.visibility =VISIBLE
            binding.recycler.visibility =GONE

        }
        else{
            binding.empty.visibility = GONE
            binding.recycler.visibility = VISIBLE
            binding.recycler.adapter = adapter}
    }

    override fun onTestClick(test: Test, position: Int) {
        if(test.name=="BBB"){
            val intent = Intent(this, DysgraphiaTestActivity::class.java)
            intent.putExtra("test",position)
            startActivity(intent)
        }
        else{
            val intent = Intent(this, TestActivity::class.java)
            intent.putExtra("test",position)
            startActivity(intent)
        }
    }

    override fun onDeleteClick(test: Test, position: Int) {
        TODO("Not yet implemented")
    }
}