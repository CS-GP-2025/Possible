package com.example.possible.ui.tracing
import android.net.Uri
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.possible.databinding.ActivityTracingBinding
import com.example.possible.repo.local.LettersAndNumbers
import com.example.possible.repo.local.SharedPref
import com.example.possible.util.TestDecoder

class TracingActivity : AppCompatActivity() {
    private var index=0
    private var numOfQuestion=0
    private var type=""
    private var pathsOfNumber=0
    private var ofCompletedPaths=0
    private lateinit var fragment:TracingFragment
    private lateinit var binding: ActivityTracingBinding
    private lateinit var pref:SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTracingBinding.inflate(layoutInflater)
        pref= SharedPref(this)

         index=intent.extras?.getInt("letterIndex",0)!!
         type=intent.extras?.getString("type",null)!!
         fragment= TracingFragment().getInstance(index,type)
        setFrame(fragment)


        binding.doneButton.setOnClickListener{
            if(isItFromSettingTest()){
                getTheQuestionOnDone(numOfQuestion)
                finish()
            }
            else{
            pathsOfNumber=fragment.getResult().first
            ofCompletedPaths=fragment.getResult().second
            if(pathsOfNumber==ofCompletedPaths){
                binding.celeprationView.visibility=VISIBLE
                binding.celeprationAnim.playAnimation()
                binding.doneButton.isEnabled=false
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Continue", Toast.LENGTH_SHORT).show()
            }}
        }

        binding.nextBtn.setOnClickListener {
            if(type=="letter"){
                if(index!= LettersAndNumbers.letters.size-1){
                    //go to next letter
                    setNextData(index +1,"letter")
                }
                else
                {
                    this.finish()
                }
            }
            else{
                if(index!= LettersAndNumbers.numbers.size-1){
                    //go to next number
                        setNextData(index+1,"number")

                }
                else
                {
                    this.finish()
                }
            }

        }
        binding.backArrowIV.setOnClickListener {
            this.finish()
        }
        setContentView(binding.root)

}
    private fun setNextData(index:Int, type:String){
        this.index=index
        this.type=type
        binding.nextBtn.isEnabled=false
        binding.nextBtn.postDelayed({
            binding.nextBtn.isEnabled=true}
            ,1000)
        binding.celeprationView.visibility= GONE
        binding.doneButton.isEnabled=true
        fragment=TracingFragment().getInstance(index,type)
        setFrame(fragment)
    }
    private fun setFrame(fragment:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(binding.frameDrawer.id,fragment)
            .commit()
    }
    private fun loadProfileImage() {
        val savedUri = pref.getImage()
        if (savedUri != null) {
            val uri = Uri.parse(savedUri)
            binding.profileIV.setImageURI(uri)
        }}

    override fun onResume() {
        super.onResume()
        loadProfileImage()
        val name=pref.getProfileDetails().getName()
        binding.userNameTV.text=name
    }
    private fun isItFromSettingTest():Boolean{
        numOfQuestion = intent.getIntExtra("noOfQuestion",0)
        return numOfQuestion!=0

    }

    private fun getTheQuestionOnDone(numOfQuestion:Int){
        val ques = TestDecoder.encodeLetterOrNumber(index,type,"beginner")
        setWhichQuestion(ques,numOfQuestion)
    }
    private fun setWhichQuestion(ques:String,numOfQuestion:Int){
        when(numOfQuestion){
            1->{
                pref.setQ1(ques)
            }
            2->{
                pref.setQ2(ques)
            }
            3->{
                pref.setQ3(ques)
            }
            4->{
                pref.setQ4(ques)
            }
        }
    }
}