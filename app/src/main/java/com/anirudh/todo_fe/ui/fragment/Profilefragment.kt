package com.anirudh.todo_fe.ui.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anirudh.todo_fe.R
import com.anirudh.todo_fe.actvity.FriendRequest
import com.anirudh.todo_fe.databinding.FragmentProfilefragmentBinding
import com.anirudh.todo_fe.network.NetworkHelper
import com.google.android.material.snackbar.Snackbar


class Profilefragment : Fragment() {
    private lateinit var binder: FragmentProfilefragmentBinding
    private lateinit var datastore:SharedPreferences
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binder = FragmentProfilefragmentBinding.inflate(layoutInflater,container,false)
        datastore = requireContext().getSharedPreferences("Token", AppCompatActivity.MODE_PRIVATE)
        binder.profileusername.text = "Username :${datastore.getString("username","")}"
        binder.profileemail.text = "Email :${datastore.getString("email","")}"


        binder.friend.setOnClickListener {
            startActivity(Intent(context,FriendRequest::class.java))
        }
        binder.friendreq.setOnClickListener{

        }
        return binder.root
    }
}