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
import com.example.possible.ui.MainActivity
import com.example.possible.ui.signLogin.signUp.SignupActivity
import com.example.possible.util.LoginChecker

class LoginActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityLoginBinding
    private var apiResult = false
    private lateinit var user: User
    private lateinit var pathOfApp:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setControllers()

    }
    private fun setPathOfApp(path:String){
        pathOfApp=path
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
        if(checkIfAllFieldsAreValid()){
            if(resultFromApi()){
                saveData()
                goToMainActivity()
            }
            else{
                Toast.makeText(this, "You have not any account you can signup", Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun checkIfAllFieldsAreValid():Boolean{
        var result=true
        val email=binding.emailET.text.toString()
        val password=binding.passwordET.text.toString()
       if(!LoginChecker.isValidEmail(email)){
           result = false
           binding.emailET.error = "Invalid Email"
       }
       if(!LoginChecker.passwordMoreThan8(password)){
           result = false
           binding.passwordET.error = "Password must be more than 8"
       }
        if(LoginChecker.isValidEmail(email)&&LoginChecker.passwordMoreThan8(password)){
            result = true
        }
        return result
    }
    private fun resultFromApi():Boolean{
        apiResult = false
       return apiResult
    }
    private fun goToMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
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