package com.example.possible.ui.splach

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.possible.R
import com.example.possible.databinding.ActivityAnimationBinding
import com.example.possible.ui.MainActivity

class AnimationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimationBinding
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAnimationBinding.inflate(layoutInflater)
        handler = Handler()
        setContentView(binding.root)
        binding.smallRocket.animate().scaleX(0f).scaleY(0f).setDuration(1000).start()
        hideAnimation(binding.smallRocket)
        hideAnimation(binding.rideRocket)
        hideAnimation(binding.title)
        animate()
        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
        },3000)


    }

    private fun animate(){
        handler.postDelayed({
            val animator = ObjectAnimator.ofFloat(binding.rocket, "translationY", 0f, -5000f)
            animator.duration = 3000 // مدة الحركة (2 ثانية)
            animator.start()
        },500)

    }
    private fun hideAnimation(view: View){
        handler.postDelayed({
            view.animate().scaleX(0f).scaleY(0f).setDuration(3000).withEndAction{
                view.visibility= View.GONE
            }.start()
        },1500)
    }
}