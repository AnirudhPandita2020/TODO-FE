package com.anirudh.todo_fe.ui.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anirudh.todo_fe.actvity.LoginActivity
import com.anirudh.todo_fe.databinding.FragmentSettingfragmentBinding

class Settingfragment : Fragment() {
    private lateinit var binding:FragmentSettingfragmentBinding
    private lateinit var datastore: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingfragmentBinding.inflate(layoutInflater,container,false)

        binding.logout.setOnClickListener {
            datastore = requireActivity().getSharedPreferences("Token", Context.MODE_PRIVATE)
            val editor:SharedPreferences.Editor = datastore.edit()
            editor.clear()
            editor.apply()
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }
        return binding.root
    }
}