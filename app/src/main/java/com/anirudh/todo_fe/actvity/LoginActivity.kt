package com.anirudh.todo_fe.actvity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import com.anirudh.todo_fe.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar


class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var datastore:SharedPreferences
    lateinit var token:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        datastore = getSharedPreferences("Token", Context.MODE_PRIVATE)
        token = datastore.getString("access_token","").toString()
        Log.i("TAG",token)
        if (token.isNotEmpty()){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.loginbtn.setOnClickListener{
            if(binding.emailinput.text.isEmpty() || binding.passwordinput.text.isEmpty()){
                Snackbar.make(it,"Oops The Fields Are Empty!!",Snackbar.LENGTH_LONG).show()
            }
            else{
                userlogin(binding.emailinput.text.toString(),binding.passwordinput.text.toString())
            }
        }
        binding.register.setOnClickListener {
            val intent = Intent(this, Signupactivty::class.java)
            startActivity(intent)
        }
    }
    private fun userlogin(username:String,password:String){
        val intent = Intent(this, LoginLoading::class.java)
        intent.putExtra("username",username)
        intent.putExtra("password",password)
        startActivity(intent)
    }


}