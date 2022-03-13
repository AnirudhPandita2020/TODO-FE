package com.anirudh.todo_fe.actvity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anirudh.todo_fe.databinding.ActivitySignupactivtyBinding
import com.google.android.material.snackbar.Snackbar

class Signupactivty : AppCompatActivity() {
    private lateinit var binding:ActivitySignupactivtyBinding
    lateinit var token:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupactivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.registerbtn.setOnClickListener {
            val username = binding.usernameinput.text.toString()
            val email = binding.newemailinput.text.toString()
            val password = binding.newpassword.text.toString()
            if (username.isEmpty() || password.isEmpty() || email.isBlank()){
                Snackbar.make(it,"Oops The Fields Are Empty!!",Snackbar.LENGTH_LONG).show()
            }
            else{
                createUser(username,email,password)
            }
        }
    }
    private fun createUser(username:String,email:String,password:String){
        val intent = Intent(this, CreateLoading::class.java)
        intent.putExtra("username",username)
        intent.putExtra("email",email)
        intent.putExtra("password",password)
        startActivity(intent)
        finish()
    }
}