package com.example.dressmeup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dressmeup.databinding.LoginPageBinding



class LoginActivity:AppCompatActivity() {
    private val loginId:String="login"
    private val password:String="123"
    private lateinit var binding: LoginPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=LoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgNextButton.setOnClickListener{
            val idInput:String=binding.edtLogin.text.toString()
            val passwordInput=binding.edtPassword.text.toString()
            if(idInput.isNotBlank()and passwordInput.isNotBlank()){

                if(idInput==loginId && passwordInput==password){
                    startActivity(Intent(this, NewSearchActivity::class.java))
                }else{
                    Toast.makeText(this, "Wrong credentials entered", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this, "Back button is disabled", Toast.LENGTH_SHORT).show()
    }
}