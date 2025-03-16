package com.example.possible.ui.profile.children.testsForChildren

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.possible.databinding.ActivityChildrenTestsBinding
import com.example.possible.model.Child
import com.example.possible.repo.local.SharedPref
import com.example.possible.repo.local.database.LocalRepoImp
import com.example.possible.ui.report.ReportActivity
import com.example.possible.util.adapter.ChildrenAdapter
import com.example.possible.util.listener.ChildListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChildrenTests : AppCompatActivity(),ChildListener {
    private lateinit var binding: ActivityChildrenTestsBinding
    private lateinit var children : ArrayList<Child>
    private lateinit var adapter : ChildrenAdapter
    private lateinit var db:LocalRepoImp
    private val sharedPref = SharedPref(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChildrenTestsBinding.inflate(layoutInflater)
        children= arrayListOf()
        adapter = ChildrenAdapter(children, this,sharedPref)
        db = LocalRepoImp(this)
        adapter.setTestMode(true)
        getAllChildren()










        setContentView(binding.root)

    }

    private fun getAllChildren(){
        lifecycleScope.launch{
           children = db.getAllChildren() as ArrayList<Child>
            if(children.isNotEmpty()){
                withContext(Dispatchers.Main){
                    adapter.updateData(children)
                    binding.recyclerView.adapter = adapter
                }
            }
            else{
                withContext(Dispatchers.Main){
                    binding.recyclerView.visibility=GONE
                    binding.hint.visibility=VISIBLE
                }
            }
        }
    }

    override fun onClick(child: Child){
        startActivity(Intent(this, ToDoTestsActivity::class.java))
    }

    override fun onDelete(position: Int, child: Child) {
        TODO("Not yet implemented")
    }
}