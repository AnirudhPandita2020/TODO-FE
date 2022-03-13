package com.anirudh.todo_fe.actvity

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anirudh.todo_fe.MainViewModel.ViewModel
import com.anirudh.todo_fe.MainViewModel.ViewModelFactory
import com.anirudh.todo_fe.R
import com.anirudh.todo_fe.Repo.Repo
import com.anirudh.todo_fe.Repo.Response
import com.anirudh.todo_fe.api.RetrofitInstance
import com.anirudh.todo_fe.api.TODOBE
import com.anirudh.todo_fe.databinding.ActivityEditBinding
import com.anirudh.todo_fe.dataclass.Newtask

class EditActivity : AppCompatActivity() {
    private lateinit var binding:ActivityEditBinding
    lateinit var bundle:Bundle
    private lateinit var title:String
    private lateinit var content:String
    private lateinit var datastore: SharedPreferences
    private var is_completed:Boolean = false
    private lateinit var mainviewmodel:ViewModel
    private lateinit var token:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bundle = intent.extras!!
        datastore = getSharedPreferences("Token", Context.MODE_PRIVATE)
        token = datastore.getString("access_token","").toString()
        val retservice = RetrofitInstance.getInstance().create(TODOBE::class.java)
        val repo = Repo(retservice)
        mainviewmodel = ViewModelProvider(this, ViewModelFactory(repo)).get(ViewModel::class.java)
        setUpEdit(bundle)

        binding.edittaskbtn.setOnClickListener {
            edittask()
        }
        binding.taskbtn.setOnClickListener {
            statechangetask()
        }
    }

    private fun setUpEdit(bundle: Bundle?){
        title = bundle?.getString("Title").toString()
        content = bundle?.getString("content").toString()
        is_completed = bundle?.getBoolean("is_completed") == true
        binding.newtask.setText(title)
        binding.content.setText(content)
        if (is_completed == true){
            binding.taskbtn.setText(R.string.mark_as_imcomplete)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.taskbtn.setBackgroundColor(getColor(R.color.red))
                binding.taskbtn.setTextColor(getColor(R.color.white))
            }
        }else{
            binding.taskbtn.setText(R.string.mark_as_complete)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.taskbtn.setBackgroundColor(getColor(R.color.green))
                binding.taskbtn.setTextColor(getColor(R.color.black))
            }
        }
    }
    private fun edittask(){
        binding.linearLayout.visibility = View.GONE
        binding.addnewanim.visibility = View.VISIBLE
        binding.textView4.visibility = View.GONE
        val edittitle = binding.newtask.text.toString()
        val content = binding.content.text.toString()
        val task = Newtask(content = content,is_completed = false,title= edittitle)
        mainviewmodel.edittask("Bearer $token",task,bundle.getInt("id"))
        mainviewmodel.resedittask.observe(this, Observer {
            when(it){
                is Response.Success -> {
                    binding.addnewanim.visibility = View.GONE
                    binding.doneanim.visibility = View.VISIBLE
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        onBackPressed()
                    },1500)
                }
                is Response.Error -> {
                    binding.erroranim.visibility = View.VISIBLE
                    binding.addnewanim.visibility = View.GONE
                }
                is Response.Loading -> {}
            }
        })
    }
    private fun statechangetask(){
        binding.linearLayout.visibility = View.GONE
        binding.addnewanim.visibility = View.VISIBLE
        binding.textView4.visibility = View.GONE
        is_completed = !is_completed
        val task = Newtask(content,is_completed,title)
        mainviewmodel.edittask("Bearer $token",task,bundle.getInt("id"))
        mainviewmodel.resedittask.observe(this, Observer {
            when(it){
                is Response.Success -> {
                    binding.addnewanim.visibility = View.GONE
                    binding.doneanim.visibility = View.VISIBLE
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        onBackPressed()
                    },1500)
                }
                is Response.Error -> {
                    binding.erroranim.visibility = View.VISIBLE
                    binding.addnewanim.visibility = View.GONE
                }
                is Response.Loading -> {}
            }
        })

    }
}