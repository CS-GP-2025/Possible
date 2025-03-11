package com.example.possible.ui.test.dyscalculiaTest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.possible.R
import com.example.possible.databinding.FragmentArithmeticBinding
import com.example.possible.util.TestDecoder

class ArithmeticFragment : Fragment() {
    private lateinit var binding: FragmentArithmeticBinding
    private lateinit var sequence : List<Int>
    private var firstResult = 0
    private var secondResult = 0
    fun getInstance(seq:String): ArithmeticFragment{
        val fragment=ArithmeticFragment()
        val bundle=Bundle()
        bundle.putString("seq",seq)
        fragment.arguments=bundle
        return fragment
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentArithmeticBinding.inflate(layoutInflater,container,false)
        val seq=arguments?.getString("seq")
        sequence = TestDecoder.decodeArithmeticSequence(seq!!)
        setViwOfQuestion()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun setViwOfQuestion(){
        binding.first.text = sequence[0].toString()+" ,   "
        binding.second.text = sequence[1].toString()+" ,  "
        binding.third.text = sequence[2].toString()+" ,  "
        binding.fivth.text = sequence[4].toString()+" ,  "
        binding.seventh.text = " ,  "+sequence[6].toString()
        firstResult = sequence[3]
        secondResult = sequence[5]
    }
     fun getResults():Int{
        var result = 0
        val firstInput = if(binding.fourth.text.toString().isNotEmpty()){
            binding.fourth.text.toString().toInt()
        } else{
            0
        }
        val secondInput = if(binding.sixth.text.toString().isNotEmpty()){
            binding.sixth.text.toString().toInt()
        } else{
            0
        }
        if(firstInput == firstResult && secondInput == secondResult){
            result = 2
        }
        else if(firstInput == firstResult || secondInput == secondResult){
            result = 1
        }
        return result
    }

}