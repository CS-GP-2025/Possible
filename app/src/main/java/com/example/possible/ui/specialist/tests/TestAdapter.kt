package com.example.possible.ui.specialist.tests

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.possible.databinding.TestItemBinding
import com.example.possible.model.Test
import com.example.possible.util.listener.TestListener

class TestAdapter(private val data: ArrayList<Test>,val listener: TestListener) :
    RecyclerView.Adapter<TestAdapter.TestHolder>() {

    // Create ViewHolder class
    class TestHolder(val binding: TestItemBinding) : RecyclerView.ViewHolder(binding.root)

    // Create ViewHolder and inflate the item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHolder {
        val binding = TestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TestHolder(binding)
    }

    // Bind data to the ViewHolder
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TestHolder, position: Int) {
        holder.binding.testName.text = data[position].name
        holder.binding.testId.text = (position+1).toString()
        holder.binding.root.setOnClickListener{
            listener.onTestClick(data[position],position)
        }
        holder.binding.deleteBtn.setOnClickListener {
            listener.onDeleteClick(data[position],position)
        }
    }

    // Return the size of the data list
    override fun getItemCount(): Int {
        return data.size
    }
}