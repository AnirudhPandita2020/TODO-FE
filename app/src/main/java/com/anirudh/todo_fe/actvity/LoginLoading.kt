package com.anirudh.todo_fe.actvity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anirudh.todo_fe.MainViewModel.ViewModel
import com.anirudh.todo_fe.MainViewModel.ViewModelFactory
import com.anirudh.todo_fe.Repo.Repo
import com.anirudh.todo_fe.Repo.Response
import com.anirudh.todo_fe.api.RetrofitInstance
import com.anirudh.todo_fe.api.TODOBE
import com.anirudh.todo_fe.databinding.ActivityLoginLoadingBinding

class LoginLoading : AppCompatActivity() {
    var isLogin :Boolean = false
    private lateinit var bundle: Bundle
    private lateinit var datastore:SharedPreferences
    lateinit var mainviewmodel: ViewModel
    lateinit var binding: ActivityLoginLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginLoadingBinding.inflate(layoutInflater)
        bundle = intent.extras!!
        val retservice = RetrofitInstance.getInstance().create(TODOBE::class.java)
        val repo = Repo(retservice)
        mainviewmodel = ViewModelProvider(this, ViewModelFactory(repo)).get(ViewModel::class.java)
        setContentView(binding.root)
        loginUser(bundle.getString("username").toString(),bundle.getString("password").toString())

    }


    private fun loginUser(username:String,password:String){
        mainviewmodel.userlogin(username,password)
        mainviewmodel.res_login.observe(this, Observer {
            when(it){
                is Response.Success ->{
                    it.data?.let { logindata ->
                        datastore = getSharedPreferences("Token", MODE_PRIVATE)
                        val editor:SharedPreferences.Editor = datastore.edit()
                        editor.putString("access_token",logindata.access_token)
                        editor.putString("username",logindata.username)
                        editor.putString("email",logindata.email)
                        editor.apply()
                        val intent =Intent(this,MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                }
                is Response.Error ->{
                    if(it.error.equals("timeout")){
                        binding.loadlogin.visibility = View.GONE
                        binding.timeout.visibility = View.VISIBLE
                        binding.errormessage.text = "Oops Timeout!!!"
                        Handler(Looper.getMainLooper()).postDelayed(Runnable {
                            finish()
                        },2000)
                    }else{
                        binding.loadlogin.visibility = View.GONE
                        binding.error.visibility = View.VISIBLE
                        binding.errormessage.visibility =View.VISIBLE
                        Handler(Looper.getMainLooper()).postDelayed(Runnable {
                            finish()
                        },2000)
                    }
                }
                is Response.Loading -> {}
            }
        })
    }
}