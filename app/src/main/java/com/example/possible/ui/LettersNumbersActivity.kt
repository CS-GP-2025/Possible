package com.example.possible.ui

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.possible.model.Letter
import com.example.possible.util.adapter.LettersAdapter
import com.example.possible.repo.local.LettersAndNumbers
import com.example.possible.databinding.ActivityLettersNumbersBinding
import com.example.possible.repo.local.SharedPref
import com.example.possible.ui.drawing.DrawingActivity
import com.example.possible.ui.tracing.TracingActivity
import com.example.possible.util.listener.LettersListener

class LettersNumbersActivity : AppCompatActivity() , LettersListener {
    private lateinit var binding: ActivityLettersNumbersBinding
    private lateinit var list: List<Letter>
    private lateinit var title: String
    private lateinit var pref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (intent.extras?.getString("result")=="letters"){
            list= LettersAndNumbers.letters
             title="Letters"
        }else{
            list= LettersAndNumbers.numbers
            title="Numbers"
        }
        binding = ActivityLettersNumbersBinding.inflate(layoutInflater)
        pref = SharedPref(this)


        binding.title.text=title
        val recyclerView=binding.recycler
        val gridLayoutManager = GridLayoutManager(this, 2) // Number of columns
        recyclerView.layoutManager = gridLayoutManager

// Add item spacing decoration to align items properly
       recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                parent.getChildAdapterPosition(view)
                gridLayoutManager.spanCount
                state.itemCount

                // Apply equal padding around items to center-align them
                val spacing = 50 // Adjust as needed
                outRect.left = spacing / 2
                outRect.right = spacing / 2
                outRect.top = spacing / 2
                outRect.bottom = spacing / 2
            }
        })
        recyclerView.adapter = LettersAdapter(list,this)



        binding.backArrowIV.setOnClickListener{
            this.finish()
        }

        setContentView(binding.root)
    }

    override fun OnClick(letter: Letter, position: Int) {
        val intent1 = Intent(this, TracingActivity::class.java)
        intent1.putExtra("letterIndex",position)
        intent1.putExtra("type",letter.type)
        val intent = Intent(this, DrawingActivity::class.java)
        intent.putExtra("letterIndex", position)
        intent.putExtra("type",letter.type)
        if(pref.getMode()=="beginner")
            startActivity(intent1)
        else
            startActivity(intent)
    }
    private fun loadProfileImage() {
        val savedUri = pref.getImage()
        if (savedUri != null) {
            val uri = Uri.parse(savedUri)
            binding.profileIV.setImageURI(uri)
        }}

    override fun onResume() {
        super.onResume()
        loadProfileImage()
        val userName=pref.getProfileDetails().getName()
        binding.userNameTV.text=userName
    }
}