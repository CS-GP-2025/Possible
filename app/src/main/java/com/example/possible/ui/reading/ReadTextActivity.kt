package com.example.possible.ui.reading

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.possible.R
import com.example.possible.databinding.ActivityReadTextBinding
import com.example.possible.util.adapter.TextsAdapter
import com.example.possible.util.listener.TextListener
import java.util.Locale

class ReadTextActivity : AppCompatActivity(), TextListener {
    private lateinit var binding: ActivityReadTextBinding
    private lateinit var pulseAnimator: ObjectAnimator
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var adapter: TextsAdapter
    private var actualText = ""
    private var speechText = ""
    private var isRecording = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadTextBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupRecyclerView()
        checkPermission()
        setupSpeechRecognizer()
        setupClickListeners()
    }

    /** ✅ إعداد قائمة النصوص */
    private fun setupRecyclerView() {
        val texts = arrayListOf(
            "Hello how are you", "I am fine thank you", "My name is John", "I am 25 years old",
            "I live in New York", "It is sunny and warm today", "I love to read books"
        )
        adapter = TextsAdapter(texts, this)
        binding.recyclerView.adapter = adapter
    }

    /** ✅ التأكد من صلاحية الوصول للمايكروفون */
    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 1)
        }
    }

    /** ✅ تجهيز محرك التعرف على الصوت */
    private fun setupSpeechRecognizer() {
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, "Speech recognition not supported", Toast.LENGTH_LONG).show()
            return
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                showListeningUI()
            }

            override fun onEndOfSpeech() {
                stopListeningUI()
                if (isRecording) speechRecognizer.startListening(getSpeechIntent())
            }

            override fun onResults(results: Bundle?) {
                speechText = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.get(0) ?: ""
                if (isRecording) speechRecognizer.startListening(getSpeechIntent())
            }

            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onError(error: Int) {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onBeginningOfSpeech() {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    /** ✅ تجهيز الـ Intent الخاص بالتعرف على الصوت */
    private fun getSpeechIntent(): Intent {
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something")
        }
    }

    /** ✅ ربط الأزرار بالأحداث */
    private fun setupClickListeners() {
        binding.recordBtn.setOnClickListener {
            isRecording = !isRecording
            if (isRecording) {
                speechRecognizer.startListening(getSpeechIntent())
                startPulseAnimation()
            } else {
                speechRecognizer.stopListening()
                stopListeningUI()
                goToResult()
            }
        }

        binding.backArrowIV.setOnClickListener { finish() }
    }

    /** ✅ تشغيل تأثير النبض عند بدء التسجيل */
    private fun startPulseAnimation() {
        pulseAnimator = ObjectAnimator.ofPropertyValuesHolder(
            binding.recordSignals,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 1.2f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.2f)
        ).apply {
            duration = 500
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
            start()
        }
    }

    /** ✅ تغيير الواجهة عند بدء التسجيل */
    private fun showListeningUI() {
        Toast.makeText(this, "Listening...", Toast.LENGTH_SHORT).show()
        binding.hint.animate().alpha(0f).setDuration(200).withEndAction {
            binding.hint.visibility = View.GONE
            binding.recordSignals.visibility = View.VISIBLE
            binding.hintCard.alpha = 0f
            binding.recordSignals.alpha = 0f
            binding.hintCard.animate().alpha(1f).setDuration(200).withEndAction {
                binding.recordSignals.animate().alpha(1f).setDuration(150).withEndAction {
                    binding.recordSignals.animate().scaleY(0.1f).setDuration(150).withEndAction {
                        binding.recordSignals.animate().scaleY(1f).setDuration(300)
                    }.start()
                }.start()
            }.start()
        }.start()
    }

    /** ✅ تغيير الواجهة عند التوقف عن التسجيل */
    private fun stopListeningUI() {
        Toast.makeText(this, "Stopped Listening", Toast.LENGTH_SHORT).show()
        binding.recordBtn.animate().scaleY(1f).scaleX(1f).setDuration(100).withEndAction {
            binding.recordBtn.setBackgroundResource(R.drawable.is_not_recording)
        }.start()
        pulseAnimator.cancel()
    }

    /** ✅ فتح شاشة النتيجة */
    private fun goToResult() {
        val intent = Intent(this, ReadingResult::class.java).apply {
            putExtra("actualText", actualText)
            putExtra("speechText", speechText)
        }
        startActivity(intent)
    }

    /** ✅ إنهاء التعرف على الصوت عند إغلاق النشاط */
    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
    }

    /** ✅ تحديد النص عند الضغط عليه */
    override fun onClick(text: String) {
        binding.theMainView.visibility = View.VISIBLE
        binding.actualText.text = text
        actualText = text
        binding.dummyView.visibility = View.GONE
    }
}
