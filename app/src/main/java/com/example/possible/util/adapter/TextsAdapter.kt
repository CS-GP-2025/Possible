package com.example.possible.util.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.possible.databinding.ChosenTextCardBinding
import com.example.possible.util.listener.TextListener

class TextsAdapter(private val data: ArrayList<String>, private val listener: TextListener) :
    RecyclerView.Adapter<TextsAdapter.Tholder>() {

    // Create ViewHolder class
    class Tholder(val binding: ChosenTextCardBinding) : RecyclerView.ViewHolder(binding.root)

    // Create ViewHolder and inflate the item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Tholder {
        val binding = ChosenTextCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Tholder(binding)
    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: Tholder, position: Int) {
       holder.binding.text.text = data[position]
        holder.binding.root.setOnClickListener{
            listener.onClick(data[position])
        }
    }

    // Return the size of the data list
    override fun getItemCount(): Int {
        return data.size
    }
}