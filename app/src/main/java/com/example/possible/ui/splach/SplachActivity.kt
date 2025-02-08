package com.example.possible.ui.splach

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.possible.databinding.ActivitySplachBinding
import com.example.possible.repo.local.SharedPref
import com.example.possible.ui.MainActivity
import com.example.possible.ui.signLogin.Login.LoginActivity

class SplachActivity : AppCompatActivity(){
    private lateinit var binding: ActivitySplachBinding

      private lateinit var p: View

      private lateinit var o: View

      private lateinit var s: View

      private lateinit var s2: View

      private lateinit var i: View

      private lateinit var b: View

      private lateinit var l: View

      private lateinit var e: View

      private lateinit var brain: View
      private lateinit var pref: SharedPref

    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        handler = Handler()
        binding = ActivitySplachBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = SharedPref(this)

        p=binding.p
        o=binding.o
        s=binding.s
        s2=binding.s2
        i=binding.i
        b=binding.b
        l=binding.l
        e=binding.e
        brain = binding.brainImg

        hideWord()
        handler.postDelayed({
            animateText()
        },1000)

        handler.postDelayed({
            if(isLogin()){
                goToMainActivity()
            }
            else{
                goToLoginActivity()
            }
        },6000)
    }

    private fun animateText() {
        animateLetter(b, 6, 100)
        animateLetter(o, 2,800 )
        animateLetter(s, 10, 100)
        animateLetter(s2, 20, 50)
        animateLetter(i, 30, 10)
        animateLetter(p, 1, 1000)
        animateLetter(l, 6, 500)
        animateLetter(e, 3, 1000)
        animateLetter(brain, 2, 1500)
    }
    private fun animateLetter(view: View, times: Int, delay: Long) {
        fun startAnimation(count: Int) {
            if (count < times) {
                view.animate().alpha(1f).setDuration(delay).withEndAction {
                    view.animate().alpha(0f).setDuration(delay + 100).withEndAction {
                        startAnimation(count + 1) // استدعاء الأنيميشن مرة أخرى حتى ينتهي العدد المطلوب
                    }.start()
                }.start()
            } else {
                view.animate().alpha(1f).setDuration(delay).start() // التأكد أن الـ View يظهر بعد انتهاء التكرار
            }
        }

        startAnimation(0) // تشغيل الأنيميشن أول مرة
    }

    private fun hideWord(){
        b.animate().alpha(0f).setDuration(2000).start()
        l.animate().alpha(0f).setDuration(1500).start()
        e.animate().alpha(0f).setDuration(1000).start()
        s.animate().alpha(0f).setDuration(500).start()
        s2.animate().alpha(0f).setDuration(1000).start()
        i.animate().alpha(0f).setDuration(300).start()
        o.animate().alpha(0f).setDuration(250).start()
        p.animate().alpha(0f).setDuration(1000).start()
        brain.animate().alpha(0f).setDuration(1000).start()

    }
    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun isLogin():Boolean{
        return pref.getLogin()
    }



}