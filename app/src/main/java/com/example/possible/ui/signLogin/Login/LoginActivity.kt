package com.example.possible.ui.signLogin.Login

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.possible.databinding.ActivityLoginBinding
import com.example.possible.model.User
import com.example.possible.repo.local.SharedPref
import com.example.possible.ui.MainActivity
import com.example.possible.ui.signLogin.signUp.SignupActivity
import com.example.possible.ui.specialist.SpecialistMainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityLoginBinding
    private var apiResult = false
    private lateinit var user: User
    private lateinit var pref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = SharedPref(this)
        setControllers()

    }
    private fun setPathOfApp(path:String){
        pref.setPath(path)
        hideTheChoosingViewAndViewTheLoginView()
    }
    private fun hideTheChoosingViewAndViewTheLoginView(){
        binding.loginView.visibility = VISIBLE
        binding.loginView.animate().scaleX(0f).scaleY(0f).setDuration(0).start()
        binding.chooseParentOrSpecialist.animate().scaleX(0f).scaleY(0f).setDuration(300).withEndAction{
            binding.loginView.animate().scaleX(1f).scaleY(1f).setDuration(300).start()
            binding.chooseParentOrSpecialist.visibility = GONE
        }.start()
    }
    private fun setControllers(){
        binding.signup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
        binding.loginBtn.setOnClickListener {
           uiLogin()
        }
        binding.chooseParentBtn.setOnClickListener {
            setPathOfApp("parent")

        }
        binding.chooseSpecialistBtn.setOnClickListener {
            setPathOfApp("specialist")
        }
    }
    private fun uiLogin(){
            if(resultFromApi()){
                saveData()
                goToMainActivity()
            }
            else{
                Toast.makeText(this, "You have not any account you can signup", Toast.LENGTH_SHORT).show()
            }
        }

    private fun resultFromApi():Boolean{
        apiResult = false
       return apiResult
    }
    private fun goToMainActivity(){
        if(pref.getPath()=="parent"){
            startActivity(Intent(this, MainActivity::class.java))
        }
        else if(pref.getPath()=="specialist"){
            startActivity(Intent(this, SpecialistMainActivity::class.java))
        }
        finish()


    }
    private fun saveData(){
        saveLogin(true)
        saveUser("","","","")

    }
    private fun saveLogin(login:Boolean){

    }
    private fun saveUser(image:String,name:String,email:String,password:String) {

    }


}