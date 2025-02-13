package com.example.dressmeup

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.dressmeup.databinding.LoginPageBinding
import com.example.dressmeup.databinding.SearchPageBinding
import java.util.Locale

class SearchActivity:AppCompatActivity() {
    private lateinit var binding:SearchPageBinding
    private val REQUEST_CODE_SPEECH_INPUT = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= SearchPageBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        binding.btnMic.setOnClickListener{
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
                // Permission already granted
                startMicRecording()
            } else {
                // Request permission
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_CODE_SPEECH_INPUT)
            }

        }
    }
    fun startMicRecording(){
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something...")
        startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!result.isNullOrEmpty()) {
                binding.edtSearch.setText(result[0])
            }
        }
    }
//    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this, "Back button is disabled", Toast.LENGTH_SHORT).show()
    }
}