package com.example.dressmeup

import android.os.Bundle
import android.os.PersistableBundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.dressmeup.databinding.SearchPageBinding

class NewSearchActivity:AppCompatActivity() {
    private lateinit var binding: SearchPageBinding
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var editText: EditText
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding= SearchPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        editText=binding.edtSearch
        speechRecognizer=SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(object:RecognitionListener{
            override fun onResults(results: Bundle?) {
                val matches=results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            }
        })
    }
}