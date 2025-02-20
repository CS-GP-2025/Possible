package com.example.possible.repo.local

import android.content.Context
import com.example.possible.model.User

class SharedPref(context: Context) {
    private val sharedPref=context.getSharedPreferences("mode",Context.MODE_PRIVATE)
    private val profilePref=context.getSharedPreferences("profile",Context.MODE_PRIVATE)
    private val appPathPref=context.getSharedPreferences("path",Context.MODE_PRIVATE)
    private val testPref=context.getSharedPreferences("test",Context.MODE_PRIVATE)


    fun setImage(uri:String){
        profilePref.edit().putString("imageUri",uri).apply()
    }
    fun getImage():String?{
        return profilePref.getString("imageUri","")
    }

    fun setProfileData(name:String, email:String, password:String, login:Boolean){
        profilePref.edit().putString("name",name).apply()
        profilePref.edit().putString("email",email).apply()
        profilePref.edit().putString("password",password).apply()
        profilePref.edit().putBoolean("login",login).apply()
    }
    fun getProfileDetails(): User {
        val name=profilePref.getString("name","")
        val email=profilePref.getString("email","")
        val password=profilePref.getString("password","")
        val login=profilePref.getBoolean("login",false)
        val imageUri=profilePref.getString("imageUri","")
        return User(name!!,login,imageUri!!,email!!,password!!)
    }
    fun getLogin():Boolean{
        return profilePref.getBoolean("login",false)
    }


    fun setMode(mode:String){
        sharedPref.edit().putString("mode",mode).apply()
    }
    fun getMode():String?{
        return sharedPref.getString("mode","beginner")
    }

    //path of app
    fun setPath(path:String){
        appPathPref.edit().putString("path",path).apply()
    }
    fun getPath():String?{
        return appPathPref.getString("path","parent")
    }

    //test
    fun setQ1(q1:String){
        testPref.edit().putString("q1",q1).apply()
    }
    fun getQ1():String?{
        return testPref.getString("q1","")
    }
    fun setQ2(q2:String){
        testPref.edit().putString("q2",q2).apply()
    }
    fun getQ2():String?{
        return testPref.getString("q2","")
    }
    fun setQ3(q3:String){
        testPref.edit().putString("q3",q3).apply()
    }
    fun getQ3():String?{
        return testPref.getString("q3","")}
    fun setQ4(q4:String){
        testPref.edit().putString("q4",q4).apply()
    }
    fun getQ4():String?{
        return testPref.getString("q4","")
    }

    fun resetQuestions(){
        testPref.edit().putString("q1","").apply()
        testPref.edit().putString("q2","").apply()
        testPref.edit().putString("q3","").apply()
        testPref.edit().putString("q4","").apply()
    }

}