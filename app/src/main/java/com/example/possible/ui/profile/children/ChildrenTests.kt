package com.example.possible.ui.profile.children

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.possible.R
import com.example.possible.databinding.ActivityChildrenTestsBinding
import com.example.possible.model.Child
import com.example.possible.repo.local.database.LocalRepoImp
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChildrenTestsBinding.inflate(layoutInflater)
        children= arrayListOf()
        adapter = ChildrenAdapter(children, this)
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

    override fun onClick(child: Child) {
        TODO("Not yet implemented")
    }

    override fun onDelete(position: Int, child: Child) {
        TODO("Not yet implemented")
    }
}