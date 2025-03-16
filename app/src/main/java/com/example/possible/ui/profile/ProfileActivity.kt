package com.example.possible.ui.profile

import DialogBuilder
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.possible.databinding.ActivityProfileBinding
import com.example.possible.repo.local.SharedPref
import com.example.possible.ui.profile.children.addChild.AddChildActivity
import com.example.possible.ui.profile.children.Children
import com.example.possible.ui.profile.children.testsForChildren.ChildrenTests
import com.example.possible.ui.profile.profileManage.EditProfileActivity
import com.example.possible.util.helper.dataManager.AppDataManager
import kotlin.system.exitProcess


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPreferences: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProfileBinding.inflate(layoutInflater)
        sharedPreferences = SharedPref(this)


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setControllers()
        setContentView(binding.root)
        setView()
        isSpecialistView()
    }
    private fun setControllers(){
        binding.myAccountLL.setOnClickListener {
            animateBtn(binding.myAccountLL
            ) {
                startActivity(Intent(this, EditProfileActivity::class.java))
            }
        }
        binding.addChildLL.setOnClickListener {
            animateBtn(binding.addChildLL
            ) { val intent = Intent(this, AddChildActivity::class.java)
                intent.putExtra("mode", "add")
                startActivity(intent)
            }
        }
        binding.childrenLL.setOnClickListener {
            animateBtn(binding.childrenLL){
                val intent = Intent(this, Children::class.java)
                intent.putExtra("mode", "view")
            startActivity(intent)
            }

        }
        binding.logoutLL.setOnClickListener {
            animateBtn(binding.logoutLL){
                DialogBuilder.showAlertDialog(this,
                    "Are you sure you want to logout?",
                    "Logout",
                    "Sure",
                    "Cancel",
                    {
                        logout()
                        finish()
                    },
                    {
                        //nothing
                    }
                )
            }

        }
        binding.testsLL.setOnClickListener {
            animateBtn(binding.testsLL){
                startActivity(Intent(this, ChildrenTests::class.java))
            }
        }
        binding.backArrowIV.setOnClickListener{
            finish()
        }
    }
    private fun  setView(){

    }


    override fun onResume() {
        super.onResume()
        AppDataManager.viewProfileImage(binding.profileImage,sharedPreferences,this)
        val user=sharedPreferences.getProfileDetails()
        binding.userName.text=user.name
    }
    private fun animateBtn(b: View,unit:()->Unit){
        b.animate().scaleY(1.2f).scaleX(1.2f).setDuration(150).withEndAction{
            b.animate().scaleX(1f).scaleY(1f).setDuration(150).withEndAction{
               unit()
            }
        }.start()

    }
    private fun logout() {
        clearAppDataAndRestart(this)
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
        finish()
    }
    private fun isSpecialistView(){
        if(sharedPreferences.getRole()=="Specialist"){
            binding.addChildLL.visibility=View.GONE
            binding.childrenLL.visibility=View.GONE
            binding.testsLL.visibility=View.GONE
        }
    }


    @SuppressLint("ServiceCast")
    fun clearAppDataAndRestart(context: Context) {
        try {
            // Clear app data
            (context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager)?.clearApplicationUserData()

            // Restart the app
            val restartIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            restartIntent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(restartIntent)
            (context as? Activity)?.finish()

            // Kill the app process
            exitProcess(0)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }






}