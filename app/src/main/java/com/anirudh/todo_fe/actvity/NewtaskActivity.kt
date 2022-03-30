package com.anirudh.todo_fe.actvity

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
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
import com.anirudh.todo_fe.databinding.ActivityCreateLoadingBinding

import com.anirudh.todo_fe.databinding.ActivityNewtaskBinding
import com.anirudh.todo_fe.network.NetworkHelper
import com.google.android.material.snackbar.Snackbar

class NewtaskActivity : AppCompatActivity() {
    private lateinit var binding:ActivityNewtaskBinding
    private lateinit var datastore: SharedPreferences
    lateinit var mainviewmodel: ViewModel
    lateinit var token:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewtaskBinding.inflate(layoutInflater)
        datastore = getSharedPreferences("Token", Context.MODE_PRIVATE)
        token = datastore.getString("access_token","").toString()
        val retservice = RetrofitInstance.getInstance().create(TODOBE::class.java)
        val repo = Repo(retservice)
        mainviewmodel = ViewModelProvider(this, ViewModelFactory(repo)).get(ViewModel::class.java)
        setContentView(binding.root)
        binding.addtaskbtn.setOnClickListener {
            if(NetworkHelper.isNetworkConnected(this)){
                addnewTask(token)
            }
            else{
                Snackbar.make(it,"No internet Connection",Snackbar.LENGTH_SHORT).show()
            }
        }
    }
    private fun addnewTask(token:String){
        binding.textView4.visibility = View.GONE
        binding.linearLayout.visibility = View.GONE
        binding.addnewanim.visibility = View.VISIBLE
        val title = binding.newtask.text.toString()
        val content = binding.content.text.toString()
        val is_completed = false
        mainviewmodel.createtask("Bearer $token",title,content,is_completed)

        mainviewmodel.res_create_task.observe(this, Observer {
            when(it){
                is Response.Success -> {
                    binding.addnewanim.visibility = View.GONE
                    binding.doneanim.visibility = View.VISIBLE
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        onBackPressed()
                    },1500)
                }
                is Response.Error -> {
                    binding.addnewanim.visibility = View.GONE
                    binding.erroranim.visibility = View.VISIBLE
                    binding.message.visibility = View.VISIBLE
                    binding.message.text = "Oops some error occured!!Please Try again"
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        onBackPressed()
                    },2000)
                }
                is Response.Loading -> {}
            }
        })

    }

}