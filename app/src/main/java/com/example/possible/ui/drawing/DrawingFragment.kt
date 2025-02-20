package com.example.possible.ui.drawing

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.possible.databinding.FragmentDrawingBinding
import com.example.possible.repo.local.LettersAndNumbers


class DrawingFragment : Fragment() {
    lateinit var binding: FragmentDrawingBinding
    private var floatStartX = 0f
    private var floatStartY = 0f
    private var floatEndX = 0f
    private var floatEndY = 0f
    private var bitmap: Bitmap? = null
    private var canvas: Canvas? = null
    private val paint = Paint()
    private var imageView: ImageView? = null


    fun getInstance(index:Int,type:String): DrawingFragment {
        val fragment = DrawingFragment()
        val args = Bundle()
        args.putInt("index",index)
        args.putString("type",type)
        fragment.arguments = args
        return fragment
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDrawingBinding.inflate(inflater, container, false)
        imageView=binding.bitmap
        binding.root.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    floatStartX = event.x
                    floatStartY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    floatEndX = event.x
                    floatEndY = event.y
                    drawPaintSketchImage() // Implement your drawing logic here
                    floatStartX = event.x
                    floatStartY = event.y
                }
                MotionEvent.ACTION_UP -> {
                    floatEndX = event.x
                    floatEndY = event.y
                    drawPaintSketchImage() // Implement your drawing logic here
                }
            }
            true // Return true to indicate the event was handled
        }
        binding.letterName.text= setNameOfLetter(arguments?.getString("type")!!,arguments?.getInt("index")!!)

        return binding.root
    }
    private fun drawPaintSketchImage() {
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(
                imageView!!.width,
                imageView!!.height,
                Bitmap.Config.ARGB_8888
            )
            canvas = Canvas(bitmap!!)
            paint.color = Color.WHITE
            paint.isAntiAlias = true
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 30f
        }
        canvas!!.drawLine(
            floatStartX,
            floatStartY-200,
            floatEndX,
            floatEndY-200 ,
            paint
        )
        imageView!!.setImageBitmap(bitmap)
    }
    private fun setNameOfLetter(type:String,index:Int):String{
        val name =if(type=="letter"){
            LettersAndNumbers.letters[index].name
        }else{
            LettersAndNumbers.numbers[index].name
        }
        return name
    }

}