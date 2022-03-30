package com.anirudh.todo_fe.actvity

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.anirudh.todo_fe.R

import com.anirudh.todo_fe.databinding.ActivityMainBinding
import com.anirudh.todo_fe.network.NetworkHelper
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataStore: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.fragment)
        binding.bottomview.setupWithNavController(navController)
        dataStore = getSharedPreferences("Token", Context.MODE_PRIVATE)
        binding.newtask.setOnClickListener {
            if (NetworkHelper.isNetworkConnected(this))
            {
                startActivity(Intent(this,NewtaskActivity::class.java))
            }
            else{
                Snackbar.make(it,"No internet connection",Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.bottomview.background = null
        binding.bottomview.menu.getItem(2).isEnabled = false


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}