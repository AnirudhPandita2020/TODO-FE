package com.anirudh.todo_fe.actvity

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

class CreateLoading : AppCompatActivity() {
    private lateinit var binding:ActivityCreateLoadingBinding
    lateinit var mainviewmodel: ViewModel
    lateinit var bundle: Bundle
    var isError:Boolean =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bundle = intent.extras!!
        val retservice = RetrofitInstance.getInstance().create(TODOBE::class.java)
        val repo = Repo(retservice)
        mainviewmodel = ViewModelProvider(this, ViewModelFactory(repo)).get(ViewModel::class.java)
        createuser(bundle)
    }

    override fun onBackPressed() {
        if(isError){
            return super.onBackPressed()
        }
        else{
            Toast.makeText(this,"Please Wait",Toast.LENGTH_SHORT).show()
        }
    }
    private fun createuser(bundle: Bundle){
        val username = bundle.getString("username")!!
        val password = bundle.getString("password")!!
        val email = bundle.getString("email")!!


        mainviewmodel.createuser(username,email,password)

        mainviewmodel.res_create.observe(this, Observer {
            when(it){
                is Response.Loading ->{}
                is Response.Success -> {
                        binding.lottieAnimationView.visibility = View.GONE
                        binding.textView.visibility = View.GONE
                        binding.done.visibility = View.VISIBLE
                        binding.donemessage.visibility = View.VISIBLE
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        finish()
                    },3000)

                }
                is Response.Error -> {
                    binding.lottieAnimationView.visibility = View.GONE
                    binding.textView.visibility = View.GONE
                    binding.error.visibility = View.VISIBLE
                    binding.errormessage.visibility = View.VISIBLE
                    isError = true
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        onBackPressed()
                    },3000)
                }
            }
        })
    }
}