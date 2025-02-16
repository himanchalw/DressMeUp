package com.example.dressmeup

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.dressmeup.databinding.SearchPageBinding
import java.util.Locale

class NewSearchActivity:AppCompatActivity() {
    private lateinit var binding: SearchPageBinding
    private val REQUEST_CODE_SPEECH_INPUT = 100
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var editText: EditText
    private lateinit var statusText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= SearchPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        editText=binding.edtSearch
        statusText=binding.edtStatus
        val btnStartSpeech=binding.btnMic
        btnStartSpeech.setOnClickListener{
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
                // Permission already granted
                startListening()
            } else {
                // Request permission
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_CODE_SPEECH_INPUT)
            }
        }
        speechRecognizer=SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(object:RecognitionListener{
            override fun onReadyForSpeech(params: Bundle?) {
                btnStartSpeech.setOnClickListener{
                    btnStartSpeech.setBackgroundColor(R.drawable.mic_button_glow)
                }
            }

            override fun onBeginningOfSpeech() {
                editText.setText("")
                statusText.text = "Listening..."
            }

            override fun onRmsChanged(rmsdB: Float) {

            }

            override fun onBufferReceived(buffer: ByteArray?) {

            }

            override fun onEndOfSpeech() {
                statusText.text = "Speech Completed"
            }

            override fun onError(error: Int) {
                statusText.text = error.toString()
//                Toast.makeText( this@NewSearchActivity,error,Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
                val matches=results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    editText.setText(matches[0]) // Set text from speech
                }
                statusText.text = "Speech Completed"
            }

            override fun onPartialResults(partialResults: Bundle?) {
                val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    runOnUiThread {
                        editText.setText(matches[0])  // ✅ Update UI safely on the main thread
                    }
                }
            }

            override fun onEvent(eventType: Int, params: Bundle?) {

            }
        })
    }
    private fun startListening(){
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        speechRecognizer.startListening(intent)
    }
    override fun onDestroy() {
        speechRecognizer.destroy()
        super.onDestroy()
    }
}